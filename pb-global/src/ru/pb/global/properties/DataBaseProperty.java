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

package ru.pb.global.properties;

/**
 * Настройки авторизации
 *
 * @author sjke
 */
public class DataBaseProperty extends PropertiesLoader {

	public final String DRIVER = loadString("driver");
	public final String URL = loadString("url");
	public final String USER = loadString("username");
	public final String PASS = loadString("password");
	public final Integer MAX_CONNECTIONS = loadInteger("maxconnections");
	public final boolean DATABASE_DEBUG_CONNECTIONS = loadBoolean("debug");

	private DataBaseProperty() {
		super("jdbc.properties");
	}

	public static DataBaseProperty getInstance() {
		return Singleton.INSTANCE;
	}

	private static class Singleton {
		private static final DataBaseProperty INSTANCE = new DataBaseProperty();
	}
}
