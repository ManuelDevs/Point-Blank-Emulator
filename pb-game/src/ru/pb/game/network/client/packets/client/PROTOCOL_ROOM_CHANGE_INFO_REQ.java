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

public class PROTOCOL_ROOM_CHANGE_INFO_REQ extends ClientPacket {

	private Room room;

	public PROTOCOL_ROOM_CHANGE_INFO_REQ(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
		room = getConnection().getRoom();
		if(room == null)
			return;
		readD();
		room.setName(readS(23));
		room.setMapId(readH());
		room.setStage4v4(readC());
		room.setType(readC());
		readC();
		readC();
		readC();
		room.setPing(readC());
		room.setAllWeapons(readC());
		room.setRandomMap(readC());
		room.setSpecial(readC());
		readS(Player.MAX_NAME_SIZE);
		room.setKillMask(readC());
		readC();
		readC();
		readC();
		room.setLimit(readC());
		room.setSeeConf(readC());
		room.setAutobalans(readH());
		room.setLeader(getConnection().getPlayer());
		room.addPlayer(getConnection().getPlayer());
		room.setAiCount(readC());
		room.setAiLevel(readC());
	}

	@Override
	public void runImpl() {
		if(room == null)
			return;
		
		for (Player member : getConnection().getRoom().getPlayers().values()) {
			member.getConnection().sendPacket(new PROTOCOL_BATTLE_ROOM_INFO_ACK(room));
		}
	}
}