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

package ru.pb.game.network.client.packets.server;

import ru.pb.game.network.client.packets.ServerPacket;
import ru.pb.global.enums.ChatType;
import ru.pb.global.models.RoomSlot;

/**
 * @author: Felixx
 * Date: 22.10.13
 * Time: 12:57
 */
public class SM_CHAT_TEAM_MESSAGE extends ServerPacket {

	private RoomSlot slot;
	private String message;
	private ChatType type;

	public SM_CHAT_TEAM_MESSAGE(RoomSlot slot, ChatType type, String message) {
		super(0xF0B);
		this.slot = slot;
		this.type = type;
		this.message = message;
	}

	@Override
	public void writeImpl() {
		writeH(type.getValue());
		writeD(slot.getId());
		writeD(message.length());
		writeS(message);
	}
}
