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

package ru.pb.game.network.client.packets.client;

import ru.pb.game.controller.BattleServerController;
import ru.pb.game.network.battle.packets.sendable.SM_REQUEST_REMOVE_PLAYER;
import ru.pb.game.network.battle.packets.sendable.SM_REQUEST_UNREGISTER_ROOM;
import ru.pb.game.network.client.packets.ClientPacket;
import ru.pb.game.network.client.packets.server.PROTOCOL_LOBBY_ENTER_ACK;
import ru.pb.game.network.client.packets.server.SM_ROOM_INFO;
import ru.pb.global.models.BattleServerInfo;
import ru.pb.global.models.Channel;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;

public class PROTOCOL_LOBBY_ENTER_REQ extends ClientPacket {

	public PROTOCOL_LOBBY_ENTER_REQ(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
	}

	@Override
	public void runImpl() {
		Player player = getConnection().getPlayer();
		Room room = getConnection().getRoom();
		Channel ch = getConnection().getServerChannel();

		if(room != null && player != null) {
			room.removePlayer(player);
			if(ch != null) {
				BattleServerInfo bsi = BattleServerController.getInstance().getBattleServerInfo(40000);
				if(bsi != null) {
					if(bsi.getConnection() != null) {
						bsi.getConnection().sendPacket(new SM_REQUEST_REMOVE_PLAYER(room, getConnection().getIPBytes(), ch.getId()));
					}
				}

				for(Player p : room.getPlayers().values()) {
					p.getConnection().sendPacket(new SM_ROOM_INFO(room));
				}

				if(bsi != null) {
					if(bsi.getConnection() != null) {
						if(room.getPlayers().isEmpty())
							bsi.getConnection().sendPacket(new SM_REQUEST_UNREGISTER_ROOM(room, ch.getId()));
					}
				}
				ch.removeRoom(room);
			}
			getConnection().setRoom(null);
		}

		if(ch != null) {
			if(player != null && !ch.getPlayers().containsKey(player.getId()) && !player.getName().isEmpty()) {
				ch.addPlayer(player);
			}
		}
		
		sendPacket(new PROTOCOL_LOBBY_ENTER_ACK());
	}
}