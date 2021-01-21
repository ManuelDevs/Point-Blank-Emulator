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

package ru.pb.global.enums;

/**
 * Типа каналов (серверов)
 *
 * @author sjke
 */
public enum ChannelType {

	NORMAL_1(1), // Обычный сервер
	NORMAL_2(2), // Обычный сервер
	BEGINERS(3), // Сервер для новичков
	CLAN(4), // Особые операции
	EXPERT_1(5), // Для опытных бойцов
	EXPERT_2(6), // Общий сервер
	CHAMPIONSHIP(7), // Сервер для соревнований
	STATE(8); // Государственный сервер

	private int value;

	ChannelType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static ChannelType getChannelType(int value) {
		for (ChannelType type : values()) {
			if (type.getValue() == value) {
				return type;
			}
		}
		return NORMAL_1;
	}
}
