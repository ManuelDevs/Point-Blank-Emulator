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
 * Copyright (C) 2013 PBDevâ„¢
 */

package ru.pb.auth.properties;

import ru.pb.global.properties.PropertiesLoader;

/**
 * Ð�Ð°Ñ�Ñ‚Ñ€Ð¾Ð¹ÐºÐ¸ Ð°Ð²Ñ‚Ð¾Ñ€Ð¸Ð·Ð°Ñ†Ð¸Ð¸
 *
 * @author sjke
 */
public class AuthServerProperty extends PropertiesLoader {

	public final String AUTH_CLIENT_HOST = loadString("auth.client.host");
	public final Integer AUTH_CLIENT_PORT = loadInteger("auth.client.port");
	public final Integer AUTH_CLIENT_COUNT_BOSS_THREADS = loadInteger("auth.client.count.boss.threads");
	public final Integer AUTH_CLIENT_COUNT_WORK_THREADS = loadInteger("auth.client.count.work.threads");
	public final String AUTH_GAME_HOST = loadString("auth.game.host");
	public final Integer AUTH_GAME_PORT = loadInteger("auth.game.port");
	public final Integer AUTH_GAME_COUNT_BOSS_THREADS = loadInteger("auth.game.count.boss.threads");
	public final Integer AUTH_GAME_COUNT_WORK_THREADS = loadInteger("auth.game.count.work.threads");
	public final Boolean ACCOUNT_AUTO_CREATE = loadBoolean("account.auto.create");
	public final Integer INTERVAL_BETWEEN_LOGIN = loadInteger("account.interval.between.login");
	public final Integer ACCOUNT_MIN_LENGTH = loadInteger("account.min.length");
	public final Integer ACCOUNT_MAX_LENGTH = loadInteger("account.max.length");
	public final String ACCOUNT_BAD_SYMBOLS = loadString("account.bad.symbols");
	public final Integer PASSWORD_MIN_LENGTH = loadInteger("password.min.length");
	public final Integer PASSWORD_MAX_LENGTH = loadInteger("password.max.length");
	public final String PASSWORD_BAD_SYMBOLS = loadString("password.bad.symbols");
	public final Boolean SHOW_BYTE_BUFFER = loadBoolean("show.byte.buffer");
	public final String GAME_VERSION = loadString("game.version");

	private AuthServerProperty() {
		super("system.properties");
	}

	public static AuthServerProperty getInstance() {
		return Singleton.INSTANCE;
	}

	private static class Singleton {
		private static final AuthServerProperty INSTANCE = new AuthServerProperty();
	}
}
