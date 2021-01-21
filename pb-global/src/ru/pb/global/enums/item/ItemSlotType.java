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

package ru.pb.global.enums.item;

/**
 * Типы слотов для итемов.
 *
 * @author Felixx
 */
public enum ItemSlotType {
	PRIM(0),
	SUB(1),
	MELEE(2),
	THROWING(3),
	ITEM(4),
	CHAR_RED(5),
	CHAR_BLUE(6),
	CHAR_HEAD(7),
	CHAR_ITEM(8),
	CHAR_DINO(9), // В клиенте нету...
	COUPON_ITEM(10);

	private int value;

	ItemSlotType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
