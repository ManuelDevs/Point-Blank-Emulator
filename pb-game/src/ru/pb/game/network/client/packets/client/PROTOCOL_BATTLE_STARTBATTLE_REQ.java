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
import ru.pb.game.network.client.packets.server.*;
import ru.pb.global.enums.RoomErrorMessage;
import ru.pb.global.enums.RoomState;
import ru.pb.global.enums.SlotState;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;
import ru.pb.global.models.RoomSlot;
import ru.pb.global.utils.concurrent.ThreadPoolManager;

public class PROTOCOL_BATTLE_STARTBATTLE_REQ extends ClientPacket {
	public PROTOCOL_BATTLE_STARTBATTLE_REQ(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
		
	}

	@Override
	public void runImpl() {
		Player player = getConnection().getPlayer();
		Room room = getConnection().getRoom();
		
		if(player == null)
			return;
		
		if(room != null && room.isPreparing())
		{
			RoomSlot slot = room.getRoomSlotByPlayer(player);
			if(slot == null)
				return;
			
			if(slot.getState() == SlotState.SLOT_STATE_PRESTART)
			{
				room.updateRoomSlotState(slot, SlotState.SLOT_STATE_BATTLE_READY);
				if(room.isBot())
					sendPacket(new PROTOCOL_BATTLE_CHANGE_DIFFICULTY_LEVEL_ACK(room));
				sendPacket(new PROTOCOL_BATTLE_ROOM_INFO_ACK(room));
			}
			else 
			{
				sendPacket(new PROTOCOL_SERVER_MESSAGE_KICK_BATTLE_PLAYER_ACK(RoomErrorMessage.BATTLE_FIRST_HOLE.get())); // TODO: Leave p2p
				room.updateRoomSlotState(slot, SlotState.SLOT_STATE_NORMAL);
				// TODO: End player count??
				return;
			}
			
			int blueReady = 0, redReady = 0;
			int blueLoad = 0, redLoad = 0;
			int total = 0;
			
			for (int i = 0; i < 16; i++)
            {
                if(room.getRoomSlot(i) == null)
                	continue;
                int slotState = room.getRoomSlot(i).getState().ordinal();
                
                if (slotState >= 9)
                {
                    total++;
                    if (i % 2 == 0) 
                    	redLoad++;
                    else 
                    	blueLoad++;
                    
                    if ((int)slotState >= 12)
                    {
                        if (i % 2 == 0) 
                        	redReady++;
                        else 
                        	blueReady++;
                    }
                }
            }
			
			boolean testMode = false;
			int udpType = 3;
			if(room.getState().ordinal() == 5 || 
					room.getRoomSlotByPlayer(room.getLeader()).getState().ordinal() >= 12 && room.isBot() &&
					(room.getRoomSlotByPlayer(room.getLeader()).getId() % 2 == 0 && redReady > redLoad / 2 || room.getRoomSlotByPlayer(room.getLeader()).getId() % 2 == 1 && blueReady > blueLoad / 2) ||
					room.getRoomSlotByPlayer(room.getLeader()).getState().ordinal() >= 12 &&
					((!testMode || udpType != 3) &&
					blueReady > blueLoad / 2 && redReady > redLoad / 2 ||
					testMode && udpType == 3))
			{
				this.spawnPlayers();
			}
			else log.info("BATTLE_START Not ready to go. [Ready: " + redReady + "|" + blueReady + "; Load: " + redLoad + "|" + blueLoad);
		}
		else
		{
			sendPacket(new PROTOCOL_SERVER_MESSAGE_KICK_BATTLE_PLAYER_ACK(RoomErrorMessage.BATTLE_FIRST_HOLE.get()));
			sendPacket(new PROTOCOL_BATTLE_STARTBATTLE_ACK());
			if(room != null)
				room.updateRoomSlotState(room.getRoomSlotByPlayer(player), SlotState.SLOT_STATE_NORMAL);
			else
			{
				sendPacket(new PROTOCOL_LOBBY_ENTER_ACK());
			}
			log.info("BATTLE_START failed due to null room or not preparing room");
		}
	}
	
	private void spawnPlayers()
	{
		log.info("Spawning player");
		
		Room room = getConnection().getRoom();
		for (int i = 0; i < 16; i++)
        {
			RoomSlot slot = room.getRoomSlot(i);
			if(slot.getState().ordinal() == 12 && slot.getPlaying() == 0 && slot.getPlayer() != null)
			{
				slot.setPlaying(1);
				slot.setState(SlotState.SLOT_STATE_BATTLE);
			}
        }
		
		if(room.getState() == RoomState.PRE_BATTLE)
			room.setTimeLost(room.getTimeByMask() * 60);
		room.updateSlotsWithPacket();
		
		for (int i = 0; i < 16; i++)
        {
			RoomSlot slot = room.getRoomSlot(i);
			if(slot.getState().ordinal() == 13 && slot.getPlaying() == 1 && slot.getPlayer() != null)
			{
				slot.setPlaying(2);
				if(room.getState() == RoomState.PRE_BATTLE)
				{
					for(Player toSend : room.getPlayersByState(SlotState.SLOT_STATE_READY, 1))
						toSend.getConnection().sendPacket(new PROTOCOL_BATTLE_STARTBATTLE_ACK(room, slot.getPlayer(), true));
					slot.getPlayer().getConnection().sendPacket(new PROTOCOL_BATTLE_ROUND_RESTART_ACK(room));
					if(room.getType() == 7 || room.getType() == 12)
					{
						
					}
					else slot.getPlayer().getConnection().sendPacket(new PROTOCOL_BATTLE_TIMERSYNC_ACK(room));
					log.info("Spawning player with PRE_BATTLE state");
				}
				else if(room.getState() == RoomState.BATTLE)
				{
					for(Player toSend : room.getPlayersByState(SlotState.SLOT_STATE_READY, 1))
						toSend.getConnection().sendPacket(new PROTOCOL_BATTLE_STARTBATTLE_ACK(room, slot.getPlayer(), false));
					if(room.getType() == 2 || room.getType() == 4)
					{
						
					}
					else slot.getPlayer().getConnection().sendPacket(new PROTOCOL_BATTLE_ROUND_RESTART_ACK(room));
					 slot.getPlayer().getConnection().sendPacket(new PROTOCOL_BATTLE_TIMERSYNC_ACK(room));
					 // TODO: Send battle record
					log.info("Spawning player with BATTLE state");
				}
			}
        }
		
		if(room.getState() == RoomState.PRE_BATTLE)
		{
			log.info("State of battle updated to BATTLE");
			room.setState(RoomState.BATTLE);
			for(Player member : room.getPlayers().values())
				member.getConnection().sendPacket(new PROTOCOL_BATTLE_ROOM_INFO_ACK(room));
		}
		
    }
}