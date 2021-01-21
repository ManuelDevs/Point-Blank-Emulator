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
import ru.pb.game.network.client.packets.server.SM_ROOM_CHANGE_TEAM;
import ru.pb.game.network.client.packets.server.SM_ROOM_INFO;
import ru.pb.game.network.client.packets.server.SM_ROOM_PLAYER_ENTER;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;
import ru.pb.global.utils.concurrent.ThreadPoolManager;

/**
 * Неизветный пакет
 *
 * @author sjke, Felixx
 */
public class CM_ROOM_CHANGE_TEAM extends ClientPacket {

	private int team;

	public CM_ROOM_CHANGE_TEAM(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
		team = readD();
	}

	@Override
	public void runImpl() {
		final Room room = getConnection().getRoom();
		Player player = getConnection().getPlayer();
		if (room != null && player != null) {
			sendPacket(new SM_ROOM_CHANGE_TEAM(player, room, 0, 1, team));
			ThreadPoolManager.getInstance().executeTask(new Runnable() {
				@Override
				public void run() {
					for (Player member : room.getPlayers().values()) {
						member.getConnection().sendPacket(new SM_ROOM_CHANGE_TEAM(getConnection().getPlayer(), room, 0, 1, team));
						member.getConnection().sendPacket(new SM_ROOM_INFO(room));
						member.getConnection().sendPacket(new SM_ROOM_PLAYER_ENTER(room.getRoomSlotByPlayer(getConnection().getPlayer()))); //временный фикс, что бы не перекашивало переходящего игрока
					}
				}
			});
		}
	}
}