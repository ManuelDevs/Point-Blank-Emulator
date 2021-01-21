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
import ru.pb.game.network.client.packets.server.SM_BATTLE_END;
import ru.pb.game.network.client.packets.server.SM_ROOM_INFO;
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
public class CM_BATTLE_TIMER_SYNC extends ClientPacket {

	private int timeLost, unkD, unkC1, unkC2;

	public CM_BATTLE_TIMER_SYNC(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
		timeLost = readD();
		unkD = readD();
		unkC1 = readC();
		unkC2 = readC();
	}

	@Override
	public void runImpl() {
		final Room room = getConnection().getRoom();
		room.setTimeLost(timeLost);
		Player player = getConnection().getPlayer();
		if (room != null) {
			if (timeLost < 1) {
				if (room.getLeader().equals(player)) {
					ThreadPoolManager.getInstance().executeTask(new Runnable() {
						@Override
						public void run() {
							for (Player member : getConnection().getRoom().getPlayers().values()) {
								RoomSlot slot = room.getRoomSlotByPlayer(member);
								if (slot.getState() == SlotState.SLOT_STATE_BATTLE || slot.getState() == SlotState.SLOT_STATE_BATTLE_READY || slot.getState() == SlotState.SLOT_STATE_PRESTART || slot.getState() == SlotState.SLOT_STATE_LOAD || slot.getState() == SlotState.SLOT_STATE_RENDEZVOUS) {
									room.getRoomSlotByPlayer(member).setState(SlotState.SLOT_STATE_NORMAL);

									member.getStats().setKills(member.getStats().getKills() + slot.getAllKills());
									member.getStats().setDeaths(member.getStats().getDeaths() + slot.getAllDeath());
									member.getStats().setFights(member.getStats().getFights() + 1);

									member.getConnection().sendPacket(new SM_BATTLE_END(member, getConnection().getRoom()));
								}
								member.getConnection().sendPacket(new SM_ROOM_INFO(room));
							}
						}
					});
					for (int i = 0; i < 16; i++) {
						RoomSlot slot = room.getRoomSlot(i);
						slot.setAllExp(slot.getAllKills() * 5);
						slot.setAllGP(slot.getAllKills() * 25);
						slot.setKillMessage(0);
						slot.setLastKillMessage(0);
						slot.setOneTimeKills(0);
						slot.setAllKills(0);
						slot.setAllDeahts(0);
						slot.setBotScore(0);
					}

					room.setRedKills(0);
					room.setRedDeaths(0);
					room.setBlueKills(0);
					room.setBlueDeaths(0);
					room.setFigth(false);
				}
			}
		}
	}
}