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
import ru.pb.game.network.client.packets.server.PROTOCOL_BATTLE_RESPAWN_ACK;
import ru.pb.global.enums.item.ItemSlotType;
import ru.pb.global.models.Player;
import ru.pb.global.models.PlayerItem;
import ru.pb.global.models.RoomSlot;
import ru.pb.global.utils.concurrent.ThreadPoolManager;

public class PROTOCOL_BATTLE_USER_RESPAWN_REQ extends ClientPacket {

	private int first, second, third, fourth, fifth, id, red, blue, head, beret, dino;

	public PROTOCOL_BATTLE_USER_RESPAWN_REQ(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
		first = checkItem(readD(), getConnection().getPlayer().getEqipment().getEquippedItemBySlot(ItemSlotType.PRIM));
		second = checkItem(readD(), getConnection().getPlayer().getEqipment().getEquippedItemBySlot(ItemSlotType.SUB));
		third = checkItem(readD(), getConnection().getPlayer().getEqipment().getEquippedItemBySlot(ItemSlotType.MELEE));
		fourth = checkItem(readD(), getConnection().getPlayer().getEqipment().getEquippedItemBySlot(ItemSlotType.THROWING));
		fifth = checkItem(readD(), getConnection().getPlayer().getEqipment().getEquippedItemBySlot(ItemSlotType.ITEM));
		id = readD();
		red = checkItem(readD(), getConnection().getPlayer().getEqipment().getEquippedItemBySlot(ItemSlotType.CHAR_RED));
		blue = checkItem(readD(), getConnection().getPlayer().getEqipment().getEquippedItemBySlot(ItemSlotType.CHAR_BLUE));
		head = checkItem(readD(), getConnection().getPlayer().getEqipment().getEquippedItemBySlot(ItemSlotType.CHAR_HEAD));
		beret = checkItem(readD(), getConnection().getPlayer().getEqipment().getEquippedItemBySlot(ItemSlotType.CHAR_ITEM));
		dino = checkItem(readD(), getConnection().getPlayer().getEqipment().getEquippedItemBySlot(ItemSlotType.CHAR_DINO));
		readC();
	}

	private int checkItem(int oldId, PlayerItem newItem) {
		if (oldId == 0 && newItem != null) {
			return newItem.getItem().getId();
		} else {
			return oldId;
		}
	}

	@Override
	public void runImpl() {
		for (Player member : getConnection().getRoom().getPlayers().values()) {
			RoomSlot slot = getConnection().getRoom().getRoomSlotByPlayer(member);
			if (slot.getState().ordinal() > 8)
				member.getConnection().sendPacket(new PROTOCOL_BATTLE_RESPAWN_ACK(getConnection().getRoom(), getConnection().getRoom().getRoomSlotByPlayer(getConnection().getPlayer()).getId(), first, second, third, fourth, fifth, id, red, blue, head, beret, dino));
		}
	}
}