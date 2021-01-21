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
import ru.pb.game.network.client.packets.server.SM_CLAN_CREATE_NICK_NAME;

/**
 * Проверка названия клана
 *
 * @author DarkSkeleton
 */
public class CM_CLAN_CREATE_NICK_NAME extends ClientPacket {

	public CM_CLAN_CREATE_NICK_NAME(int opcode) {
		super(opcode);
	}

	@Override
	protected void readImpl() {
		String clanName = readS(readC());//название клана для его создания
	}

	@Override
	protected void runImpl() {
		sendPacket(new SM_CLAN_CREATE_NICK_NAME());
	}
}