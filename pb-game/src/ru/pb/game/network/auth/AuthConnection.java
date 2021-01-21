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

package ru.pb.game.network.auth;

import io.netty.channel.ChannelHandlerContext;
import ru.pb.game.network.auth.objects.AuthObjectHandler;
import ru.pb.game.properties.GameServerProperty;
import ru.pb.global.models.GameServerInfo;
import ru.pb.global.network.objects.Connection;
import ru.pb.global.network.objects.GameAuth.AuthOnServer;

/**
 * Обработчик сообщений между игровым сервером и сервером авторизации
 *
 * @author sjke
 */
public class AuthConnection extends Connection {

	private GameServerInfo gameServerInfo = null;

	public AuthConnection() {
		super(AuthObjectHandler.getInstance());
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		AuthOnServer obj = new AuthOnServer();
		obj.setId(GameServerProperty.getInstance().GAME_SERVER_ID);
		obj.setPort(GameServerProperty.getInstance().GAME_SERVER_PORT);
		obj.setPassword(GameServerProperty.getInstance().GAME_SERVER_PASSWORD);
		sendObject(obj);
	}

	public GameServerInfo getGameServerInfo() {
		return gameServerInfo;
	}

	public void setGameServerInfo(GameServerInfo gameServerInfo) {
		this.gameServerInfo = gameServerInfo;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("AuthClient [id:").append(gameServerInfo == null ? null : gameServerInfo.getId());
		sb.append(", ip: ").append(getIp()).append(']');
		return sb.toString();
	}
}
