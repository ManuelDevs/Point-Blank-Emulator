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

package ru.pb.game.controller;

import ru.pb.global.controller.BaseController;
import ru.pb.global.dao.DaoManager;
import ru.pb.global.models.Good;
import ru.pb.global.models.Item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Контроллер для магазина
 *
 * @author sjke
 */
public class GoodsController extends BaseController {

	private static Map<Integer, Good> goods;
	private static Map<Integer, Item> items = new ConcurrentSkipListMap<Integer, Item>();

	private GoodsController() {
		goods = DaoManager.getInstance().getShopDao().getAllGoods();
		for(Good g : goods.values()){
			if(!goods.containsKey(g.getItem().getId())){
				items.put(g.getItem().getId(), g.getItem());
			}else{
				log.info("Item: " + g.getItem().getId() + " already in collection.");
			}
		}
		log.info("Loaded " + goods.size() + " goods.");
		log.info("Loaded " + items.size() + " used items.");
	}

	public static GoodsController getInstance() {
		return Singleton.INSTANCE;
	}

	private static class Singleton {
		private static final GoodsController INSTANCE = new GoodsController();
	}

	public Collection<Good> getAllGoods() {
		return goods.values();
	}

	public Collection<Item> getAllUsedItems(){
		return items.values();
	}

	public Good getGoodItemById(int id) {
		for (Good item : goods.values()) {
			if (item.getGoodId() == id) {
				return item;
			}
		}
		return null;
	}

	public Good getGoodItemByItemId(int id){
		for (Good item : goods.values()) {
			if (item.getItem().getId() == id) {
				return item;
			}
		}
		return null;
	}
}
