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
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;
import ru.pb.global.models.RoomSlot;

/**
 * Пакет входа в комнату
 *
 * @author sjke, DarkSkeleton
 */
public class SM_ROOM_ENTER extends ServerPacket {

	private final Room room;
	private final int slodId;
	private final int error;

	public SM_ROOM_ENTER(Room room, int slodId, int error) {
		super(0xC0A);
		this.room = room;
		this.slodId = slodId;
		this.error = error;
	}

	@Override
	public void writeImpl() {
		writeD(error);
		if (error >= 0) {
			writeD(slodId);
			writeD(room.getId());
			writeS(room.getName(), 23);
			writeC(room.getMapId());
			writeC(0);
			writeC(0);
			writeC(room.getType());
			writeC(5);
			writeC(room.getPlayers().size());
			writeC(room.getSlots());
			writeC(5); // ping ?!
			writeC(room.getAllWeapons());
			writeC(room.getRandomMap());
			writeC(room.getSpecial());
			writeS(room.getLeader().getName(), Player.MAX_NAME_SIZE);
			writeC(room.getKillMask());
			writeC(0);
			writeC(0);
			writeC(0);
			writeC(room.getLimit());
			writeC(room.getSeeConf());
			writeH(room.getAutobalans());
			writeD(0);
			writeC(0);
			writeD(room.getRoomSlotByPlayer(room.getLeader()).getId());

			for (RoomSlot slot : room.getRoomSlots()) {
				if (slot.getPlayer() == null) {
					writeC(slot.getState().ordinal());
					writeB(new byte[9]);
					writeC(255);
					writeC(255);
					writeC(255);
					writeC(255);
					writeC(0);
					writeB(new byte[27]);
				} else {
					writeC(slot.getState().ordinal());
					writeC(slot.getPlayer().getRank());
					writeD(0);
					writeD(0);
					writeC(slot.getPlayer() == null || slot.getPlayer().getClan() == null ? 255 : slot.getPlayer().getClan().getLogo1());
					writeC(slot.getPlayer() == null || slot.getPlayer().getClan() == null ? 255 : slot.getPlayer().getClan().getLogo2());
					writeC(slot.getPlayer() == null || slot.getPlayer().getClan() == null ? 255 : slot.getPlayer().getClan().getLogo3());
					writeC(slot.getPlayer() == null || slot.getPlayer().getClan() == null ? 255 : slot.getPlayer().getClan().getLogo4());
					writeC(slot.getPlayer() == null || slot.getPlayer().getClan() == null ? 0 : slot.getPlayer().getClan().getColor());
					writeB(new byte[6]);
					writeS(slot.getPlayer() == null || slot.getPlayer().getClan() == null ? "" : slot.getPlayer().getClan().getName(), Clan.CLAN_NAME_SIZE);
					writeD(0);
				}
			}

			writeC(room.getPlayers().size());
			for (Player player : room.getPlayers().values()) {
				writeC(room.getRoomSlotByPlayer(player).getId());
				writeC(Player.MAX_NAME_SIZE);
				writeS(player.getName(), Player.MAX_NAME_SIZE);
				writeC(player.getColor());
			}
			writeC(room.getAiCount()); // aiCount
			writeC(room.getAiLevel()); // aiLevel
		}
	}
}