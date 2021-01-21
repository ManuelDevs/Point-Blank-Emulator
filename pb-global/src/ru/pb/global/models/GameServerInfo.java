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

package ru.pb.global.models;

import ru.pb.global.enums.ChannelType;
import ru.pb.global.network.objects.Connection;

import java.beans.Transient;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Модель игрового сервера
 *
 * @author sjke
 */

public class GameServerInfo implements Serializable {

	private static final long serialVersionUID = 3258629378963112228L;
	private Integer id;
	private String password;
	private int maxPlayer;
	private ChannelType type;
	private int port;
	private AtomicInteger online = new AtomicInteger();
	private String ip;
	private boolean available;
	private transient Connection connection;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getMaxPlayer() {
		return maxPlayer;
	}

	public void setMaxPlayer(int maxPlayer) {
		this.maxPlayer = maxPlayer;
	}

	public ChannelType getType() {
		return type;
	}

	public void setType(ChannelType type) {
		this.type = type;
	}

	public AtomicInteger getOnline() {
		return online;
	}

	public void setOnline(AtomicInteger online) {
		this.online = online;
	}

	@Transient
	public Connection getConnection() {
		return connection;
	}

	@Transient
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public boolean getAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	@Override
	public String toString() {
		return "GameServerInfo{" +
				"id=" + id +
				", maxPlayer=" + maxPlayer +
				", type=" + type +
				", port=" + port +
				", online=" + online +
				", ip='" + ip + '\'' +
				", available=" + available +
				", connection=" + connection +
				"}";
	}
}