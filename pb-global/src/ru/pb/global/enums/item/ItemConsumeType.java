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

package ru.pb.global.enums.item;

/**
 * Ð¢Ñ‹Ð¿Ñ‹ Ð¿Ð¾Ñ‚Ñ€ÐµÐ±Ð»ÐµÐ½Ð¸Ñ� Ð¿Ñ€ÐµÐ´Ð¼ÐµÑ‚Ð°.
 *
 * @author Felixx
 */
public enum ItemConsumeType {
	DISABLED(0),
	DURABLE(1),
	TEMPORARY(2),
	PERMANENT(3);
	private int value;

	ItemConsumeType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}

