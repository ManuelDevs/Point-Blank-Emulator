/*
 * Java Server Emulator Project Blackout / PointBlank
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Authors: DarkSkeleton, sjke, Felixx
 * Copyright (C) 2013 PBDev™
 */

package ru.pb.global.utils.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Менеджер потоков
 *
 * @author sjke
 */
public class ThreadPoolManager {

	private static final Logger log = LoggerFactory.getLogger(ThreadPoolManager.class);

	private static final long MAX_DELAY = TimeUnit.NANOSECONDS.toMillis(Long.MAX_VALUE - System.nanoTime()) / 2;
	private final ScheduledThreadPoolExecutor scheduledPool;
	private final ThreadPoolExecutor instantPool;

	private ThreadPoolManager() {
		scheduledPool = new ScheduledThreadPoolExecutor(5);
		scheduledPool.setRejectedExecutionHandler(new ServerRejectedExecutionHandler());
		scheduledPool.prestartAllCoreThreads();

		instantPool = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(100000));
		instantPool.setRejectedExecutionHandler(new ServerRejectedExecutionHandler());
		instantPool.prestartAllCoreThreads();

		scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				purge();
			}
		}, 60000, 60000);
		log.info("ThreadPoolManager: Initialized with " + scheduledPool.getPoolSize() + " scheduler, " + instantPool.getPoolSize() + " instant running thread(s).");
	}

	public static ThreadPoolManager getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private final long validate(long delay) {
		return Math.max(0, Math.min(MAX_DELAY, delay));
	}

	public final ScheduledFuture<?> schedule(Runnable runnable, long delay) {
		delay = validate(delay);
		return scheduledPool.schedule(runnable, delay * 1000, TimeUnit.MILLISECONDS);
	}

	public final ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long delay, long period) {
		delay = validate(delay);
		period = validate(period);
		return scheduledPool.scheduleAtFixedRate(runnable, delay * 1000, period * 1000, TimeUnit.MILLISECONDS);
	}

	public ScheduledFuture<?> scheduleTaskManager(Runnable runnable, long delay) {
		return schedule(runnable, delay);
	}

	public final void executeTask(Runnable r) {
		instantPool.execute(r);
	}

	public void purge() {
		scheduledPool.purge();
		instantPool.purge();
	}

	/**
	 * Shutdown all thread pools.
	 */
	public void shutdown() {
		final long begin = System.currentTimeMillis();
		log.info("ThreadPoolManager: Shutting down.");
		log.info("\t... executing " + getTaskCount(scheduledPool) + " scheduled tasks.");
		scheduledPool.shutdown();
		boolean success = false;
		try {
			success |= awaitTermination(5000);
			scheduledPool.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
			scheduledPool.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
			success |= awaitTermination(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("\t... success: " + success + " in " + (System.currentTimeMillis() - begin) + " msec.");
		log.info("\t... " + getTaskCount(scheduledPool) + " scheduled tasks left.");
	}

	private int getTaskCount(ThreadPoolExecutor tp) {
		return tp.getQueue().size() + tp.getActiveCount();
	}

	public List<String> getStats() {
		List<String> list = new ArrayList<String>();
		list.add("Scheduled pool:");
		list.add("=================================================");
		list.add("\tgetActiveCount: ...... " + scheduledPool.getActiveCount());
		list.add("\tgetCorePoolSize: ..... " + scheduledPool.getCorePoolSize());
		list.add("\tgetPoolSize: ......... " + scheduledPool.getPoolSize());
		list.add("\tgetLargestPoolSize: .. " + scheduledPool.getLargestPoolSize());
		list.add("\tgetMaximumPoolSize: .. " + scheduledPool.getMaximumPoolSize());
		list.add("\tgetCompletedTaskCount: " + scheduledPool.getCompletedTaskCount());
		list.add("\tgetQueuedTaskCount: .. " + scheduledPool.getQueue().size());
		list.add("\tgetTaskCount: ........ " + scheduledPool.getTaskCount());
		list.add("");
		list.add("Instant pool:");
		list.add("=================================================");
		list.add("\tgetActiveCount: ...... " + instantPool.getActiveCount());
		list.add("\tgetCorePoolSize: ..... " + instantPool.getCorePoolSize());
		list.add("\tgetPoolSize: ......... " + instantPool.getPoolSize());
		list.add("\tgetLargestPoolSize: .. " + instantPool.getLargestPoolSize());
		list.add("\tgetMaximumPoolSize: .. " + instantPool.getMaximumPoolSize());
		list.add("\tgetCompletedTaskCount: " + instantPool.getCompletedTaskCount());
		list.add("\tgetQueuedTaskCount: .. " + instantPool.getQueue().size());
		list.add("\tgetTaskCount: ........ " + instantPool.getTaskCount());
		list.add("");
		return list;
	}

	private boolean awaitTermination(long timeoutInMillisec) throws InterruptedException {
		final long begin = System.currentTimeMillis();
		while (System.currentTimeMillis() - begin < timeoutInMillisec) {
			if (!scheduledPool.awaitTermination(10, TimeUnit.MILLISECONDS) && scheduledPool.getActiveCount() > 0) {
				continue;
			}
			if (!instantPool.awaitTermination(10, TimeUnit.MILLISECONDS) && instantPool.getActiveCount() > 0) {
				continue;
			}
			return true;
		}

		return false;
	}

	private static final class SingletonHolder {
		private static final ThreadPoolManager INSTANCE = new ThreadPoolManager();
	}

}
