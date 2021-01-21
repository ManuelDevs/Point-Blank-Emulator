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
import ru.pb.global.models.Player;

/**
 * @author: Felixx
 * Date: 22.10.13
 * Time: 15:32
 */
public class SM_CHAT_WHISPER_MESSAGE extends ServerPacket {

	private String sender;

	public SM_CHAT_WHISPER_MESSAGE(String sender) {
		super(0x123);
		this.sender = sender;
	}

	@Override
	public void writeImpl() {
		writeD(0);
		writeC(0);
		writeC(0);
		writeC(0);
		//writeС(0x80);  // Что это? ЧТо нет такого плеера или что сам себе пишеш?
		writeC(0);
		writeS(sender, Player.MAX_NAME_SIZE);
	}
}