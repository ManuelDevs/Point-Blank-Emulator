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
import ru.pb.game.network.client.packets.server.SM_BATTLE_READY;
import ru.pb.game.network.client.packets.server.SM_ROOM_INFO;
import ru.pb.global.enums.SlotState;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;
import ru.pb.global.models.RoomSlot;
import ru.pb.global.utils.concurrent.ThreadPoolManager;

/**
 * Пакет готовности плеера в комнате
 * TODO: В случае если нет готовых игроков в противополжной комнанде, то надо посылать ошибку из RoomErrorMessage вместо STATE_SLOT в этом же пакет-ответе
 * TODO: В случае если у игрока возникли проблемы с инвентарем, то пользуемся RoomErrorMessage(там есть описания ошибок)
 *
 * @author sjke, DarkSkeleton
 */
public class CM_BATTLE_READY extends ClientPacket {

	public CM_BATTLE_READY(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
	}

	@Override
	public void runImpl() {
		final Room room = getConnection().getRoom();
		if (room.getLeader().equals(getConnection().getPlayer())) {
			if (!room.isFigth()) {
				room.setFigth(true);
				room.setTimeLost(room.getKillTime() * 60);
			}
			ThreadPoolManager.getInstance().executeTask(new Runnable() {
				@Override
				public void run() {
					for (Player member : getConnection().getRoom().getPlayers().values()) {
						RoomSlot slot = room.getRoomSlotByPlayer(member);
						if (slot.getState() == SlotState.SLOT_STATE_READY && !room.getLeader().equals(member)) {
							slot.setState(SlotState.SLOT_STATE_LOAD);
							member.getConnection().sendPacket(new SM_BATTLE_READY(room, getConnection().getPlayer()));
						} else if (room.getLeader().equals(member)) {
							slot.setState(SlotState.SLOT_STATE_LOAD);
							member.getConnection().sendPacket(new SM_BATTLE_READY(room, member));
						}
					}
				}
			});
		} else {
			RoomSlot slotLeader = room.getRoomSlotByPlayer(room.getLeader());
			RoomSlot slot = room.getRoomSlotByPlayer(getConnection().getPlayer());
			if (slotLeader.getState() == SlotState.SLOT_STATE_LOAD || slotLeader.getState() == SlotState.SLOT_STATE_RENDEZVOUS || slotLeader.getState() == SlotState.SLOT_STATE_PRESTART || slotLeader.getState() == SlotState.SLOT_STATE_BATTLE_READY || slotLeader.getState() == SlotState.SLOT_STATE_BATTLE) {
				slot.setState(SlotState.SLOT_STATE_LOAD);
				sendPacket(new SM_BATTLE_READY(room, getConnection().getPlayer()));
			}
			if (slot.getState() == SlotState.SLOT_STATE_READY) {
				slot.setState(SlotState.SLOT_STATE_NORMAL);
			} else if (slot.getState() == SlotState.SLOT_STATE_NORMAL) {
				slot.setState(SlotState.SLOT_STATE_READY);
			}
		}
		ThreadPoolManager.getInstance().executeTask(new Runnable() {
			@Override
			public void run() {
				for (Player member : getConnection().getRoom().getPlayers().values()) {
					member.getConnection().sendPacket(new SM_ROOM_INFO(room));
				}
			}
		});
	}

}