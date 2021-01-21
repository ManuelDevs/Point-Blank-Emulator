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

package ru.pb.game.network.client.packets.client;

import ru.pb.game.controller.ChannelController;
import ru.pb.game.network.client.packets.ClientPacket;
import ru.pb.game.network.client.packets.server.SM_CHAT_WHISPER_MESSAGE;
import ru.pb.global.models.Channel;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;

/**
 * @author: Felixx
 * Date: 22.10.13
 * Time: 14:20
 */
public class CM_CHAT_WHISPER_MESSAGE extends ClientPacket {
	//запрос 22 01 7F 0A 54 65 61 73 74 46 6F 72 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 08 00 57 48 5F 43 48 41 54 00
	//ответ: 23 01 00 00 00 00 00 00 00 80 54 65 61 73 74 46 6F 72 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
	// Но писал я тут сам себе...  или не в онлайне плееру. надо переснифать с норм плеерами.

	private String toPlayer, message;

	public CM_CHAT_WHISPER_MESSAGE(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
		toPlayer = readS(Player.MAX_NAME_SIZE).trim();
		message = readS(readH());
	}

	@Override
	public void runImpl() {
		log.info("CHAT_WHISPER_MESSAGE: to=" + toPlayer + "; msg=" + message);
		Player to = getPlayer(toPlayer);
		if(to != null){
			to.getConnection().sendPacket(new SM_CHAT_WHISPER_MESSAGE(getConnection().getPlayer().getName()));
		}
		else{
			// todo: Себе сообщение о ошибке?
		}
	}

	private Player getPlayer(String name) {
		for (Channel ch : ChannelController.getInstance().getChannels()) {
			for (Player member : ch.getPlayers().values()) {
				if (member.getName().equals(name)) {
					return member;
				}
			}
			for (Room room : ch.getRooms().values()) {
				for (Player member : room.getPlayers().values()) {
					if (member.getName().equals(name)) {
						return member;
					}
				}
			}
		}
		return null;
	}
}
