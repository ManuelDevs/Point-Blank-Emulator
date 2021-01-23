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
import ru.pb.global.models.Room;

public class PROTOCOL_BATTLE_CHANGE_DIFFICULTY_LEVEL_ACK extends ServerPacket {

	private final Room room;

	public PROTOCOL_BATTLE_CHANGE_DIFFICULTY_LEVEL_ACK(Room room) {
		super(3377);
		this.room = room;
	}

	@Override
	public void writeImpl() {
		writeC(room.getAiLevel() <= 10 ? room.getAiLevel() : 10);
		for (int i = 0; i < 8; i++) {
			writeD(1);
			writeD(1);
		}
	}
}