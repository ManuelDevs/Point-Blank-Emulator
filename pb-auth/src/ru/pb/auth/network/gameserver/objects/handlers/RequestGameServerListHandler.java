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
import ru.pb.global.models.GameServerInfo;
import ru.pb.global.network.objects.Handler;
import ru.pb.global.network.objects.GameAuth.GameServerList;
import ru.pb.global.network.objects.GameAuth.RequestGameServerList;

import java.util.ArrayList;
import java.util.List;

/**
 * Запрос списка серверов
 *
 * @author sjke
 */
public class RequestGameServerListHandler implements Handler<RequestGameServerList, GameConnection> {

	@Override
	public void call(RequestGameServerList obj, GameConnection connection) {
		GameServerList gameServerList = new GameServerList();
		List<GameServerInfo> list = new ArrayList<GameServerInfo>();
		for (GameServerInfo server : GameServerController.getInstance().getServers()) {
			GameServerInfo newServer = new GameServerInfo();
			newServer.setId(server.getId());
			newServer.setPassword(server.getPassword());
			newServer.setMaxPlayer(server.getMaxPlayer());
			newServer.setType(server.getType());
			newServer.setPort(server.getPort());
			newServer.setOnline(server.getOnline());
			newServer.setIp(server.getIp());
			newServer.setAvailable(server.getAvailable());
			list.add(newServer);
		}
		gameServerList.setList(list);
		connection.sendObject(gameServerList);
	}
}