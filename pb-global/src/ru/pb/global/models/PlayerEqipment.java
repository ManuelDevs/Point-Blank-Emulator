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
 * Copyright (C) 2013 PBDevâ„¢
 */

package ru.pb.global.models;

import ru.pb.global.enums.ItemState;
import ru.pb.global.enums.item.ItemSlotType;
import ru.pb.global.enums.item.ItemType;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Ð˜Ð½Ð²ÐµÐ½Ñ‚Ð°Ñ€ÑŒ Ð¸Ð³Ñ€Ð¾ÐºÐ°
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
	
	public PlayerItem getItemById(long id){
		for(ItemType type : ItemType.values()){
			for(PlayerItem it : items.get(type)){
				if(it.getId() == id)
					return it;
			}
		}
		return null;
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
		/**  Ð˜Ð¼ÐµÐ½Ð½Ð¾ Ñ‚ÑƒÑ‚ Ñ„Ð»Ð°Ð³ Ñ�Ñ‚Ð°Ð²Ð¸Ñ‚ÑŒ Ð½ÐµÐ»ÑŒÐ·Ñ� Ð¿Ð¾Ñ‚Ð¾Ð¼Ñƒ Ñ‡Ñ‚Ð¾ ÐºÐ¾Ð³Ð´Ð° Ð¸Ð³Ñ€Ð¾Ðº Ð²Ñ…Ð¾Ð´Ð¸Ñ‚ Ð² Ð¸Ð³Ñ€Ñƒ,
		 *  Ð½Ð° Ð½ÐµÐ³Ð¾ Ñ†ÐµÐ¿Ð»Ñ�ÑŽÑ‚Ñ�Ñ� ÑˆÐ¼Ð¾Ñ‚ÐºÐ¸, Ð¿Ñ€Ð¸ Ñ�Ñ‚Ð¾Ð¼ Ð²Ñ‹Ð·Ñ‹Ð²Ð°ÐµÑ‚Ñ�Ñ� Ñ�Ñ‚Ð¾Ñ‚ Ð¼ÐµÑ‚Ð¾Ð´. ÐŸÐ¾ Ð¸Ñ‚Ð¾Ð³Ñƒ
		 *  Ð²Ð¿ÑƒÑ�Ñ‚ÑƒÑŽ Ð¾Ð±Ð½Ð¾Ð²Ð»Ñ�ÐµÑ‚Ñ�Ñ� Ñ„Ð»Ð°Ð³ Ð¸ Ð² Ð¿ÑƒÑ�Ñ‚ÑƒÑŽ Ð¿Ð¾Ð¹Ð´ÐµÑ‚ Ð¾Ð±Ð½Ð¾Ð²Ð»ÐµÐ½Ð¸Ðµ
		 *  ÐŸÐ¾Ñ�Ñ‚Ð¾Ð¼Ñƒ Ñ„Ð»Ð°Ð³ Ð¼ÐµÐ½Ñ�ÐµÐ¼ Ð¸Ð¼ÐµÐ½Ð½Ð¾ Ñ‚Ð°Ð¼ Ð³Ð´Ðµ Ð¿Ñ€Ð¾Ð¸Ñ�Ñ…Ð¾Ð´Ð¸Ñ‚ Ð´ÐµÐ¹Ñ�Ñ‚Ð²Ð¾.
		 * Ð� Ð¸Ð¼ÐµÐ½Ð½Ð¾ ÐºÐ»Ð°Ñ�Ñ� Ñ€ÐµÐ°Ð»Ð¸Ð·ÑƒÑŽÑ‰Ð¸Ð¹ Ð¿Ð°ÐºÐµÑ‚ Ð²Ñ‹Ñ…Ð¾Ð´Ð° Ð¸Ð· Ð¸Ð½Ð²ÐµÐ½Ñ‚Ð°Ñ€Ñ�
		 */
		//item.setFlag(ItemState.UPDATE);
		/**
		 * Ð¯ Ð¾Ð´ÐµÐ» ÑˆÐ¼Ð¾Ñ‚ÐºÑƒ, Ð° Ð½Ð° Ñ�Ð¾Ñ…Ñ€Ð°Ð½ÐµÐ½Ð¸Ðµ Ð¾Ð½Ð° Ð¿Ð¾ÑˆÐ»Ð° ÐºÐ°Ðº INVENTORY
		 * Ð¿Ð¾Ñ�Ñ‚Ð¾Ð¼Ñƒ Ñ�Ñ‚Ð¾Ñ‚ Ð¼ÐµÑ‚Ð¾Ð´ Ð½ÑƒÐ¶ÐµÐ½ Ñ‚ÑƒÑ‚
		 */
		item.setEquipped(true);  //ÐŸÐ¾Ð¿Ð°Ð»Ñ�Ñ� ÐºÐ¾Ñ‚Ð¾Ñ€Ñ‹Ð¹ ÐºÑƒÑ�Ð°Ð»Ñ�Ñ�.
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
	 * ÐœÐµÑ‚Ð¾Ð´ Ð²Ð¾Ð·Ð²Ñ€Ð°Ñ‰Ð°ÐµÑ‚ Ñ�Ñ�Ñ‹Ð»Ð¾Ñ‡Ð½ÑƒÑŽ Ð¿ÐµÑ€ÐµÐ¼ÐµÐ½Ð½ÑƒÑŽ Ð½Ð° Ð¾Ð±ÑŠÐµÐºÑ‚ ÐºÐ°Ñ€Ñ‚Ñ‹
	 * ÐšÐ°Ñ€Ñ‚Ð° Ñ�Ð¾Ð´ÐµÑ€Ð¶Ð¸Ñ‚ Ñ�Ð¿Ð¸Ñ�Ð¾Ðº Ð¾Ð´ÐµÑ‚Ñ‹Ñ… Ð²ÐµÑ‰ÐµÐ¹
	 */
	public ConcurrentMap<ItemSlotType, PlayerItem> getEquippedMap() {
		return equipped;
	}

	/**
	 * ÐœÐµÑ‚Ð¾Ð´ Ð²Ð¾Ð·Ð²Ñ€Ð°Ñ‰Ð°ÐµÑ‚ Ñ�Ñ�Ñ‹Ð»Ð¾Ñ‡Ð½ÑƒÑŽ Ð¿ÐµÑ€ÐµÐ¼ÐµÐ½Ð½ÑƒÑŽ Ð½Ð° Ð¾Ð±ÑŠÐµÐºÑ‚ ÐºÐ°Ñ€Ñ‚Ñ‹
	 * ÐšÐ°Ñ€Ñ‚Ð° Ñ�Ð¾Ð´ÐµÑ€Ð¶Ð¸Ñ‚ Ñ�Ð¿Ð¸Ñ�Ð¾Ðº Ð²ÐµÑ‰ÐµÐ¹ Ð² Ñ�ÑƒÐ¼ÐºÐµ
	 */
	public ConcurrentMap<ItemType, CopyOnWriteArrayList<PlayerItem>> getItemMap() {
		return items;
	}
}
