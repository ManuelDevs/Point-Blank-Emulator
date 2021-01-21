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
import ru.pb.game.network.client.packets.server.SM_SHOP_BUY_ITEM;
import ru.pb.global.data.holder.ItemHolder;
import ru.pb.global.enums.ItemState;
import ru.pb.global.enums.item.ItemLocation;
import ru.pb.global.models.Good;
import ru.pb.global.models.PlayerItem;
import ru.pb.global.utils.NetworkUtil;

/**
 * Неизветный пакет
 *
 * @author sjke
 */
public class CM_SHOP_BUY_ITEM extends ClientPacket {

	private int id;
	private boolean isEquipped = false;   // Где то тут должно слаться одеть сразу или нет. ну или при выходе из магаза.

	public CM_SHOP_BUY_ITEM(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
		log.info("C:" + readC());
		id = readD();
		log.info("D - ShopID:" + id);
		log.info("C:" + readC());
		log.info("D:" + readD());
		log.info("Q:" + readQ());
		if (getBuf().readableBytes() > 0)
			NetworkUtil.printData("DATA:", getBuf());
	}

	@Override
	public void runImpl() {
		Good item = GoodsController.getInstance().getGoodItemById(id);
		if (item == null || getConnection().getPlayer().getGp() < item.getPricePoints() || getConnection().getAccount().getMoney() < item.getPriceCredits()) {
			sendPacket(new SM_SHOP_BUY_ITEM(0x80001019, null, null));
		} else {
			PlayerItem playerItem = ItemHolder.getInstance().createItem(ItemLocation.INVENTORY, item.getItem().getId(), 1);
			playerItem.setFlag(ItemState.INSERT);
			getConnection().getPlayer().getEqipment().addItem(playerItem);
			getConnection().getPlayer().setGp(getConnection().getPlayer().getGp() - item.getPricePoints());
			getConnection().getAccount().setMoney(getConnection().getAccount().getMoney() - item.getPriceCredits());
			sendPacket(new SM_SHOP_BUY_ITEM(0, playerItem, getConnection().getPlayer()));
		}
	}
}