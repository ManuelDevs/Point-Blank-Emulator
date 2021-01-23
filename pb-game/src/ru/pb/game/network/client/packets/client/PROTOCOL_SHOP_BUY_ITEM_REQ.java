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
import ru.pb.game.network.client.packets.server.PROTOCOL_BUY_ITEM_ACK;
import ru.pb.global.data.holder.ItemHolder;
import ru.pb.global.enums.ItemState;
import ru.pb.global.enums.item.ItemLocation;
import ru.pb.global.models.Good;
import ru.pb.global.models.PlayerItem;
import ru.pb.global.service.EquipmentDaoService;
import ru.pb.global.utils.NetworkUtil;

public class PROTOCOL_SHOP_BUY_ITEM_REQ extends ClientPacket {

	private int id, buyType;
	
	public PROTOCOL_SHOP_BUY_ITEM_REQ(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
		int count = readC();
		
		id = readD();
		buyType = readC();
	}

	@Override
	public void runImpl() {
		Good item = GoodsController.getInstance().getGoodItemById(id);
		if (item == null || getConnection().getPlayer().getGp() < item.getPricePoints() || getConnection().getAccount().getMoney() < item.getPriceCredits()) {
			sendPacket(new PROTOCOL_BUY_ITEM_ACK(0x80001019, null, null));
		} else {
			PlayerItem playerItem = getConnection().getPlayer().getEqipment().getItemByItemId(item.getItem().getId());
			
			if(playerItem == null)
			{
				playerItem = ItemHolder.getInstance().createItem(ItemLocation.INVENTORY, item.getItem().getId(), item.getQuantity());
				EquipmentDaoService.getInstance().registerItem(getConnection().getPlayer(), playerItem);
				
				getConnection().getPlayer().getEqipment().addItem(playerItem);
				getConnection().getPlayer().setGp(getConnection().getPlayer().getGp() - item.getPricePoints());
				getConnection().getAccount().setMoney(getConnection().getAccount().getMoney() - item.getPriceCredits());
			}
			else
			{
				playerItem.setFlag(ItemState.UPDATE);
				playerItem.addCount(item.getQuantity());
				getConnection().getPlayer().setGp(getConnection().getPlayer().getGp() - item.getPricePoints());
				getConnection().getAccount().setMoney(getConnection().getAccount().getMoney() - item.getPriceCredits());
			}
			sendPacket(new PROTOCOL_BUY_ITEM_ACK(0, playerItem, getConnection().getPlayer()));
		}
	}
}