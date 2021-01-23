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
import ru.pb.game.network.client.packets.server.PROTOCOL_ROOM_SLOT_INFO_ACK;
import ru.pb.game.network.client.packets.server.PROTOCOL_SHOP_ENTER_ACK;
import ru.pb.global.enums.SlotState;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;
import ru.pb.global.utils.concurrent.ThreadPoolManager;

public class PROTOCOL_SHOP_ENTER_REQ extends ClientPacket {

	public PROTOCOL_SHOP_ENTER_REQ(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
	}

	@Override
	public void runImpl() {
		final Room room = getConnection().getRoom();

		if (room != null) {
			room.getRoomSlotByPlayer(getConnection().getPlayer()).setState(SlotState.SLOT_STATE_SHOP);
			ThreadPoolManager.getInstance().executeTask(new Runnable() {
				@Override
				public void run() {
					for (Player member : getConnection().getRoom().getPlayers().values()) {
						member.getConnection().sendPacket(new PROTOCOL_ROOM_SLOT_INFO_ACK(room));
					}
				}
			});
		}
		sendPacket(new PROTOCOL_SHOP_ENTER_ACK());
	}
}