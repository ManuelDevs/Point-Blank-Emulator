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

package ru.pb.auth.controller;

import ru.pb.auth.network.gameserver.GameConnection;
import ru.pb.global.controller.BaseController;
import ru.pb.global.enums.GameServerAuthResponse;
import ru.pb.global.models.GameServerInfo;
import ru.pb.global.service.GameServerDaoService;

import java.util.*;

/**
 * Контроллер игровых серверов подключенных к серверу авторизации
 *
 * @author sjke
 */
public class GameServerController extends BaseController {

	private static Map<Integer, GameServerInfo> gameservers = new HashMap<Integer, GameServerInfo>();

	private GameServerController() {
		List<GameServerInfo> list = GameServerDaoService.getInstance().getAll();
		for (GameServerInfo gs : list) {
			gameservers.put(gs.getId(), gs);
		}
		log.info("Loaded " + gameservers.size() + " registered GameServers.");
	}

	public static GameServerController getInstance() {
		return Singleton.INSTANCE;
	}

	public Collection<GameServerInfo> getServers() {
		return Collections.unmodifiableCollection(gameservers.values());
	}

	public GameServerAuthResponse registerGameServer(GameConnection connection, int id, String password, int port) {
		GameServerInfo gsi = getGameServerInfo(id);
		if (gsi == null) {
			log.info(connection + " requested id [" + id + "] not aviable!");
			return GameServerAuthResponse.NOT_FOUND;
		}
		if (gsi.getConnection() != null) {
			log.info(connection + " requested id [" + id + "] is already registered!");
			return GameServerAuthResponse.ALREADY_REGISTERED;
		}
		if (!gsi.getPassword().equals(password)) {
			log.info(connection + " requested id [" + id + "] incorrect password [" + password + "]");
			return GameServerAuthResponse.INCORRECT;
		}
		gsi.setPort(port);
		gsi.setIp(connection.getIp());
		gsi.setConnection(connection);
		gsi.setAvailable(true);
		connection.setGameServerInfo(gsi);
		return GameServerAuthResponse.AUTHED;
	}

	public GameServerInfo getGameServerInfo(Integer gameServerId) {
		return gameservers.get(gameServerId);
	}

	private static class Singleton {
		private static final GameServerController INSTANCE = new GameServerController();
	}
}
