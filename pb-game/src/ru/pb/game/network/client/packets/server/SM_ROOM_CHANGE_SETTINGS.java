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
import ru.pb.global.models.Room;

/**
 * @author: Felixx
 * Date: 03.10.13
 * Time: 12:21
 */
public class SM_ROOM_CHANGE_SETTINGS extends ServerPacket {

	private Room room;
	private String lider;

	public SM_ROOM_CHANGE_SETTINGS(Room room, String lider) {
		super(0xF13);
		this.room = room;
		this.lider = lider;
	}

	@Override
	public void writeImpl() {
		writeS(lider, Player.MAX_NAME_SIZE);
		writeD(room.getKillMask()); // маска Убиств / Время
		writeC(room.getLimit()); // Лимит на вход
		writeC(room.getSeeConf()); // Настройки наблюдения
		writeH(room.getAutobalans()); // автобаланс
	}
}