/*
 * Java Server Emulator Project Blackout / PointBlank
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * Authors: DarkSkeleton, sjke, Felixx
 * Copyright (C) 2013 PBDev™
 */

package ru.pb.game.network.client.packets.client;

import java.util.List;

import ru.pb.game.network.client.packets.ClientPacket;
import ru.pb.game.network.client.packets.server.SM_EQUIPMENT_LEAVE;
import ru.pb.game.network.client.packets.server.SM_ROOM_INFO;
import ru.pb.global.enums.ItemState;
import ru.pb.global.enums.SlotState;
import ru.pb.global.enums.item.ItemSlotType;
import ru.pb.global.enums.item.ItemType;
import ru.pb.global.models.Player;
import ru.pb.global.models.PlayerEqipment;
import ru.pb.global.models.PlayerItem;
import ru.pb.global.models.Room;
import ru.pb.global.utils.concurrent.ThreadPoolManager;

/**
 * Пакет выхода из инвентаря
 * 
 * @author sjke, DarkSkeleton, Felixx
 */
public class CM_EQUIPMENT_LEAVE extends ClientPacket {

	private int type, first, second, third, fourth, fifth, head, red, blue, dino, item;

	public CM_EQUIPMENT_LEAVE(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
		type = readD(); // Тип одеваемых предметов
		if(type == 3) { // если 3 - оружие + скины, маски, береты - 40 байт
			readEquipWeapons();
			readEquipArmors();
		} else if(type == 2) { // если 2 - оружие - 20 байт
			readEquipWeapons();
		} else if(type == 1) { // если 1 - скины, маски, береты - 20 байт
			readEquipArmors();
		}
	}

	private void readEquipWeapons() {
		first = readD(); // Какая должна быть одета прим пуха
		second = readD(); // Какая должна быть одета саб пуха
		third = readD(); // Какая должна быть одета мили пуха
		fourth = readD(); // Какая должна быть одета тров пуха
		fifth = readD(); // Какая должна быть одета итем пуха
	}

	private void readEquipArmors() {
		red = readD(); // Скин Мужчина стандартный красные.
		blue = readD(); // Скин Мужчина стандартный синие.
		head = readD(); // Шлем.
		dino = readD(); // Берет.
		item = readD(); // Скин дино.
	}

