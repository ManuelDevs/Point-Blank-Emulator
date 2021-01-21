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

package ru.pb.auth.network.gameserver.objects.handlers;

import ru.pb.auth.controller.GameServerController;
import ru.pb.auth.network.gameserver.GameConnection;
import ru.pb.global.network.objects.Handler;
import ru.pb.global.network.objects.GameAuth.AuthOnServer;

public class AuthOnServerHandler implements Handler<AuthOnServer, GameConnection> {

	@Override
	public void call(AuthOnServer obj, GameConnection connection) {
		obj.setResponse(GameServerController.getInstance().registerGameServer(connection, obj.getId(), obj.getPassword(), obj.getPort()));
		connection.sendObject(obj);
	}
}
