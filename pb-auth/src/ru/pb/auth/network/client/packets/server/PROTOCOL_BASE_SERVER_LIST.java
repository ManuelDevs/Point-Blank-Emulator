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
 * Authors: DarkSkeleton
 * Copyright (C) 2013 PBDev
 */

package ru.pb.auth.network.client.packets.server;

import ru.pb.auth.controller.GameServerController;
import ru.pb.auth.network.client.packets.ServerPacket;
import ru.pb.global.models.GameServerInfo;

public class PROTOCOL_BASE_SERVER_LIST extends ServerPacket {

	public PROTOCOL_BASE_SERVER_LIST() {
		super(2049);
	}

	@Override
	public void writeImpl() {
		writeD(con.getId());
		writeB(con.getIPBytes());
		writeH(con.getSecurityKey());
		writeH(0);
		for (int i = 0; i < 10; i++) 
			writeC(1);
		writeC(1);
		writeD(GameServerController.getInstance().getServers().size() + 1); 
		writeFakeServer();
		for (GameServerInfo info : GameServerController.getInstance().getServers()) {
			writeBool(info.getConnection() != null);
			writeB(info.getConnection() == null ? new byte[] {127, 0, 0, 1} : info.getConnection().getIPBytes());
			writeH(info.getPort());
			writeC(info.getType().getValue());
			writeH(info.getMaxPlayer());
			writeD(info.getOnline().get());
		}
	}
	
	private void writeFakeServer()
	{
		writeBool(false);
		writeB(new byte[] {127, 0, 0, 1});
		writeH(0);
		writeC(5);
		writeH(0);
		writeD(0);
	}
}