	private void equipWeapons(PlayerEqipment eqipment) {
		if(checkNotChanged(eqipment.getEquippedItemBySlot(ItemSlotType.PRIM), first)) {
			PlayerItem newItem = getChangedItem(eqipment.getItemsByType(ItemType.WEAPON), first);
			if(newItem != null) {
				PlayerItem oldItem = eqipment.getEquippedItemBySlot(ItemSlotType.PRIM);
				if(oldItem != null) {
					eqipment.getEquippedItemBySlot(ItemSlotType.PRIM).setFlag(ItemState.UPDATE);
					eqipment.getEquippedItemBySlot(ItemSlotType.PRIM).setEquipped(Boolean.FALSE);
				}
				newItem.setFlag(ItemState.UPDATE);
				eqipment.equipItem(newItem);
			}
		}
		if(checkNotChanged(eqipment.getEquippedItemBySlot(ItemSlotType.SUB), second)) {
			PlayerItem newItem = getChangedItem(eqipment.getItemsByType(ItemType.WEAPON), second);
			if(newItem != null) {
				PlayerItem oldItem = eqipment.getEquippedItemBySlot(ItemSlotType.SUB);
				if(oldItem != null) {
					eqipment.getEquippedItemBySlot(ItemSlotType.SUB).setFlag(ItemState.UPDATE);
					eqipment.getEquippedItemBySlot(ItemSlotType.SUB).setEquipped(Boolean.FALSE);
				}
				newItem.setFlag(ItemState.UPDATE);
				eqipment.equipItem(newItem);
			}
		}
		if(checkNotChanged(eqipment.getEquippedItemBySlot(ItemSlotType.MELEE), third)) {
			PlayerItem newItem = getChangedItem(eqipment.getItemsByType(ItemType.WEAPON), third);
			if(newItem != null) {
				PlayerItem oldItem = eqipment.getEquippedItemBySlot(ItemSlotType.MELEE);
				if(oldItem != null) {
					eqipment.getEquippedItemBySlot(ItemSlotType.MELEE).setFlag(ItemState.UPDATE);
					eqipment.getEquippedItemBySlot(ItemSlotType.MELEE).setEquipped(Boolean.FALSE);
				}
				newItem.setFlag(ItemState.UPDATE);
				eqipment.equipItem(newItem);
			}
		}
		if(checkNotChanged(eqipment.getEquippedItemBySlot(ItemSlotType.THROWING), fourth)) {
			PlayerItem newItem = getChangedItem(eqipment.getItemsByType(ItemType.WEAPON), fourth);
			if(newItem != null) {
				PlayerItem oldItem = eqipment.getEquippedItemBySlot(ItemSlotType.THROWING);
				if(oldItem != null) {
					eqipment.getEquippedItemBySlot(ItemSlotType.THROWING).setFlag(ItemState.UPDATE);
					eqipment.getEquippedItemBySlot(ItemSlotType.THROWING).setEquipped(Boolean.FALSE);
				}
				newItem.setFlag(ItemState.UPDATE);
				eqipment.equipItem(newItem);
			}
		}
		if(checkNotChanged(eqipment.getEquippedItemBySlot(ItemSlotType.ITEM), fifth)) {
			PlayerItem newItem = getChangedItem(eqipment.getItemsByType(ItemType.WEAPON), fifth);
			if(newItem != null) {
				PlayerItem oldItem = eqipment.getEquippedItemBySlot(ItemSlotType.ITEM);
				if(oldItem != null) {
					eqipment.getEquippedItemBySlot(ItemSlotType.ITEM).setFlag(ItemState.UPDATE);
					eqipment.getEquippedItemBySlot(ItemSlotType.ITEM).setEquipped(Boolean.FALSE);
				}
				newItem.setFlag(ItemState.UPDATE);
				eqipment.equipItem(newItem);
			}
		}
	}

