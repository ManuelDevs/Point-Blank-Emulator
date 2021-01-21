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
 * Time: 6:16
 */
public class SM_BATTLE_BOT_RESPAWN extends ServerPacket {

	private int slot;

	public SM_BATTLE_BOT_RESPAWN(int slot) {
		super(0xD33);
		this.slot = slot;
	}

	@Override
	public void writeImpl() {
		writeD(slot);   // TODO Узнать слот для респавна или кол-во.
	}
}
