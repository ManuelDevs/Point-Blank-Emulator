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

package ru.pb.auth.network.client.packets.server;

import ru.pb.auth.network.client.packets.ServerPacket;
import ru.pb.global.models.LevelUpInfo;

import java.util.Map;
import java.util.TreeMap;

/**
 * Пакет сообщает клиенту награды за повышение лвл
 *
 * @author Felixx
 */
public class SM_FREE_ITEMS_OF_LEVELUP extends ServerPacket {

	private Map<Byte, LevelUpInfo> rewards;

	public SM_FREE_ITEMS_OF_LEVELUP(Map<Byte, LevelUpInfo> items) {
		super(0xA6D);
		rewards = items;
	}

	@Override
	public void writeImpl() {
		for (LevelUpInfo info : rewards.values()) {
			writeC(info.getRank());
			for (int itemID : info.getRewards()) {
				writeD(itemID);
			}
		}
	}
}