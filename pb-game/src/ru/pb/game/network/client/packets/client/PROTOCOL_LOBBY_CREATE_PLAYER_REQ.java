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

import ru.pb.game.network.client.packets.ClientPacket;
import ru.pb.game.network.client.packets.server.PROTOCOL_LOBBY_CREATE_PLAYER_ACK;
import ru.pb.game.network.client.packets.server.SM_UNK_2596;
import ru.pb.global.models.Player;
import ru.pb.global.service.PlayerDaoService;

public class PROTOCOL_LOBBY_CREATE_PLAYER_REQ extends ClientPacket {
	private String playerName;

	public PROTOCOL_LOBBY_CREATE_PLAYER_REQ(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
		int length = readC();
		playerName = readS(length - 1);
	}

	@Override
	public void runImpl() {
		boolean status = PlayerDaoService.getInstance().playerExist(playerName);
		if(status) {
			sendPacket(new PROTOCOL_LOBBY_CREATE_PLAYER_ACK(0x80000113));
		} else {
			Player player = PlayerDaoService.getInstance().read(getConnection().getAccount().getId());
			if(player == null) {
				sendPacket(new PROTOCOL_LOBBY_CREATE_PLAYER_ACK(0x4a100080));
			} else {
				player.setName(playerName);
				getConnection().setPlayer(player);
				getConnection().getServerChannel().addPlayer(player);
				sendPacket(new PROTOCOL_LOBBY_CREATE_PLAYER_ACK(0));
				sendPacket(new SM_UNK_2596());
			}
		}
	}
}