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

/**
 * @author: Felixx
 * Date: 03.10.13
 * Time: 5:53
 */
public class CM_UPDATE_GAMESERVERS extends ClientPacket {

	public CM_UPDATE_GAMESERVERS(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
	}

	@Override
	public void runImpl() {
		// TODO: Test
	}
}