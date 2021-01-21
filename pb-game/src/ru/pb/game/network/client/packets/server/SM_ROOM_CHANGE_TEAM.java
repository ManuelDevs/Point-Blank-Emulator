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
import ru.pb.global.enums.SlotState;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;
import ru.pb.global.models.RoomSlot;

/**
 * Неизместный пакет, отправляется вместе с пекетов авторизации
 *
 * @author sjke
 */
public class SM_ROOM_CHANGE_TEAM extends ServerPacket {

	private Player sender;
	private final Room room;
	private final RoomSlot leaderSlot;

	private int cmd, slotcount, newTeam;

	public SM_ROOM_CHANGE_TEAM(Player sender, Room room, int cmd, int slotcount, int newTeam) {
		super(0xF25);
		this.sender = sender;
		this.room = room;
		this.cmd = cmd;
		this.slotcount = slotcount;
		this.newTeam = newTeam;

		this.leaderSlot = room.getRoomSlotByPlayer(room.getLeader());
	}

	@Override
	public void writeImpl() {
		// Для вывода сообщения что лидер сменил местами команы, надо 2.
		// Для сообщения "Исходя из настроек баланса, было установлено одинаковое кол-во бойцов"
		// TODO сравнить с оффом как ето число получать.
		writeC(cmd); // Вызов сообщения о смене команды
		writeC(leaderSlot.getId() % 2 == 0 ? leaderSlot.getId() + 1 : leaderSlot.getId() - 1);
		writeC(slotcount);

		if (slotcount > 1) {
			for (int slot : room.RED_TEAM) {
				RoomSlot oldSlot = room.getRoomSlots()[slot];
				RoomSlot newSlot = room.getRoomSlots()[slot + 1];

				SlotState oldState = oldSlot.getState();
				oldSlot.setState(newSlot.getState());
				newSlot.setState(oldState);
				Player oldPlayer = oldSlot.getPlayer();
				oldSlot.setPlayer(newSlot.getPlayer());
				newSlot.setPlayer(oldPlayer);

				writeC(oldSlot.getId());
				writeC(newSlot.getId());
				writeC(oldSlot.getState().ordinal());
				writeC(newSlot.getState().ordinal());
			}
		} else {
			RoomSlot oldSlot = room.getRoomSlotByPlayer(sender);
			RoomSlot newSlot = room.changeTeam(sender, newTeam);

			writeC(oldSlot.getId()); // Номер старого слота.
			writeC(newSlot.getId()); // Номер нового слота.
			writeC(oldSlot.getState().ordinal()); // Установить флаг в старом слоте.
			writeC(newSlot.getState().ordinal()); // Установить флаг в новом слоте.
		}
	}
}