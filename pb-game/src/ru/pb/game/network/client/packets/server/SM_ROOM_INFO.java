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
import ru.pb.global.models.Clan;
import ru.pb.global.models.Room;
import ru.pb.global.models.RoomSlot;

/**
 * Неизместный пакет, отправляется вместе с пекетов авторизации
 *
 * @author sjke
 */
public class SM_ROOM_INFO extends ServerPacket {

	private final Room room;

	public SM_ROOM_INFO(Room room) {
		super(0xF15);
		this.room = room;
	}

	@Override
	public void writeImpl() {

		if (room.getRoomSlotByPlayer(room.getLeader()) == null)
			room.setNewLeader();

		writeD(room.getRoomSlotByPlayer(room.getLeader()).getId());
		for (int i = 0; i < 16; i++) {
			RoomSlot slot = room.getRoomSlot(i);
			writeC(slot.getState().ordinal());
			writeC(slot.getPlayer() == null ? 0 : slot.getPlayer().getRank());
			writeB(new byte[9]); // unk
			writeC(slot.getPlayer() == null || slot.getPlayer().getClan() == null ? 255 : slot.getPlayer().getClan().getLogo1());
			writeC(slot.getPlayer() == null || slot.getPlayer().getClan() == null ? 255 : slot.getPlayer().getClan().getLogo2());
			writeC(slot.getPlayer() == null || slot.getPlayer().getClan() == null ? 255 : slot.getPlayer().getClan().getLogo3());
			writeC(slot.getPlayer() == null || slot.getPlayer().getClan() == null ? 255 : slot.getPlayer().getClan().getLogo4());
			writeC(slot.getPlayer() == null || slot.getPlayer().getClan() == null ? 0 : slot.getPlayer().getClan().getColor());
			writeB(new byte[6]); // unk
			writeS(slot.getPlayer() == null || slot.getPlayer().getClan() == null ? "" : slot.getPlayer().getClan().getName(), Clan.CLAN_NAME_SIZE);
			writeH(0); // unk
			writeC(0); // unk
		}
	}
}