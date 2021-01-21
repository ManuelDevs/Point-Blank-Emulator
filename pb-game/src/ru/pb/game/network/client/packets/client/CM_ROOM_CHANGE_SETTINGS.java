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
import ru.pb.game.network.client.packets.server.SM_ROOM_CHANGE_SETTINGS;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;
import ru.pb.global.utils.concurrent.ThreadPoolManager;

/**
 * @author: Felixx
 * Date: 03.10.13
 * Time: 12:15
 */
public class CM_ROOM_CHANGE_SETTINGS extends ClientPacket {

	private String leaderName;
	private int limit, seeConf, autobalans, killtime;

	public CM_ROOM_CHANGE_SETTINGS(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
		leaderName = readS(Player.MAX_NAME_SIZE).trim(); // Интересно зачем?
		killtime = readD();
		limit = readC();
		seeConf = readC();
		autobalans = readH();
	}

	@Override
	public void runImpl() {
		final Room room = getConnection().getRoom();
		if (room != null) {
			room.setKillMask(killtime);
			room.setLimit(limit);
			room.setSeeConf(seeConf);
			room.setAutobalans(autobalans);
			ThreadPoolManager.getInstance().executeTask(new Runnable() {
				@Override
				public void run() {
					for (Player member : getConnection().getRoom().getPlayers().values()) {
						member.getConnection().sendPacket(new SM_ROOM_CHANGE_SETTINGS(room, leaderName));
					}
				}
			});
		}
	}
}