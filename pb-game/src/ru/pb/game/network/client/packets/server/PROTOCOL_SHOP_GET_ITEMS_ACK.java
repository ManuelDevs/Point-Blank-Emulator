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

package ru.pb.game.network.client.packets.server;

import ru.pb.game.network.client.packets.ServerPacket;
import ru.pb.global.enums.item.ItemType;
import ru.pb.global.models.Item;
import ru.pb.global.utils.NetworkUtil;

import java.util.Collection;

public class PROTOCOL_SHOP_GET_ITEMS_ACK extends ServerPacket {
	private Collection<Item> templates;

	public PROTOCOL_SHOP_GET_ITEMS_ACK(Collection<Item> templates) {
		super(0x20D);
		this.templates = templates;
	}

	@Override
	public void writeImpl() {
		writeD(templates.size());
		writeD(templates.size());
		writeD(0);
		for (Item item : templates) {
			writeD(item.getId());
			writeC(item.getConsumeType().getValue());
			writeC(1); 
			writeC(item.getItemType() == ItemType.COUPON ? 2 : 1);
			writeC(item.getRequiredTitle());
		}
		writeD((NetworkUtil.UNBUFFERED_STATE));
	}
}