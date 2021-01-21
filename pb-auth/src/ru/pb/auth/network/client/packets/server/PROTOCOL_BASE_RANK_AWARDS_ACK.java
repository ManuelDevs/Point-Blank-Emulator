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

package ru.pb.auth.network.client.packets.server;

import ru.pb.auth.network.client.packets.ServerPacket;

public class PROTOCOL_BASE_RANK_AWARDS_ACK extends ServerPacket {

	public PROTOCOL_BASE_RANK_AWARDS_ACK() {
		super(2667);
	}

	@Override
	public void writeImpl() {
		for (int i = 0; i < 50; i++)
        {
            writeC((byte)(i + 1));
            writeD(0);
            writeD(0);
            writeD(0);
            writeD(0);
        }
	}
}