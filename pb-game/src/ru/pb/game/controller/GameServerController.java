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

package ru.pb.game.controller;

import ru.pb.game.network.auth.AuthConnection;
import ru.pb.game.properties.GameServerProperty;
import ru.pb.global.controller.BaseController;
import ru.pb.global.models.GameServerInfo;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Контроллер игровых серверов подключенных к серверу авторизации
 *
 * @author sjke
 */
public class GameServerController extends BaseController {

	private static ConcurrentMap<Integer, GameServerInfo> gameservers = new ConcurrentSkipListMap<Integer, GameServerInfo>();
	private static GameServerInfo currentGameServerInfo;

	private GameServerController() {
		log.info("Loaded");
	}

	public static GameServerController getInstance() {
		return Singleton.INSTANCE;
	}

	public Collection<GameServerInfo> getServers() {
		return Collections.unmodifiableCollection(gameservers.values());
	}

	public void writeServerList(Collection<GameServerInfo> list, AuthConnection connection) {
		for (GameServerInfo gs : list) {
			gameservers.put(gs.getId(), gs);
			if (GameServerProperty.getInstance().GAME_SERVER_ID.equals(gs.getId())) {
				currentGameServerInfo = gs;
				currentGameServerInfo.setConnection(connection);
				connection.setGameServerInfo(gs);
			}
		}
	}

	public GameServerInfo getCurrentGameServerInfo() {
		return currentGameServerInfo;
	}

	public void setCurrentGameServerInfo(GameServerInfo currentGameServerInfo) {
		GameServerController.currentGameServerInfo = currentGameServerInfo;
	}

	private static class Singleton {
		private static final GameServerController INSTANCE = new GameServerController();
	}
}
