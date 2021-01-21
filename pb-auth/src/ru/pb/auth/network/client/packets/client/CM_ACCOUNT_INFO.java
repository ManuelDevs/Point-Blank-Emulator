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

package ru.pb.auth.network.client.packets.client;

import ru.pb.auth.network.client.packets.ClientPacket;
import ru.pb.auth.network.client.packets.server.SM_ACCOUNT_INFO;
import ru.pb.global.models.Player;
import ru.pb.global.service.PlayerDaoService;

/**
 * Запрос информации об аккаунте
 *
 * @author sjke
 */
public class CM_ACCOUNT_INFO extends ClientPacket {

	public CM_ACCOUNT_INFO(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
	}

	@Override
	public void runImpl() {
		Player p = PlayerDaoService.getInstance().read(getConnection().getAccount().getId());
		if (p == null) {
			p = PlayerDaoService.getInstance().createBasePlayer("", getConnection().getAccount().getId());
		}
		sendPacket(new SM_ACCOUNT_INFO(p, getConnection().getAccount()));
	}
}