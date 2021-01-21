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
import ru.pb.game.network.client.packets.server.SM_LOBBY_ENTER;
import ru.pb.game.network.client.packets.server.SM_ROOM_INFO;
import ru.pb.global.enums.SlotState;
import ru.pb.global.models.Player;
import ru.pb.global.models.RoomSlot;
import ru.pb.global.utils.concurrent.ThreadPoolManager;

/**
 * Неизветный пакет
 *
 * @author sjke
 */
public class CM_ROOM_CLOSE_SLOT extends ClientPacket {

	private int slotId;

	public CM_ROOM_CLOSE_SLOT(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
		slotId = readC(); // Слот для закрытия/открытия
		readC(); // Всегда 0
		readC(); // Всегда 0
		readC(); // Всегда 16, если ОТКРЫВАЕМ ранее закрытый слот.
	}

	@Override
	public void runImpl() {
		RoomSlot slot = getConnection().getRoom().getRoomSlots()[slotId];
		switch (slot.getState()) {
			case SLOT_STATE_EMPTY:
				slot.setState(SlotState.SLOT_STATE_CLOSE);
				break;
			case SLOT_STATE_CLOSE:
				slot.setState(SlotState.SLOT_STATE_EMPTY);
				break;

			case SLOT_STATE_CLAN:
			case SLOT_STATE_NORMAL:
			case SLOT_STATE_INFO:
			case SLOT_STATE_INVENTORY:
			case SLOT_STATE_OUTPOST:
			case SLOT_STATE_SHOP:
			{
				slot.getPlayer().getConnection().sendPacket(new SM_LOBBY_ENTER());
				slot.setState(SlotState.SLOT_STATE_EMPTY);
			}
		}
		ThreadPoolManager.getInstance().executeTask(new Runnable() {
			@Override
			public void run() {
				for (Player member : getConnection().getRoom().getPlayers().values()) {
					member.getConnection().sendPacket(new SM_ROOM_INFO(getConnection().getRoom()));
				}
			}
		});
	}
}