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
import ru.pb.global.models.Room;
import ru.pb.global.utils.NetworkUtil;

/**
 * Неизместный пакет, отправляется вместе с пекетов авторизации
 *
 * @author sjke
 */
public class SM_BATTLE_PREPARATION extends ServerPacket {

	private final Player player;
	private final Room room;

	public SM_BATTLE_PREPARATION(Room room, Player player) {
		super(0xD15);
		this.player = player;
		this.room = room;
	}

	@Override
	public void writeImpl() {
		writeBool(room.isFigth());
		writeD(room.getRoomSlotByPlayer(player).getId());
		writeC(2); // тип сервера? 1 - p2p server - норм работает
		writeB(room.getLeader().getConnection().getIPBytes());
		writeH(29890);// порт откуда идет
		writeB(room.getLeader().getConnection().getIPBytes());
		writeH(29890);// порт куда идет
		writeC(0);

		writeB(player.getConnection().getIPBytes());
		writeH(29890);// порт
		writeB(player.getConnection().getIPBytes());
		writeH(29890); // порт
		writeC(0);
		writeB(NetworkUtil.parseIpToBytes("107.6.122.139"));
		writeH(40000);
		/*writeB(new byte[]{
				0x19,
				0x72,
				(byte) 0x6E,
				(byte) 0x99
		}); // Начиная от суда 4,2,4,4 байтов читается, потом в каком то случае 35 байт */
		writeB(new byte[]
				{
						//
						// (byte) 0xc3, 0x51, 0x74, 0x36, // айпи чего?
						//
						//0x43,
						//(byte) 0x9c, // 40000 - порт?
						//
						(byte) 0x91,
						0x00,
						0x00,
						0x00,
						//
						(byte) 0x47,
						0x06,
						0x00,
						0x00
				});

		writeC(0);

		writeB(new byte[]
				{
						0x0a,
						0x22,
						0x00,
						0x01,
						0x10,
						0x03,
						0x1e,
						0x05,
						0x06,
						0x07,
						0x04,
						0x09,
						0x16,
						0x0b,
						0x1b,
						0x08,
						0x0e,
						0x0f,
						0x02,
						0x11,
						0x12,
						0x21,
						0x14,
						0x15,
						0x13,
						0x17,
						0x18,
						0x19,
						0x1a,
						0x0c,
						0x1c,
						0x1d,
						0x0d,
						0x1f,
						0x20
				});
	}
}