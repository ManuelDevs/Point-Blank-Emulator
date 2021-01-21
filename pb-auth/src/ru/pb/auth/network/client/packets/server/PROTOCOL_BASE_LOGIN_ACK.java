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

package ru.pb.auth.network.client.packets.server;

import ru.pb.auth.network.client.packets.ServerPacket;
import ru.pb.global.enums.LoginAccess;
import ru.pb.global.models.Account;

public class PROTOCOL_BASE_LOGIN_ACK extends ServerPacket {

	private LoginAccess access;
	private Account acc;

	public PROTOCOL_BASE_LOGIN_ACK(LoginAccess access, Account acc) {
		super(2564);
		this.access = access;
		this.acc = acc;
	}

	@Override
	public void writeImpl() {
		writeD(access.get());  // login result
		writeC(0);
		writeQ(acc != null ? acc.getId() : 0);
		writeC(acc != null ? acc.getLogin().length() : 0);
		writeS(acc != null ? acc.getLogin() : "", acc != null ? acc.getLogin().length() : 0); // loginName
		writeC(0);
		writeC(0);
	}
}
