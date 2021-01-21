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
import ru.pb.global.models.Good;
import ru.pb.global.utils.NetworkUtil;

import java.util.Collection;
import java.util.List;

public class PROTOCOL_SHOP_GET_GOODS_ACK extends ServerPacket {
	private Collection<Good> goods;

	public PROTOCOL_SHOP_GET_GOODS_ACK(Collection<Good> goods) {
		super(0x20B);
		this.goods = goods;
	}

	@Override
	public void writeImpl() {
		writeD(goods.size());
		writeD(goods.size());
		writeD(0);
		for (Good good : goods) {
			writeD(good.getGoodId());
			writeC(1); 
			writeC(1); // 4 | 1 TODO: Understand how use this value
			writeD(good.getPricePoints());
            writeD(good.getPriceCredits());
			writeC(good.getStockType());
		}
		writeD(NetworkUtil.UNBUFFERED_STATE);
	}
}