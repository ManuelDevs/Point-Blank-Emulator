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

public class PROTOCOL_BASE_EXIT_URL_ACK extends ServerPacket {

	public PROTOCOL_BASE_EXIT_URL_ACK() {
		super(2694);
	}

	@Override
	public void writeImpl() {
         writeC(0); // count
         
         /*
            count: 1
            writeD(1);
            writeD((int)ClientLocale.Brazil);
            writeS(link, 256);
          */
	}
}
