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

package ru.pb.auth.network.client.packets.client;

import ru.pb.auth.network.client.packets.ClientPacket;
import ru.pb.auth.network.client.packets.server.SM_FREE_ITEMS_OF_LEVELUP;
import ru.pb.global.models.LevelUpInfo;
import ru.pb.global.service.LevelUpDaoService;

import java.util.Map;
import java.util.TreeMap;

/**
 * Пакет сообщает клиенту награды за повышение лвл
 *
 * @author Felixx
 */
public class CM_FREE_ITEMS_OF_LEVELUP extends ClientPacket {
	private Map<Byte, LevelUpInfo> rewards = LevelUpDaoService.getInstance().getAll();

	public CM_FREE_ITEMS_OF_LEVELUP(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
	}

	@Override
	public void runImpl() {
		sendPacket(new SM_FREE_ITEMS_OF_LEVELUP(rewards));
	}
}