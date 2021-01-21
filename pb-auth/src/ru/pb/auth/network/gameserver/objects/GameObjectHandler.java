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

package ru.pb.auth.network.gameserver.objects;

import ru.pb.auth.network.gameserver.GameConnection;
import ru.pb.auth.network.gameserver.objects.handlers.AuthOnServerHandler;
import ru.pb.auth.network.gameserver.objects.handlers.RequestGameServerListHandler;
import ru.pb.global.network.objects.ObjectHandler;
import ru.pb.global.network.objects.GameAuth.AuthOnServer;
import ru.pb.global.network.objects.GameAuth.RequestGameServerList;

/**
 * Обработчик сообщений от ГС-а
 *
 * @author sjke
 */
public class GameObjectHandler extends ObjectHandler<GameConnection> {

	private static final GameObjectHandler INSTANCE = new GameObjectHandler();

	private GameObjectHandler() {
		addHolder(AuthOnServer.class.getSimpleName(), new AuthOnServerHandler());
		addHolder(RequestGameServerList.class.getSimpleName(), new RequestGameServerListHandler());
		log.info("Loaded " + map.size() + " operations interaction with the game server");
	}

	public static GameObjectHandler getInstance() {
		return INSTANCE;
	}
}