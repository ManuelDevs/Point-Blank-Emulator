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
 * Пакет обновления 1ой части настроек комнаты
 *
 * @author DarkSkeleton
 */
public class CM_ROOM_CHANGE_SETTINGS_PART1 extends ClientPacket {
	private String name;
	private int map_id, stage4v4, room_type, slot_count, allweapons, random_map, special, _aiLevel, _aiCount;

	public CM_ROOM_CHANGE_SETTINGS_PART1(int opcode) {
		super(opcode);
	}

	@Override
	protected void readImpl() {
		readD();
		name = readS(18); //23
		readB(5);
		map_id = readC(); // ИД карты
		readC(); // Хз тут 0
		stage4v4 = readC(); // Карта 4 на 4

		room_type = readC(); // Тип карты
		readC(); // Хз тут 0
		readC(); // Хз тут 0
		slot_count = readC(); // Сколько разрешено слотов

		readC(); // Хз тут 0
		allweapons = readC(); // Разрешонное оружие
		random_map = readC(); // Карта рандомная = 2
		special = readC(); // Номер спец миссии

		_aiCount = readC();
		_aiLevel = readC();
	}

	@Override
	protected void runImpl() {
		final Room room = getConnection().getRoom();
		if (room != null) {
			room.setName(name);
			room.setMapId(map_id);
			room.setStage4v4(stage4v4);
			room.setType(room_type);
			room.setSlots(slot_count);
			room.setAllWeapons(allweapons);
			room.setRandomMap(random_map);
			room.setSpecial(special);
			room.setAiCount(_aiCount);
			room.setAiLevel(_aiLevel);
			ThreadPoolManager.getInstance().executeTask(new Runnable() {
				@Override
				public void run() {
					for (Player member : getConnection().getRoom().getPlayers().values()) {
						member.getConnection().sendPacket(new SM_BATTLE_ROOM_INFO(room));
					}
				}
			});
		}
	}
}
