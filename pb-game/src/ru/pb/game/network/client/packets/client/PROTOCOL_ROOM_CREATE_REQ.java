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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.pb.game.controller.BattleServerController;
import ru.pb.game.network.battle.packets.sendable.SM_REQUEST_REGISTER_ROOM;
import ru.pb.game.network.client.packets.ClientPacket;
import ru.pb.game.network.client.packets.server.PROTOCOL_ROOM_CREATE_ACK;
import ru.pb.global.models.BattleServerInfo;
import ru.pb.global.models.Channel;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;

public class PROTOCOL_ROOM_CREATE_REQ extends ClientPacket {
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	private Room room;
	private int error = 0;
	private boolean checkId = false;
	public PROTOCOL_ROOM_CREATE_REQ(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
		room = new Room();
		
		if (getConnection() != null) {
			for (int id = 0; id < Channel.MAX_ROOMS_COUNT; id++) {
				if (!getConnection().getServerChannel().getRooms().containsKey(id)) {
					room.setId(id);
					checkId = true;
					break;
				}
			}
		}
		
		if(!checkId)
		{
			room = null;
			error = 0x80000000;
			return;
		}
		
		readD();
		room.setName(readS(Room.ROOM_NAME_SIZE).trim());
		room.setMapId(readH());
		room.setStage4v4(readC());
		room.setType(readC());
		
		if(room.getType() == 0)
		{	
			room = null;
			error = 0x80000000;
			return;
		}
		
		readB(2);
		room.setSlots(readC());
		room.setPing(readC());
		room.setAllWeapons(readC());
		room.setRandomMap(readC());
		room.setSpecial(readC());
		
		if(getConnection().getServerChannel().getType().getValue() == 4 && room.isBot())
		{
			room = null;
			error = 0x8000107D;
			return;
		}
		
		readS(Player.MAX_NAME_SIZE);
		room.setKillMask(readC());
		readB(3);
		room.setLimit(readC());
		room.setSeeConf(readC());
		room.setAutobalans(readH());
		
		if(getConnection().getServerChannel().getType().getValue() == 4)
		{
			room.setLimit(1);
			room.setAutobalans(0);
		}
		
		room.setPassword(readS(Room.ROOM_PASSWORD_SIZE));
		
		if(room.isBot())
		{
			room.setAiCount(readC());
			room.setAiLevel(readC());
		}
		
		room.setLeader(getConnection().getPlayer());
		room.addPlayer(getConnection().getPlayer());
		room.setChannelId(getConnection().getServerChannel().getId());
	}

	@Override
	public void runImpl() {
		getConnection().setRoom(room);
		
		if(room != null)
		{
			Player player = getConnection().getPlayer();
			Channel ch = getConnection().getServerChannel();

			ch.removePlayer(player);
			ch.addRoom(room);
			

			BattleServerInfo bsi = BattleServerController.getInstance().getBattleServerInfo(40009);
			if (bsi != null) {
				if (bsi.getConnection() != null) {
					bsi.getConnection().sendPacket(new SM_REQUEST_REGISTER_ROOM(getConnection().getRoom(), getConnection().getServerChannel().getId()));
				}
			}
		}

		sendPacket(new PROTOCOL_ROOM_CREATE_ACK(error, room));
	}
}