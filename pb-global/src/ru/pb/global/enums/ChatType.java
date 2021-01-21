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
 * Тип чата
 */
public enum ChatType {

	ROOT_COMMAND(-1, "/root"),
	ADMIN_COMMAND(0, "/adm", "/Adm"),

	ALL(1),
	WHISPER(2),
	REPLY(3),
	TEAM(4),
	CLAN(5),
	LOBBY(6),
	MATCH(7),
	CLAN_MEMBER_PAGE(8);

	private int value;
	private String[] prefix;

	ChatType(int value, String... prefix) {
		this.value = value;
		this.prefix = prefix;
	}

	public int getValue() {
		return value;
	}

	public String[] getPrefix() {
		return prefix;
	}

	public static ChatType getByValue(int value) {
		for (ChatType chatType : values()) {
			if (chatType.getValue() == value) {
				return chatType;
			}
		}
		return ALL;
	}
}
