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

package ru.pb.game.network.client.packets.server;

import ru.pb.game.network.client.packets.ServerPacket;
import ru.pb.global.models.Player;

/**
 * Неизместный пакет, отправляется вместе с пекетов авторизации
 *
 * @author sjke
 */
public class SM_CHAT_LOBBY_MESSAGE extends ServerPacket {

	private Player player;
	private String message;

	public SM_CHAT_LOBBY_MESSAGE(Player player, String message) {
		super(0xC15);
		this.player = player;
		this.message = message;
	}

	@Override
	public void writeImpl() {
		writeD(player.getConnection().getId());
		int length = player.getName().length() > player.MAX_NAME_SIZE ? player.MAX_NAME_SIZE : player.getName().length();
		writeC(length);
		writeS(player.getName().length() > length ? player.getName().substring(0, length) : player.getName());
		writeC(player.getColor());
		writeH(message.length());
		writeS(message);
	}
}