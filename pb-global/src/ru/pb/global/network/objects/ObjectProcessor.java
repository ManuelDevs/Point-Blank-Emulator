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

package ru.pb.global.network.objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Обработчик пакетов
 *
 * @author sjke
 */
public class ObjectProcessor<T extends Connection> {

	private static final Logger log = LoggerFactory.getLogger(ObjectProcessor.class.getName());

	private final static int reduceThreshold = 3;
	private final static int increaseThreshold = 50;
	private final Lock lock = new ReentrantLock();
	private final Condition notEmpty = lock.newCondition();
	private final List<Object> packets = new LinkedList<Object>();
	private final List<Thread> threads = new ArrayList<Thread>();
	private final int minThreads;
	private final int maxThreads;

	private final Connection connection;
	private ObjectHandler handler;

	public ObjectProcessor(Connection connection, int minThreads, int maxThreads) {
		this.connection = connection;
		if (minThreads <= 0) {
			minThreads = 1;
		}
		if (maxThreads < minThreads) {
			maxThreads = minThreads;
		}

		this.minThreads = minThreads;
		this.maxThreads = maxThreads;

		if (minThreads != maxThreads) {
			startCheckerThread();
		}

		for (int i = 0; i < minThreads; i++) {
			newThread();
		}
	}

	private void startCheckerThread() {
		new Thread(new CheckerTask(), "PacketProcessor:Checker").start();
	}

	private boolean newThread() {
		if (threads.size() >= maxThreads) {
			return false;
		}
		log.debug("Creating new PacketProcessor Thread: PacketProcessor:" + threads.size());
		Thread t = new Thread(new PacketProcessorTask(), "PacketProcessor:" + threads.size());
		threads.add(t);
		t.start();
		return true;
	}

	private void killThread() {
		if (threads.size() < minThreads) {
			Thread t = threads.remove((threads.size() - 1));
			log.debug("Killing PacketProcessor Thread: " + t.getName());
			t.interrupt();
		}
	}

	public final void executeObject(Object object) {
		lock.lock();
		try {
			packets.add(object);
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}

	private Object getFirstAviable() {
		while (true) {
			while (packets.isEmpty()) {
				notEmpty.awaitUninterruptibly();
			}
			ListIterator<Object> it = packets.listIterator();
			while (it.hasNext()) {
				Object obj = it.next();
				it.remove();
				return obj;
			}
			notEmpty.awaitUninterruptibly();
		}
	}

	public void setHandler(ObjectHandler handler) {
		this.handler = handler;
	}

	private final class PacketProcessorTask implements Runnable {
		@Override
		public void run() {
			Object obj = null;
			while (true) {
				lock.lock();
				try {
					if (Thread.interrupted()) {
						return;
					}
					obj = getFirstAviable();
				} finally {
					lock.unlock();
				}
				if (obj instanceof ObjectNetwork) {
					handler.call((ObjectNetwork) obj, connection);
				}
			}
		}
	}

	private final class CheckerTask implements Runnable {
		private final int sleepTime = 60 * 1000;
		private int lastSize = 0;

		@Override
		public void run() {
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
			}
			int sizeNow = packets.size();
			if (sizeNow < lastSize) {
				if (sizeNow < reduceThreshold) {
					killThread();
				}
			} else if (sizeNow > lastSize && sizeNow > increaseThreshold) {
				if (!newThread() && sizeNow >= increaseThreshold * 3) {
					log.info("Lagg detected! [" + sizeNow + " client packets are waiting for execution]. You should " +
							"consider increasing PacketProcessor maxThreads or hardware upgrade.");
				}
			}
			lastSize = sizeNow;
		}
	}
}
