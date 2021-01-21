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

package ru.pb.global.network.objects.GameAuth;

import ru.pb.global.enums.GameServerAuthResponse;
import ru.pb.global.network.objects.Connection;
import ru.pb.global.network.objects.ObjectNetwork;

import java.io.Serializable;

/**
 * Объект для авторизации ГС-а на ЛС-е
 *
 * @author sjke
 */
public class AuthOnServer<T extends Connection> extends ObjectNetwork<T> implements Serializable {

	private static final long serialVersionUID = 4802148983333921821L;

	private int id;
	private int port;
	private String password;
	private GameServerAuthResponse response;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public GameServerAuthResponse getResponse() {
		return response;
	}

	public void setResponse(GameServerAuthResponse response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "AuthOnServer{" +
				"id=" + id +
				", port=" + port +
				", password='" + password + '\'' +
				", response=" + response +
				'}';
	}
}
