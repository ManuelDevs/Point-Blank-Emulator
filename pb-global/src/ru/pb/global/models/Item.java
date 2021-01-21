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

import ru.pb.global.enums.item.ItemConsumeType;
import ru.pb.global.enums.item.ItemRepair;
import ru.pb.global.enums.item.ItemSlotType;
import ru.pb.global.enums.item.ItemType;

/**
 * Предмет
 *
 * @author sjke, Felixx
 */
public class Item {
	private final int id;
	private final ItemType itemType;
	private final ItemSlotType slotType;

	private final int consumeValue;
	private final ItemConsumeType consumeType;
	private final ItemRepair repair;

	public Item(int id, ItemType itemType, ItemSlotType slotType, ItemConsumeType consumeType, int consumeValue, ItemRepair repair) {
		this.id = id;
		this.itemType = itemType;
		this.slotType = slotType;
		this.consumeType = consumeType;
		this.consumeValue = consumeValue;
		this.repair = repair;
	}

	public int getId() {
		return id;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public ItemSlotType getSlotType() {
		return slotType;
	}

	public ItemConsumeType getConsumeType() {
		return consumeType;
	}

	public int getConsumeValue() {
		return consumeValue;
	}

	public ItemRepair getRepair() {
		return repair;
	}

	@Override
	public String toString() {
		return "Item{" +
				"id=" + id +
				", itemType=" + itemType +
				", slotType=" + slotType +
				", consumeType=" + consumeType +
				", repair=" + repair +
				'}';
	}
}
