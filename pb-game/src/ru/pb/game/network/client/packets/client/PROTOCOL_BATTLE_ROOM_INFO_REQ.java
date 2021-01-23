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
 * Copyright (C) 2013 PBDevâ„¢
 */

package ru.pb.game.network.client.packets.client;

import ru.pb.game.network.client.packets.ClientPacket;
import ru.pb.game.network.client.packets.server.PROTOCOL_BATTLE_ROOM_INFO_ACK;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;

public class PROTOCOL_BATTLE_ROOM_INFO_REQ extends ClientPacket {
	
	private Room room;
	
	public PROTOCOL_BATTLE_ROOM_INFO_REQ(int opcode) {
		super(opcode);
	}
	@Override
	public void readImpl() {
		if(getConnection() == null)
			return;
		
		room = getConnection().getRoom();
		Player player = getConnection().getPlayer();
		if(room == null)
			return;
		
		readD();
		room.setName(readS(23));
		room.setMapId(readH());
		room.setStage4v4(readC());
		room.setType(readC());
		readB(3);
		room.setPing(readC());
		room.setAllWeapons(readC());
		room.setRandomMap(readC());
		room.setSpecial(readC());
		room.setAiCount(readC());
		room.setAiLevel(readC());
	}

	@Override
	public void runImpl() {
		if(room == null)
			return;
		
		for(Player member : room.getPlayers().values())
		{
			member.getConnection().sendPacket(new PROTOCOL_BATTLE_ROOM_INFO_ACK(room));
		}
	}
}