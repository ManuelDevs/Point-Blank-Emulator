/*
 * Java Server Emulator Project Blackout / PointBlank
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * Authors: DarkSkeleton, sjke, Felixx
 * Copyright (C) 2013 PBDev™
 */

package ru.pb.game.network.client.packets.server;

import ru.pb.game.network.client.packets.ServerPacket;
import ru.pb.global.models.Frag;
import ru.pb.global.models.FragInfos;
import ru.pb.global.models.Room;
import ru.pb.global.models.RoomSlot;

/**
 * @author: Felixx
 *          Date: 02.10.13
 *          Time: 6:48
 */
public class PROTOCOL_BATTLE_FRAG_INFO_ACK extends ServerPacket {

	private Room room;
	private FragInfos fragInfos;

	public PROTOCOL_BATTLE_FRAG_INFO_ACK(Room room, FragInfos fragInfos) {
		super(0xD1B);
		this.room = room;
		this.fragInfos = fragInfos;
	}

	@Override
	public void writeImpl() {
		writeC(fragInfos.getVicTimIdx());
		writeC(fragInfos.getKillsCount());
		writeC(fragInfos.getKiller());
		writeD(fragInfos.getKillWeaapon()); 
		writeB(fragInfos.getUnkBytes());

		RoomSlot killer = room.getRoomSlot(fragInfos.getKiller());

		for(int i = 1; i <= fragInfos.getKillsCount(); i++) {
			Frag frag = fragInfos.getFrag(i);
			writeC(frag.getUnkC1());
			writeC(frag.getDeathMask()); //

			switch(killer.getKillMessage())
				{
					case 0:
						writeH(0);
						break;
					case 1:
						writeH(1);
						break;
					case 2:
						writeH(2);
						break;
					case 3:
						writeH(4);
						break;
					case 4:
						writeH(8);
						break;
					case 5:
						writeH(16);
						break;
					case 6:
						writeH(32);
						break;
					case 7:
						writeH(64);
						break;
					case 8:
						writeH(128);
						break;
					default:
						writeH(0);
						break;
				}
			writeB(frag.getUnk13bytes());
		}

		writeD(room.getRedKills());
		writeD(room.getBlueKills());

		for(RoomSlot member : room.getRoomSlots()) {
			writeH(member.getAllKills());
			writeH(member.getAllDeath());
		}

		if(fragInfos.getKillsCount() == 1)
			if(killer.getOneTimeKills() == 1)
				writeC(0);
			else if(killer.getOneTimeKills() == 2)
				writeC(1);
			else if(killer.getOneTimeKills() == 3)
				writeC(2);
			else if(killer.getOneTimeKills() > 3)
				writeC(3);

		writeH(killer.getBotScore());
		int hz = 0;
		if(hz > 0)
			writeD(0);
	}
}