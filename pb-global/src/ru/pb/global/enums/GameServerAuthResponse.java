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

package ru.pb.global.enums;

/**
 * Ответ на резистрацию игрового сервера на сервере авторизации
 *
 * @author sjke
 */
public enum GameServerAuthResponse {

	AUTHED(0),
	NOT_FOUND(1),
	INCORRECT(2),
	ALREADY_REGISTERED(3);

	private byte responseId;

	private GameServerAuthResponse(int responseId) {
		this.responseId = (byte) responseId;
	}

	public byte getResponseId() {
		return responseId;
	}
}
