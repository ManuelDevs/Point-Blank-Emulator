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
import ru.pb.global.models.Item;
import ru.pb.global.utils.NetworkUtil;

import java.util.Collection;
import java.util.List;

/**
 * Список итемов
 *
 * @author Felixx
 */
public class SM_SHOP_GET_ITEMS extends ServerPacket {
	private Collection<Item> templates;

	public SM_SHOP_GET_ITEMS(Collection<Item> templates) {
		super(0x20D);
		this.templates = templates;
	}

	@Override
	public void writeImpl() {
		writeD(templates.size()); // кол-во в магазине
		writeD(templates.size()); // кол-во для отсылки
		writeD(0); // сколько уже было отослано
		for (Item item : templates) {
			writeD(item.getId()); // айди предмета
			writeC(item.getConsumeType().getValue()); // life_Type время жизни (3 - постоянный, 2-на время, 1-на QTY, 0 - пусто)
			writeC(1); // Кол - во? quantity?
			writeC(2); // unk
			writeC(0); // unk
		}
		writeD((NetworkUtil.UNBUFFERED_STATE));
	}
}