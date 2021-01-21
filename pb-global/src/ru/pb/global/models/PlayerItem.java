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

import org.slf4j.LoggerFactory;

import ru.pb.global.enums.ItemState;
import ru.pb.global.enums.item.ItemLocation;
import ru.pb.global.utils.DateTimeUtil;

/**
 * Предмет игрока
 *
 * @author sjke, Felixx
 */
public class PlayerItem {
	private final long id;
	private ItemLocation loc;
	private final Item item;
	private int count;
	private int consumeLost;
	public ItemState flag = ItemState.NOTHING;

	public PlayerItem(ItemLocation loc, Item item, int count, boolean isNew) {
		this(0, loc, item, count, isNew);
	}

	public PlayerItem(long id, ItemLocation loc, Item item, int count) {
		this(id, loc, item, count, false);
	}

	public PlayerItem(long id, ItemLocation loc, Item item, int count, boolean isNew) {
		this.id = id;
		this.loc = loc;
		this.item = item;
		this.count = count;

		if (isNew) {
			initConsumeLost();
		}
	}

	public void initConsumeLost() {
		switch (item.getConsumeType()) {
			case TEMPORARY: {
				consumeLost = item.getConsumeValue(); /*DateTimeUtil.getDateTime(item.getConsumeValue());*/
				LoggerFactory.getLogger(getClass()).info("TEMPORARY - initConsumeLost");
				break;
			}
			case DURABLE: {
				consumeLost = item.getConsumeValue();
				LoggerFactory.getLogger(getClass()).info("DURABLE - initConsumeLost");
				break;
			}
			case PERMANENT: {
				consumeLost = 0;
				LoggerFactory.getLogger(getClass()).info("PERMANENT - initConsumeLost");
				break;
			}
		}
	}

	public long getId() {
		return id;
	}

	public ItemLocation getItemLocation() {
		return loc;
	}

	public boolean isEquipped() {
		return loc == ItemLocation.EQUIPPED;
	}

	public void setEquipped(Boolean b) {
		if (b) {
			loc = ItemLocation.EQUIPPED;
			return;
		}
		loc = ItemLocation.INVENTORY;
	}

	public Item getItem() {
		return item;
	}

	public int getCount() {
		return count;
	}

	public void addCount(int count) {
		this.count += count;
	}

	public int getConsumeLost() {
		return consumeLost;
	}

	public ItemState getFlag() {
		return flag;
	}

	public void setFlag(Enum flag) {
		this.flag = (ItemState) flag;
	}

	@Override
	public String toString() {
		return "\nPlayerItem{" +
				"id=" + id +
				"item=" + item +
				", count=" + count +
				", consumeLost=" + consumeLost +
				'}';
	}
}
