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
import ru.pb.game.network.client.packets.server.PROTOCOL_BASE_ENTER_ACK;
import ru.pb.global.models.Player;
import ru.pb.global.service.AccountDaoService;
import ru.pb.global.service.PlayerDaoService;

public class PROTOCOL_BASE_ENTER_REQ extends ClientPacket {

	private String login;

	public PROTOCOL_BASE_ENTER_REQ(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
		login = readS(readC()).trim();
		readQ();
		readC();
		readB(4);
	}

	@Override
	public void runImpl() {
		if (getConnection().getAccount() == null) {
			getConnection().setAccount(AccountDaoService.getInstance().readByLogin(login));
			Player player = PlayerDaoService.getInstance().read(getConnection().getAccount().getId());
			if (player != null) {
				getConnection().setPlayer(player);
			}
		}
		sendPacket(new PROTOCOL_BASE_ENTER_ACK());
	}
}