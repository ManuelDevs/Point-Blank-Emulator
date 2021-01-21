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

import ru.pb.game.controller.GoodsController;
import ru.pb.game.network.client.packets.ClientPacket;
import ru.pb.game.network.client.packets.server.*;
import ru.pb.global.enums.item.ItemSlotType;
import ru.pb.global.enums.item.ItemType;
import ru.pb.global.models.Good;
import ru.pb.global.models.Item;
import ru.pb.global.models.PlayerEqipment;
import ru.pb.global.models.PlayerItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Показывает итемы в магазине.
 *
 * @author Felixx
 */
public class CM_SHOP_LIST extends ClientPacket {
	private int error;

	public CM_SHOP_LIST(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
		error = readD(); // error ?
	}

	@Override
	public void runImpl() {
		if (error == 0) {
			Collection<Good> allGoods = GoodsController.getInstance().getAllGoods();
			Collection<Item> gallItems = GoodsController.getInstance().getAllUsedItems();

			sendPacket(new SM_SHOP_GET_ITEMS(gallItems));
			sendPacket(new SM_SHOP_GET_GOODS(allGoods));
			sendPacket(new SM_SHOP_TEST_1());
			sendPacket(new SM_SHOP_TEST_2());
			sendPacket(new SM_SHOP_GET_MATCHING(allGoods, getConnection().getPlayer().getEqipment()));

			/*PlayerEqipment eq = getConnection().getPlayer().getEqipment();
			for (ItemType t : ItemType.values()) {
				for (PlayerItem i : eq.getItemsByType(t)) {
					Good g = GoodsController.getInstance().getGoodItemByItemId(i.getItem().getId());
					if (g != null) {
						int stock = g.getStockType();
						if (i.getItem().getConsumeType().getValue() != stock) {
							log.info("BAD ITEM: " + i.getItem().getId() + "; stock=" + stock + "; ctype: " + i.getItem().getConsumeType().getValue());
						}
						else{
							log.info("GOOD ITEM: " + i.getItem().getId() + "; stock=" + stock + "; ctype: " + i.getItem().getConsumeType().getValue());
						}
					}
					else{
						log.info("IS NO GOOD ITEM: " + i.getItem().getId());
					}
				}
			}

			for (ItemSlotType t : ItemSlotType.values()) {
				PlayerItem i = eq.getEquippedItemBySlot(t);
				if(i != null){
					Good g = GoodsController.getInstance().getGoodItemByItemId(i.getItem().getId());
					if (g != null) {
						int stock = g.getStockType();
						if (i.getItem().getConsumeType().getValue() != stock) {
							log.info("BAD EQUIPPED ITEM: " + i.getItem().getId() + "; stock=" + stock + "; ctype: " + i.getItem().getConsumeType().getValue());
						}
						else{
							log.info("GOOD EQUIPPED ITEM: " + i.getItem().getId() + "; stock=" + stock + "; ctype: " + i.getItem().getConsumeType().getValue());
						}
					}
					else{
						log.info("IS NO GOOD EQUIPPED ITEM: " + i.getItem().getId());
					}
				}
			}*/
		}
		sendPacket(new SM_SHOP_LIST());
	}
}