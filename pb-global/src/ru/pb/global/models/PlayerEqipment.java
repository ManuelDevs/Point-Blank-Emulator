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

package ru.pb.global.models;

import ru.pb.global.enums.ItemState;
import ru.pb.global.enums.item.ItemSlotType;
import ru.pb.global.enums.item.ItemType;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Инвентарь игрока
 *
 * @author sjke, Felixx
 */
public class PlayerEqipment {
	private final ConcurrentMap<ItemSlotType, PlayerItem> equipped;
	private final ConcurrentMap<ItemType, CopyOnWriteArrayList<PlayerItem>> items;

	public PlayerEqipment() {
		equipped = new ConcurrentHashMap<ItemSlotType, PlayerItem>(ItemSlotType.values().length);
		items = new ConcurrentHashMap<ItemType, CopyOnWriteArrayList<PlayerItem>>(ItemType.values().length);
		for (ItemType it : ItemType.values()) {
			items.put(it, new CopyOnWriteArrayList<PlayerItem>());
		}
	}

	public CopyOnWriteArrayList<PlayerItem> getItemsByType(ItemType type) {
		return items.get(type);
	}

	public boolean hasItemByTypeAndId(ItemType type, int id) {
		for (PlayerItem item : items.get(type)) {
			if (item.getItem().getId() == id)
				return true;
		}
		return false;
	}

	public PlayerItem getEquippedItemById(int id){
		PlayerItem item = null;
		for(ItemSlotType type : ItemSlotType.values()){
			item = equipped.get(type);
			if(item != null){
				if(item.getItem().getId() == id){
					return item;
				}
			}
		}
		return item;
	}

	public PlayerItem getItemByItemId(int id){
		for(ItemType type : ItemType.values()){
			for(PlayerItem it : items.get(type)){
				if(it.getItem().getId() == id)
					return it;
			}
		}
		return getEquippedItemById(id);
	}

	public PlayerItem getItemByTypeAndId(ItemType type, int id) {
		for (PlayerItem item : items.get(type)) {
			if (item.getItem().getId() == id)
				return item;
		}
		return null;
	}

	public void addItem(PlayerItem item) {
		addItem(item, false);
	}

	public void addItem(PlayerItem item, boolean equip) {
		if (hasItemByTypeAndId(item.getItem().getItemType(), item.getItem().getId())) {
			for (PlayerItem it : items.get(item.getItem().getItemType())) {
				if (it.getItem().getId() == item.getItem().getId()) {
					it.addCount(1);
				}
//				if (!it.isActiveDatabaseFlag(PlayerItem.NEED_UPDATE)) {
//					it.activeDatabaseFlag(PlayerItem.NEED_UPDATE);
//				}
			}
		} else {
//			if (!item.isActiveDatabaseFlag(PlayerItem.NEED_INSERT)) {
//				item.activeDatabaseFlag(PlayerItem.NEED_INSERT);
//			}
			items.get(item.getItem().getItemType()).add(item);
		}
		if (equip) {
			equipItem(item);
		}
	}

	public void equipItem(PlayerItem item) {
		/**  Именно тут флаг ставить нельзя потому что когда игрок входит в игру,
		 *  на него цепляются шмотки, при этом вызывается этот метод. По итогу
		 *  впустую обновляется флаг и в пустую пойдет обновление
		 *  Поэтому флаг меняем именно там где происходит действо.
		 * А именно класс реализующий пакет выхода из инвентаря
		 */
		//item.setFlag(ItemState.UPDATE);
		/**
		 * Я одел шмотку, а на сохранение она пошла как INVENTORY
		 * поэтому этот метод нужен тут
		 */
		item.setEquipped(true);  //Попался который кусался.
		equipped.put(item.getItem().getSlotType(), item);
	}

	public void removeItem(PlayerItem item) {
		items.get(item.getItem().getSlotType()).remove(item);
		item.setFlag(ItemState.DELETE);
	}

	public PlayerItem getEquippedItemBySlot(ItemSlotType type) {
		return equipped.get(type);
	}

	/**
	 * Метод возвращает ссылочную переменную на объект карты
	 * Карта содержит список одетых вещей
	 */
	public ConcurrentMap<ItemSlotType, PlayerItem> getEquippedMap() {
		return equipped;
	}

	/**
	 * Метод возвращает ссылочную переменную на объект карты
	 * Карта содержит список вещей в сумке
	 */
	public ConcurrentMap<ItemType, CopyOnWriteArrayList<PlayerItem>> getItemMap() {
		return items;
	}
}
