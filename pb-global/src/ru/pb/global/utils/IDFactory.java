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

package ru.pb.global.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.BitSet;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Фабрика ID-шек
 *
 * @author sjke
 */
public class IDFactory {

	private static final Logger log = LoggerFactory.getLogger(IDFactory.class);
	/**
	 * Список заюзанных id-шек.
	 */
	private static final BitSet idList = new BitSet();
	private static final ReentrantLock lock = new ReentrantLock();
	/**
	 * Минимальный ID-шник
	 */
	private volatile int nextMinId = 0;

	private IDFactory() {
		idList.set(0);
	}

	public static final IDFactory getInstance() {
		return SingletonHolder.instance;
	}

	public int nextId() {
		try {
			lock.lock();
			int id;
			if (nextMinId == Integer.MIN_VALUE) {
				id = Integer.MIN_VALUE;
			} else {
				id = idList.nextClearBit(nextMinId);
			}
			idList.set(id);
			nextMinId = id + 1;
			return id;
		} finally {
			lock.unlock();
		}
	}

	public void releaseId(int id) {
		try {
			lock.lock();
			boolean status = idList.get(id);
			if (status) {
				idList.clear(id);
				if (id < nextMinId || nextMinId == Integer.MIN_VALUE) {
					nextMinId = id;
				}
			}
		} finally {
			lock.unlock();
		}
	}

	@SuppressWarnings("synthetic-access")
	private static class SingletonHolder {
		protected static final IDFactory instance = new IDFactory();
	}
}
