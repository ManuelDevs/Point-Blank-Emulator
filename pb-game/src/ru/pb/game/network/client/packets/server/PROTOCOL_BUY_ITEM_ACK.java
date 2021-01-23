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
import ru.pb.global.enums.item.ItemConsumeType;
import ru.pb.global.models.Player;
import ru.pb.global.models.PlayerItem;
import ru.pb.global.utils.DateTimeUtil;

public class PROTOCOL_BUY_ITEM_ACK extends ServerPacket {

	private int error;
	private PlayerItem item;
	private Player player;

	public PROTOCOL_BUY_ITEM_ACK(int error, PlayerItem item, Player player) {
		super(0x213);
		this.error = error;
		this.item = item;
		this.player = player;
	}

	@Override
	public void writeImpl() {
		if (error == 0) {
			writeD(1);
			writeD(DateTimeUtil.getDateTime());
			writeD(item.getItem().getSlotType().ordinal() >= 5 && item.getItem().getSlotType().ordinal() < 10 ? 1 : 0);
			writeD(item.getItem().getSlotType().ordinal() < 5 ? 1 : 0);
			writeD(item.getItem().getSlotType().ordinal() >= 10 ? 1 : 0);

			writeQ(item.getId());
			writeD(item.getItem().getId());
			writeC(item.getConsumeLost());
			writeD(item.getCount());
			
			writeD(player.getGp());
			writeD(con.getAccount().getMoney());
		} else {
			writeD(error);
		}
	}

}