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

package ru.pb.battle.properties;

import ru.pb.global.properties.PropertiesLoader;

/**
 * Настройки Боевого сервера
 *
 * @author sjke
 */
public class BattleServerProperty extends PropertiesLoader {

	private static final String FILE_NAME = "system.properties";

	public String GAME_SERVER_HOST;
	public Integer GAME_SERVER_PORT;

	public Boolean SHOW_BYTE_BUFFER;

	public String BATTLE_SERVER_HOST;
	public Integer BATTLE_SERVER_PORT;

	private BattleServerProperty() {
		super(FILE_NAME);
		GAME_SERVER_HOST = loadString("game.server.host");
		GAME_SERVER_PORT = loadInteger("game.server.port");

		BATTLE_SERVER_HOST = loadString("battle.server.host");
		BATTLE_SERVER_PORT = loadInteger("battle.server.port");

		SHOW_BYTE_BUFFER = loadBoolean("show.byte.buffer");

	}

	public static BattleServerProperty getInstance() {
		return Singleton.INSTANCE;
	}

	private static class Singleton {
		private static final BattleServerProperty INSTANCE = new BattleServerProperty();
	}
}
