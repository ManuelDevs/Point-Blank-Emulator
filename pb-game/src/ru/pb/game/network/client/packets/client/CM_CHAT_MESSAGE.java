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

import ru.pb.game.chat.commands.ChangePlayerRankAdminCommand;
import ru.pb.game.handler.AdminCommandHandler;
import ru.pb.game.network.client.ClientConnection;
import ru.pb.game.network.client.packets.ClientPacket;
import ru.pb.game.network.client.packets.server.SM_CHAT_LOBBY_MESSAGE;
import ru.pb.game.network.client.packets.server.SM_CHAT_TEAM_MESSAGE;
import ru.pb.global.enums.ChatType;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;
import ru.pb.global.models.RoomSlot;

/**
 * Неизветный пакет
 *
 * @author sjke
 */
public class CM_CHAT_MESSAGE extends ClientPacket {

	private ChatType chatType;
	private String message;

	public CM_CHAT_MESSAGE(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
		chatType = ChatType.getByValue(readH());
		message = readS(readH()).trim();
	}

	@Override
	public void runImpl() {
		Room room = getConnection().getRoom();
		Player player = getConnection().getPlayer();
		if (checkPrefix(ChatType.ROOT_COMMAND.getPrefix())) {
			new ChangePlayerRankAdminCommand().call(getConnection(), message.substring(ChatType.ROOT_COMMAND.getPrefix()[0].length() + 1));
			return;
		}
		if (checkPrefix(ChatType.ADMIN_COMMAND.getPrefix()) /*&& getConnection().getPlayer().getRank() > 53*/) {
			AdminCommandHandler.getInstance().handler(getConnection(), message.substring(ChatType.ADMIN_COMMAND.getPrefix()[0].length() + 1));
		} else {
			switch (chatType) {
				case ALL: {
					log.info("CHAT ALL");  // В бою для всех
                    if (room != null && room.getPlayers().containsValue(player)) {
                        for (Player member : room.getPlayers().values()) {
                            member.getConnection().sendPacket(new SM_CHAT_LOBBY_MESSAGE(player, message));
                        }
                    }
					break;
				}
				case WHISPER: {
					log.info("CHAT WHISPER"); // Приват
					break;
				}
				case LOBBY: {  //В лобби, и в комнате для всех.
					log.info("CHAT LOBBY");
					if (room != null && room.getPlayers().containsKey(player.getId())) {
						for (Player member : room.getPlayers().values()) {
							member.getConnection().sendPacket(new SM_CHAT_LOBBY_MESSAGE(player, message));
						}
					}
					else{
						for (Player member : getConnection().getServerChannel().getPlayers().values()) {
							member.getConnection().sendPacket(new SM_CHAT_LOBBY_MESSAGE(player, message));
						}
					}
					break;
				}
				case TEAM: {
					log.info("CHAT TEAM");
					if (room != null) {
						RoomSlot playerSlot = room.getRoomSlotByPlayer(player);
						boolean isRed = playerSlot.getId() % 2 == 0;
						for (Player member : room.getPlayers().values()) {
							RoomSlot memberSlot = room.getRoomSlotByPlayer(member);
							if (isRed) {
								if (memberSlot.getId() % 2 == 0) {
									member.getConnection().sendPacket(new SM_CHAT_TEAM_MESSAGE(playerSlot, chatType, message));
								}
							} else {
								if (memberSlot.getId() % 2 != 0) {
									member.getConnection().sendPacket(new SM_CHAT_TEAM_MESSAGE(playerSlot, chatType, message));
								}
							}
						}
						break;
					}
				}
				case CLAN: { // Клану
					log.info("CHAT CLAN");
					break;
				}
				default: {
					log.info("CHAT UNKNOW");
				}
			}
		}
	}

	private boolean checkPrefix(String[] prefixes) {
		for (String prefix : prefixes) {
			if (message.startsWith(prefix)) {
				return true;
			}
		}
		return false;
	}

}