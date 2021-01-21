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
import ru.pb.game.network.client.packets.server.*;
import ru.pb.global.enums.SlotState;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;
import ru.pb.global.models.RoomSlot;
import ru.pb.global.utils.concurrent.ThreadPoolManager;

/**
 * Неизветный пакет
 *
 * @author sjke
 */
public class CM_BATTLE_SLOT_INFO extends ClientPacket {

	private byte[] bytes;
	boolean isAllReady = false;

	public CM_BATTLE_SLOT_INFO(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
		bytes = readB(16);
	}

	@Override
	public void runImpl() {
		final Room room = getConnection().getRoom();
		ThreadPoolManager.getInstance().executeTask(new Runnable() {
			@Override
			public void run() {
			for (Player member : getConnection().getRoom().getPlayers().values()) {
				RoomSlot slot = room.getRoomSlotByPlayer(member);
				if (slot.getState() == SlotState.SLOT_STATE_BATTLE) {
					member.getConnection().sendPacket(new SM_BATTLE_PLAYER_PING(bytes));
				}
			}
			}
		});
	}
}