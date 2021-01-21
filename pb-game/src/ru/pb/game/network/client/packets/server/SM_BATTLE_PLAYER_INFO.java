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
import ru.pb.global.enums.item.ItemSlotType;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;

/**
 * Пакет начала боя
 *
 * @author sjke, DarkSkeleton
 */
public class SM_BATTLE_PLAYER_INFO extends ServerPacket {

	private final Room room;
	private final Player player;

	public SM_BATTLE_PLAYER_INFO(Room room, Player player) {
		super(0xD06);
		this.room = room;
		this.player = player;
	}

	@Override
	public void writeImpl() {
		writeBool(room.isFigth()); //В бою или не в бою(если же нет, то игрока выкинет из боя)
		writeD(room.getRoomSlotByPlayer(player).getId());
		writeD(player.getEqipment().getEquippedItemBySlot(ItemSlotType.CHAR_RED) == null ? 0 : player.getEqipment().getEquippedItemBySlot(ItemSlotType.CHAR_RED).getItem().getId());
		writeD(player.getEqipment().getEquippedItemBySlot(ItemSlotType.CHAR_BLUE) == null ? 0 : player.getEqipment().getEquippedItemBySlot(ItemSlotType.CHAR_BLUE).getItem().getId());
		writeD(player.getEqipment().getEquippedItemBySlot(ItemSlotType.CHAR_HEAD) == null ? 0 : player.getEqipment().getEquippedItemBySlot(ItemSlotType.CHAR_HEAD).getItem().getId());
		writeD(player.getEqipment().getEquippedItemBySlot(ItemSlotType.CHAR_ITEM) == null ? 0 : player.getEqipment().getEquippedItemBySlot(ItemSlotType.CHAR_ITEM).getItem().getId());
		writeD(player.getEqipment().getEquippedItemBySlot(ItemSlotType.CHAR_DINO) == null ? 0 : player.getEqipment().getEquippedItemBySlot(ItemSlotType.CHAR_DINO).getItem().getId());
		writeC(0x00); // wtf?
		writeC(0x00);
		writeC(0);
		//writeH(0x1c);
		writeC(0x01); //вывод надписи сверху экрана
	}
}