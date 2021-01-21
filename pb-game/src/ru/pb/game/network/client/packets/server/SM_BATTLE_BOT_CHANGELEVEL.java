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

/**
 * @author: Felixx
 * Date: 02.10.13
 * Time: 15:13
 */
public class SM_BATTLE_BOT_CHANGELEVEL extends ServerPacket {

	public final int AILevel;

	public SM_BATTLE_BOT_CHANGELEVEL(int AILevel) {
		super(0xD31);
		this.AILevel = AILevel;
	}

	@Override
	public void writeImpl() {
		writeC(AILevel <= 10 ? AILevel : 10);
		for (int i = 0; i < 8; i++) {
			writeD(1);// ранг бота?
			writeD(1);// ранг бота?
		}
	}
}