	private void equipArmors(PlayerEqipment eqipment) {
		if(checkNotChanged(eqipment.getEquippedItemBySlot(ItemSlotType.CHAR_RED), red)) {
			PlayerItem newItem = getChangedItem(eqipment.getItemsByType(ItemType.CHARACTER), red);
			if(newItem != null) {
				PlayerItem oldItem = eqipment.getEquippedItemBySlot(ItemSlotType.CHAR_RED);
				if(oldItem != null) {
					eqipment.getEquippedItemBySlot(ItemSlotType.CHAR_RED).setFlag(ItemState.UPDATE);
					eqipment.getEquippedItemBySlot(ItemSlotType.CHAR_RED).setEquipped(Boolean.FALSE);
				}
				newItem.setFlag(ItemState.UPDATE);
				eqipment.equipItem(newItem);
			}
		}
		if(checkNotChanged(eqipment.getEquippedItemBySlot(ItemSlotType.CHAR_BLUE), blue)) {
			PlayerItem newItem = getChangedItem(eqipment.getItemsByType(ItemType.CHARACTER), blue);
			if(newItem != null) {
				PlayerItem oldItem = eqipment.getEquippedItemBySlot(ItemSlotType.CHAR_BLUE);
				if(oldItem != null) {
					eqipment.getEquippedItemBySlot(ItemSlotType.CHAR_BLUE).setFlag(ItemState.UPDATE);
					eqipment.getEquippedItemBySlot(ItemSlotType.CHAR_BLUE).setEquipped(Boolean.FALSE);
				}
				newItem.setFlag(ItemState.UPDATE);
				eqipment.equipItem(newItem);
			}
		}
		if(checkNotChanged(eqipment.getEquippedItemBySlot(ItemSlotType.CHAR_HEAD), head)) {
			PlayerItem newItem = getChangedItem(eqipment.getItemsByType(ItemType.CHARACTER), head);
			if(newItem != null) {
				PlayerItem oldItem = eqipment.getEquippedItemBySlot(ItemSlotType.CHAR_HEAD);
				if(oldItem != null) {
					eqipment.getEquippedItemBySlot(ItemSlotType.CHAR_HEAD).setFlag(ItemState.UPDATE);
					eqipment.getEquippedItemBySlot(ItemSlotType.CHAR_HEAD).setEquipped(Boolean.FALSE);
				}
				newItem.setFlag(ItemState.UPDATE);
				eqipment.equipItem(newItem);
			}
		}
		if(checkNotChanged(eqipment.getEquippedItemBySlot(ItemSlotType.CHAR_DINO), dino)) {
			PlayerItem newItem = getChangedItem(eqipment.getItemsByType(ItemType.CHARACTER), dino);
			if(newItem != null) {
				PlayerItem oldItem = eqipment.getEquippedItemBySlot(ItemSlotType.CHAR_DINO);
				if(oldItem != null) {
					eqipment.getEquippedItemBySlot(ItemSlotType.CHAR_DINO).setFlag(ItemState.UPDATE);
					eqipment.getEquippedItemBySlot(ItemSlotType.CHAR_DINO).setEquipped(Boolean.FALSE);
				}
				newItem.setFlag(ItemState.UPDATE);
				eqipment.equipItem(newItem);
			}
		}
		if(checkNotChanged(eqipment.getEquippedItemBySlot(ItemSlotType.CHAR_ITEM), item)) {
			PlayerItem newItem = getChangedItem(eqipment.getItemsByType(ItemType.CHARACTER), item);
			if(newItem != null) {
				PlayerItem oldItem = eqipment.getEquippedItemBySlot(ItemSlotType.CHAR_ITEM);
				if(oldItem != null) {
					eqipment.getEquippedItemBySlot(ItemSlotType.CHAR_ITEM).setFlag(ItemState.UPDATE);
					eqipment.getEquippedItemBySlot(ItemSlotType.CHAR_ITEM).setEquipped(Boolean.FALSE);
				}
				newItem.setFlag(ItemState.UPDATE);
				eqipment.equipItem(newItem);
			}
		}
	}

	private boolean checkNotChanged(PlayerItem oldItem, int newItem) {
		return oldItem == null ? newItem != 0 : oldItem.getItem().getId() != newItem;
	}

	private PlayerItem getChangedItem(List<PlayerItem> items, int itemId) {
		for(PlayerItem item : items) {
			if( !checkNotChanged(item, itemId)) {
				return item;
			}
		}
		return null;
	}

	@Override
	public void runImpl() {
		if(type == 3) { // если 3 - оружие + скины, маски, береты - 40 байт
			equipWeapons(getConnection().getPlayer().getEqipment());
			equipArmors(getConnection().getPlayer().getEqipment());
		} else if(type == 2) { // если 2 - оружие - 20 байт
			equipWeapons(getConnection().getPlayer().getEqipment());
		} else if(type == 1) { // если 1 - скины, маски, береты - 20 байт
			equipArmors(getConnection().getPlayer().getEqipment());
		}

		final Room room = getConnection().getRoom();
		if(room != null) {
			room.getRoomSlotByPlayer(getConnection().getPlayer()).setState(SlotState.SLOT_STATE_NORMAL);
			ThreadPoolManager.getInstance().executeTask(new Runnable() {
				@Override
				public void run() {
					for(Player member : getConnection().getRoom().getPlayers().values()) {
						member.getConnection().sendPacket(new SM_ROOM_INFO(room));
					}
				}
			});
		}
		sendPacket(new SM_EQUIPMENT_LEAVE(type));
	}
}