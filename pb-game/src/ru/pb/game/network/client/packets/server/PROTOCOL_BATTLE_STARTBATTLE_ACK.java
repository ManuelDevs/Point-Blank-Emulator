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

public class PROTOCOL_BATTLE_STARTBATTLE_ACK extends ServerPacket {

	private Player player;
	private Room room;
	private int type;
	private boolean isBattle;
	
	public PROTOCOL_BATTLE_STARTBATTLE_ACK(Room room, Player player, boolean type) {
		super(3334);
		this.player = player;
		this.room = room;
		this.type = type ? 0 : 1;
		this.isBattle = true;
	}
	
	public PROTOCOL_BATTLE_STARTBATTLE_ACK() {
		super(3334);
		this.isBattle = false;
	}

	@Override
	public void writeImpl() {
		writeD(1);
		if(isBattle)
		{
			System.out.println("Type: " + type);
			writeD(room.getRoomSlotByPlayer(player).getId());
			writeC(type);
			writeH(room.getSlotFlag(true, false));
		}
	}
}