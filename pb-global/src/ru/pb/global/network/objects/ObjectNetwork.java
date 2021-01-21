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

/**
 * Объекты для передачи по сети
 *
 * @author sjke
 */
public abstract class ObjectNetwork<T extends Connection> {

	protected final Logger log = LoggerFactory.getLogger(getClass());
	private T connection;

	protected void sendObject(ObjectNetwork<T> obj) {
		connection.sendObject(obj);
	}

	public T getConnection() {
		return connection;
	}
}
