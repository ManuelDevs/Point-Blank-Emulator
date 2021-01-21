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
import ru.pb.game.network.client.packets.server.SM_ROOM_ENTER;
import ru.pb.game.network.client.packets.server.SM_ROOM_PLAYER_ENTER;
import ru.pb.global.models.Channel;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;
import ru.pb.global.models.RoomSlot;
import ru.pb.global.utils.concurrent.ThreadPoolManager;

/**
 * Неизветный пакет
 *
 * @author sjke
 */
public class CM_ROOM_ENTER extends ClientPacket {

	private int roomId;

	public CM_ROOM_ENTER(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
		roomId = readD();
	}

	@Override
	public void runImpl() {
		Room room = getConnection().getServerChannel().getRoom(roomId);
		if (room == null) {
			sendPacket(new SM_ROOM_ENTER(null, 0, 0x80001004));
			return;
		}
		getConnection().setRoom(room);

		room.addPlayer(getConnection().getPlayer());
		final RoomSlot playerSlot = room.getRoomSlotByPlayer(getConnection().getPlayer());
		if (playerSlot == null) {
			sendPacket(new SM_ROOM_ENTER(null, 0, 0x80001004));
			return;
		}
		ThreadPoolManager.getInstance().executeTask(new Runnable() {
			@Override
			public void run() {
				for (Player member : getConnection().getRoom().getPlayers().values()) {
					if (!getConnection().getPlayer().getId().equals(member.getId())) {
						log.info("Send SM_ROOM_PLAYER_ENTER to " + member.getName());
						member.getConnection().sendPacket(new SM_ROOM_PLAYER_ENTER(playerSlot));
					}
				}
			}
		});

		//нужно удалять игрока из канала, если он входит в комнату
		Channel ch = getConnection().getServerChannel();
		ch.removePlayer(getConnection().getPlayer());

		sendPacket(new SM_ROOM_ENTER(room, playerSlot.getId(), 0));
	}
}