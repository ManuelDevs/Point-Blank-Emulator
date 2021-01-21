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
 * Неизместный пакет, отправляется вместе с пекетов авторизации
 *
 * @author sjke
 */
public class SM_BATTLE_BOMB_TAB extends ServerPacket {

	public final int zone;
	public final int slot;
	public final int x, y, z;

	public SM_BATTLE_BOMB_TAB(int zone, int slot, int x, int y, int z) {
		super(0xF1F);
		this.z = z;
		this.y = y;
		this.x = x;
		this.slot = slot;
		this.zone = zone;
	}

	@Override
	public void writeImpl() {
		writeD(slot);
		writeC(zone);
		writeH(42);
		writeD(x);
		writeD(y);
		writeD(z);
	}
}