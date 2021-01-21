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

import ru.pb.auth.network.client.packets.ServerPacket;
import ru.pb.global.enums.item.ItemConsumeType;
import ru.pb.global.enums.item.ItemType;
import ru.pb.global.models.PlayerEqipment;
import ru.pb.global.models.PlayerItem;

public class PROTOCOL_UNK_2679_ACK extends ServerPacket {

	public PROTOCOL_UNK_2679_ACK() {
		super(2679);
	}

	@Override
	public void writeImpl() {
         writeD(8);
         writeC(1);
         writeC(5);

         writeH(1);
         writeH(15);
         writeH(42);
         writeH(0);

         writeH(1012);
         writeH(12);

         writeC(5);
         writeS("Mar  2 2017", 11);
         writeD(0);
         writeS("11:10:23", 8);
         writeB(new byte[7]);
         writeS("DIST", 4);
         writeH(0);
	}
}
