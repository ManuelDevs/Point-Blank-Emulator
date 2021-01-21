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
import ru.pb.global.models.Channel;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;

/**
 * Неизместный пакет, отправляется вместе с пекетов авторизации
 *
 * @author sjke
 */
public class SM_LOBBY_ROOM_LIST extends ServerPacket {

	private final Channel channel;

	public SM_LOBBY_ROOM_LIST(Channel channel) {
		super(0xC02);
		this.channel = channel;
	}

	@Override
	public void writeImpl() {
		writeD(channel.getRooms().size());
		writeD(0);
		writeD(channel.getRooms().size());
		int i = 0;
		for (Room room : channel.getRooms().values()) {
			writeD(room.getId());
			writeS(room.getName(), 23);
			writeC(room.getMapId());
			writeC(0);
			writeC(0);
			writeC(room.getType());
			writeC(room.isFigth() ? 1 : 0);
			writeC(room.getPlayers().size());
			writeC(room.getSlots());
			writeC(1);
			writeC(0x2b);
			writeC(room.getPassword().isEmpty() ? 0 : 1);
			writeC(room.getSpecial());
		}
		writeD(channel.getPlayers().size());
		writeD(0);
		writeD(channel.getPlayers().size());
		i = 0;
		for (Player player : channel.getPlayers().values()) {
			if (player != null) {
				i++;
				writeD(i);
				int logo1 = 255, logo2 = 255, logo3 = 255, logo4 = 255, color = 0;
				String clanName = "";

				if (player.getClan() != null) {
					logo1 = player.getClan().getLogo1();
					logo2 = player.getClan().getLogo2();
					logo3 = player.getClan().getLogo3();
					logo4 = player.getClan().getLogo4();
					color = player.getClan().getColor();
					clanName = player.getClan().getName();
				}
				writeC(logo1);
				writeC(logo2);
				writeC(logo3);
				writeC(logo4);
				writeC(color);
				writeS(clanName, 16);
				writeH(player.getRank());
				writeS(player.getName(), 33);
				writeC(player.getColor());
			} else {
				writeD(i);
				writeC(0);
				writeC(0);
				writeC(0);
				writeC(0);
				writeC(0);
				writeS("", 0);
				writeH(0);
				writeS("", 0);
				writeC(0);
			}
		}
	}
}