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

public class PROTOCOL_BATTLE_PRESTARTBATTLE_ACK extends ServerPacket {

	private final Player player;
	private final Room room;
	private final boolean isPreparing;
	private final boolean loadHits;
	
	public PROTOCOL_BATTLE_PRESTARTBATTLE_ACK(Room room, Player player, boolean isPreparing, boolean loadHits) {
		super(0xD15);
		this.player = player;
		this.room = room;
		this.isPreparing = isPreparing;
		this.loadHits = loadHits;
	}

	@Override
	public void writeImpl() {
		writeBool(isPreparing);
		if(!isPreparing) 
			return;
		
		writeD(room.getRoomSlotByPlayer(player).getId());
		writeC(room.isBot() ? 2 : 3);
		System.out.println(" Starting room with udp: " + (room.isBot() ? 2 : 3));
		
		writeB(room.getLeader().getConnection().getIPBytes());
		writeH(29890);
		writeB(room.getLeader().getConnection().getIPBytes());
		writeH(29890);
		writeC(0);

		writeB(player.getConnection().getIPBytes());
		writeH(29890);
		writeB(player.getConnection().getIPBytes());
		writeH(29890);
		writeC(0);
		writeB(NetworkUtil.parseIpToBytes("127.0.0.1"));
		writeH(40009);
		
		int gameServerId = 1;
		int uniqueId = ((gameServerId & 0xff) << 20 | (room.getChannel() & 0xff) << 12 | room.getId() & 0xfff);
		
		writeD(uniqueId);
		writeD((room.getMapId() * 16) + room.getType());
		
		if(loadHits)
		{
			writeB(new byte[]
			{
					(byte) 0x20, (byte) 0x15, (byte) 0x16, (byte) 0x17, 
		            (byte) 0x18, (byte) 0x19, (byte) 0x11, (byte) 0x1B, 
		            (byte) 0x1C, (byte) 0x1D, (byte) 0x1A, (byte) 0x1F, 
		            (byte) 0x09, (byte) 0x21, (byte) 0x0E, (byte) 0x1E, 
		            (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, 
		            (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, 
		            (byte) 0x14, (byte) 0x0A, (byte) 0x0B, (byte) 0x0C, 
		            (byte) 0x0D, (byte) 0x22, (byte) 0x0F, (byte) 0x10, 
		            (byte) 0x00, (byte) 0x12, (byte) 0x13
			});
		}
	}
}