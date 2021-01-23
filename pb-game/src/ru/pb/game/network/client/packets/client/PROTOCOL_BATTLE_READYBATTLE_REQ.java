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

package ru.pb.game.network.client.packets.client;

import ru.pb.game.network.client.packets.ClientPacket;
import ru.pb.global.enums.RoomState;
import ru.pb.global.enums.SlotState;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;
import ru.pb.global.models.RoomSlot;

public class PROTOCOL_BATTLE_READYBATTLE_REQ extends ClientPacket {
	
	private boolean startAsObserver;
	
	public PROTOCOL_BATTLE_READYBATTLE_REQ(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
		startAsObserver = readBool();
	}

	@Override
	public void runImpl() {
		Player player = getConnection().getPlayer();
		Room room = getConnection().getRoom();
		if(player == null || room == null || room.getLeader() == null)
			return;
		
		RoomSlot slot = room.getRoomSlotByPlayer(player);
		if(slot == null)
			return;
		
		if(room.getLeader().getId() == player.getId())
		{
			room.startRoom(false);
		}
		else
		{
			if(room.getState() == RoomState.READY || room.getState() == RoomState.COUNTDOWN)
			{
				slot.setState(slot.getState() == SlotState.SLOT_STATE_NORMAL ?  SlotState.SLOT_STATE_READY :  SlotState.SLOT_STATE_NORMAL);
			}
			else
			{
				slot.setState(SlotState.SLOT_STATE_LOAD);
			}
		}
	}
}