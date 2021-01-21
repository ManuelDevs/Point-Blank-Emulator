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

import ru.pb.game.network.client.packets.ClientPacket;
import ru.pb.global.enums.ChatType;
import ru.pb.global.models.Clan;
import ru.pb.global.models.Player;

/**
 * @author: Felixx
 * Date: 22.10.13
 * Time: 12:12
 */
public class CM_CHAT_CLAN_MESSAGE extends ClientPacket {

	private ChatType chatType;
	private String message;

	public CM_CHAT_CLAN_MESSAGE(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
		chatType = ChatType.getByValue(readH());
		int messageLength = readH();
		message = readS(messageLength).trim();
	}

	@Override
	public void runImpl() {
		log.info("CHAT_CLAN_MESSAGE: type=" + chatType + "; msg=" + message);
		//Player player = getConnection().getPlayer();
		//Clan clan = player.getClan();
		//if(clan != null){
		//for(Player member : clan.getPlayers()){
		//	member.getConnection().sendPacket(new SM_CHAT_CLAN_MESSAGE());
		//}
		//}
		//player.getConnection().sendPacket(new SM_CHAT_CLAN_MESSAGE(player, message));
	}
}
