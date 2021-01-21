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

package ru.pb.auth.network.gameserver;

import io.netty.channel.ChannelHandlerContext;
import ru.pb.auth.network.gameserver.objects.GameObjectHandler;
import ru.pb.global.models.GameServerInfo;
import ru.pb.global.network.objects.Connection;


/**
 * Обработчик сообщений между игровым сервером и сервером авторизации
 *
 * @author sjke
 */
public class GameConnection extends Connection {

	private GameServerInfo gameServerInfo = null;

	public GameConnection() {
		super(GameObjectHandler.getInstance());
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}

	public void setGameServerInfo(GameServerInfo gameServerInfo) {
		this.gameServerInfo = gameServerInfo;
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		super.channelUnregistered(ctx);
		gameServerInfo.setConnection(null);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("GameServer [id:");
		sb.append(gameServerInfo == null ? "none" : gameServerInfo.getId());
		sb.append(", ip: ").append(getIp()).append(']');
		return sb.toString();
	}
}
