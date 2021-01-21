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

import java.util.HashMap;
import java.util.Map;

/**
 * Специальный обработчик классов
 *
 * @author sjke
 */
public abstract class ObjectHandler<T extends Connection> {

	protected final Logger log = LoggerFactory.getLogger(getClass());
	protected final Map<String, Handler> map = new HashMap<String, Handler>();

	public void call(ObjectNetwork obj, T connection) {
		Handler handler = map.get(obj.getClass().getSimpleName());
		if (handler == null) {
			log.info(connection + " unknown object: " + (log.isDebugEnabled() ? obj : obj.getClass().getSimpleName()));
			return;
		}
		log.debug(connection + " reading object: " + (log.isDebugEnabled() ? obj : obj.getClass().getSimpleName()) + " from address: " + connection.getChannel().remoteAddress());
		try {
			handler.getClass().newInstance().call(obj, connection);
		} catch (Exception e) {
			log.error("Error new instance handler for object: " + obj);
		}
	}

	protected void addHolder(String name, Handler handler) {
		map.put(name, handler);
	}
}
