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

package ru.pb.game.properties;

import ru.pb.global.properties.PropertiesLoader;

/**
 * Настройки ГС-а
 *
 * @author sjke
 */
public class GameServerProperty extends PropertiesLoader {

	private static final String FILE_NAME = "system.properties";

	public String AUTH_SERVER_HOST;
	public Integer AUTH_SERVER_PORT;

	public Integer GAME_SERVER_ID;
	public String GAME_SERVER_PASSWORD;

	public String GAME_SERVER_HOST;
	public Integer GAME_SERVER_PORT;
	public Integer GAME_SERVER_COUNT_BOSS_THREADS;
	public Integer GAME_SERVER_COUNT_WORK_THREADS;

	public String UDP_SERVER_HOST;
	public Integer UDP_SERVER_PORT;
	public Integer UDP_SERVER_COUNT_BOSS_THREADS;
	public Integer UDP_SERVER_COUNT_WORK_THREADS;

	public Boolean SHOW_BYTE_BUFFER;

	private GameServerProperty() {
		super(FILE_NAME);
		AUTH_SERVER_HOST = loadString("auth.server.host");
		AUTH_SERVER_PORT = loadInteger("auth.server.port");
		GAME_SERVER_ID = loadInteger("game.server.id");
		GAME_SERVER_PASSWORD = loadString("game.server.password");
		GAME_SERVER_HOST = loadString("game.server.host");
		GAME_SERVER_PORT = loadInteger("game.server.port");
		GAME_SERVER_COUNT_BOSS_THREADS = loadInteger("game.server.count.boss.threads");
		GAME_SERVER_COUNT_WORK_THREADS = loadInteger("game.server.count.work.threads");
		UDP_SERVER_HOST = loadString("udp.server.host");
		UDP_SERVER_PORT = loadInteger("udp.server.port");
		UDP_SERVER_COUNT_BOSS_THREADS = loadInteger("udp.server.count.boss.threads");
		UDP_SERVER_COUNT_WORK_THREADS = loadInteger("udp.server.count.work.threads");
		SHOW_BYTE_BUFFER = loadBoolean("show.byte.buffer");
	}

	public static GameServerProperty getInstance() {
		return Singleton.INSTANCE;
	}

	private static class Singleton {
		private static final GameServerProperty INSTANCE = new GameServerProperty();
	}
}
