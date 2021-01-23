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

public class PROTOCOL_BATTLE_ROOM_INFO_ACK extends ServerPacket {

	private final Room room;
	public PROTOCOL_BATTLE_ROOM_INFO_ACK(Room room) {
		super(3848);
		
		this.room = room;
	}

	@Override
	public void writeImpl() {
		writeD(room.getId());
		writeS(room.getName(), Room.ROOM_NAME_SIZE);
		writeH(room.getMapId());
		writeC(room.getStage4v4());
		writeC(room.getType());
		writeC(room.getState().ordinal());
		writeC(room.getPlayers().size());
		writeC(room.getValidSlots());
		writeC(room.getPing());
		writeC(room.getAllWeapons());
		writeC(room.getRandomMap());
		writeC(room.getSpecial());
		writeS(room.getLeader() == null ? "" : room.getLeader().getName(), Player.MAX_NAME_SIZE);
		writeD(room.getKillMask());
		writeC(room.getLimit());
		writeC(room.getSeeConf());
		writeH(room.getAutobalans().ordinal());
		if(room.isBot())
		{
			writeC(room.getAiCount());
			writeC(room.getAiLevel());
		}
	}
}