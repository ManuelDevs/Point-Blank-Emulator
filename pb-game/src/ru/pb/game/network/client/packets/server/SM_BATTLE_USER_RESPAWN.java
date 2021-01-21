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

/**
 * Неизместный пакет, отправляется вместе с пекетов авторизации
 *
 * @author sjke
 */
public class SM_BATTLE_USER_RESPAWN extends ServerPacket {

	private int slotId, first, second, third, fourth, fifth, id, red, blue, head, beret, dino;

	public SM_BATTLE_USER_RESPAWN(int slotId, int first, int second, int third, int fourth, int fifth, int id,
								  int red, int blue, int head, int beret, int dino) {
		super(0xD0A);
		this.slotId = slotId;
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
		this.fifth = fifth;
		this.id = id;
		this.red = red;
		this.blue = blue;
		this.head = head;
		this.beret = beret;
		this.dino = dino;
	}

	@Override
	public void writeImpl() {
		writeD(slotId);
		writeD(1);
		writeD(1);
		writeD(first);
		writeD(second);
		writeD(third);
		writeD(fourth);
		writeD(fifth);
		writeD(id);
		writeB(new byte[]{0x64, 0x64, 0x64, 0x64, 0x64, 0x01});
		writeD(red);
		writeD(blue);
		writeD(head);
		writeD(beret);
		writeD(dino);
	}
}