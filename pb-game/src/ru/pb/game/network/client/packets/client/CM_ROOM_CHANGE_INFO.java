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
import ru.pb.game.network.client.packets.server.SM_BATTLE_ROOM_INFO;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;
import ru.pb.global.utils.concurrent.ThreadPoolManager;

/**
 * Пакет полной смены настроек комнаты
 *
 * @author: Felixx, DarkSkeleton
 */
public class CM_ROOM_CHANGE_INFO extends ClientPacket {

	private Room room;

	public CM_ROOM_CHANGE_INFO(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
		room = getConnection().getRoom();
		readD(); // тут всегда 0
		room.setName(readS(Room.ROOM_NAME_SIZE).trim());
		room.setMapId(readC());
		readC(); // unk
		room.setStage4v4(readC());
		room.setType(readC());
		readC(); // unk
		readC(); // unk
		room.setSlots(readC());
		readC(); // unk
		room.setAllWeapons(readC());
		room.setRandomMap(readC());
		room.setSpecial(readC());
		readS(Player.MAX_NAME_SIZE);
		room.setKillMask(readC());
		readC(); // unk
		readC(); // unk
		readC(); // unk
		room.setLimit(readC());
		room.setSeeConf(readC());
		room.setAutobalans(readH());
		room.setLeader(getConnection().getPlayer());
		room.addPlayer(getConnection().getPlayer());
		if (getBuf().readableBytes() > 0) {
			room.setAiCount(readC());
			room.setAiLevel(readC());
		}
	}

	@Override
	public void runImpl() {
		if (getConnection().getPlayer() != null) {
			ThreadPoolManager.getInstance().executeTask(new Runnable() {
				@Override
				public void run() {
					for (Player member : getConnection().getRoom().getPlayers().values()) {
						member.getConnection().sendPacket(new SM_BATTLE_ROOM_INFO(getConnection().getRoom()));
					}
				}
			});
		}
	}
}