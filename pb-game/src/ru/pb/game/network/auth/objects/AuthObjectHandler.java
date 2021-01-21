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

package ru.pb.game.network.auth.objects;

import ru.pb.game.network.auth.AuthConnection;
import ru.pb.game.network.auth.objects.handlers.AuthOnServerHandler;
import ru.pb.game.network.auth.objects.handlers.GameServerListHandler;
import ru.pb.global.network.objects.ObjectHandler;
import ru.pb.global.network.objects.GameAuth.AuthOnServer;
import ru.pb.global.network.objects.GameAuth.GameServerList;

/**
 * Обработчик сообщений от ЛС-а
 *
 * @author sjke
 */
public class AuthObjectHandler extends ObjectHandler<AuthConnection> {

	private static final AuthObjectHandler INSTANCE = new AuthObjectHandler();

	private AuthObjectHandler() {
		addHolder(AuthOnServer.class.getSimpleName(), new AuthOnServerHandler());
		addHolder(GameServerList.class.getSimpleName(), new GameServerListHandler());
		log.info("Loaded " + map.size() + " operations interaction with the auth server");
	}

	public static AuthObjectHandler getInstance() {
		return INSTANCE;
	}
}
