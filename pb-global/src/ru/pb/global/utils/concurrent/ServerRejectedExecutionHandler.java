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

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * хендлер
 *
 * @author sjke
 */
public class ServerRejectedExecutionHandler implements RejectedExecutionHandler {

	private static final Logger log = LoggerFactory.getLogger(ServerRejectedExecutionHandler.class);

	@Override
	public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
		if (executor.isShutdown()) {
			return;
		}
		log.warn(runnable + " from " + executor, new RejectedExecutionException());
		if (Thread.currentThread().getPriority() > Thread.NORM_PRIORITY) {
			new Thread(runnable).start();
		} else {
			runnable.run();
		}
	}
}