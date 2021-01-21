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

package ru.pb.auth.network.client.packets.server;

import ru.pb.auth.network.client.packets.ServerPacket;
import ru.pb.global.enums.item.ItemConsumeType;
import ru.pb.global.enums.item.ItemSlotType;
import ru.pb.global.enums.item.ItemType;
import ru.pb.global.models.*;

public class PROTOCOL_BASE_USER_INFO_ACK extends ServerPacket {
	private Player player;
	private Clan clan;
	private Account account;

	public PROTOCOL_BASE_USER_INFO_ACK(Player player, Account account) {
		super(0xA06);
		this.account = account;
		this.player = player;
		this.clan = player.getClan();
	}

	@Override
	public void writeImpl() {
		writeD(0); // Error
		writeC(0);
		writeS(player.getName(), Player.MAX_NAME_SIZE);
		writeD(player.getExp());
		writeD(player.getRank());
		writeD(player.getRank()); 
		writeD(player.getGp());
		writeD(account.getMoney());
		writeD(0);
		writeD(0); 
		writeQ(0);
		writeC(0);
		writeC(0); // Tourney level
		writeC(player.getColor());
		writeS(clan == null ? "" : player.getClan().getName(), Clan.CLAN_NAME_SIZE);
		writeH(clan == null ? 0 : player.getClan().getRank());
		writeC(clan == null ? 255 : player.getClan().getLogo1());
		writeC(clan == null ? 255 : player.getClan().getLogo2());
		writeC(clan == null ? 255 : player.getClan().getLogo3());
		writeC(clan == null ? 255 : player.getClan().getLogo4());
		writeC(clan == null ? 0 : player.getClan().getColor());

		 writeD(10000);
         writeC(0);
         writeD(0);
         writeD(0); // last rankup data

		writeD(player.getStats().getFights());
		writeD(player.getStats().getWins());
		writeD(player.getStats().getLosts());
		writeD(player.getStats().getDraws());
		writeD(player.getStats().getKills());
		writeD(player.getStats().getSeriaWins());
		writeD(player.getStats().getDeaths());
		writeD(0); // total fights
		writeD(player.getStats().getKpd());
		writeD(player.getStats().getEscapes());
		
		writeD(player.getStats().getSeasonFights());
		writeD(player.getStats().getSeasonWins());
		writeD(player.getStats().getSeasonLosts());
		writeD(player.getStats().getSeasonDraws());
		writeD(player.getStats().getSeasonKills());
		writeD(player.getStats().getSeasonSeriaWins());
		writeD(player.getStats().getSeasonDeaths());
		writeD(0); // total fights
		writeD(player.getStats().getSeasonKpd());
		writeD(player.getStats().getSeasonEscapes());

		writeEquipedItems(player.getEqipment());

		writeH(0);
		writeD(55); //fakerank
		writeD(55); //fakerank
		writeS("", 33); //fakenick
		writeH(0); // sight color
		writeC(31);

		writeC(0);  // Outpost
		writeD(0); 
		writeD(0);
		writeD(0);
		writeD(0);
		writeC(1); // actual mission
		writeC(1); // card 1
		writeC(0); // card 2
		writeC(0); // card 3
		writeC(0); // card 4
		writeB(new byte[20]); // card info 1
		writeB(new byte[20]); // card info 2
		writeB(new byte[20]); // card info 3 
		writeB(new byte[20]); // card info 4 
	}

	private void writeEquipedItems(PlayerEqipment equipment) {
		writeD(equipment.getEquippedItemBySlot(ItemSlotType.CHAR_RED) == null ? 0 : equipment.getEquippedItemBySlot(ItemSlotType.CHAR_RED).getItem().getId());
		writeD(equipment.getEquippedItemBySlot(ItemSlotType.CHAR_BLUE) == null ? 0 : equipment.getEquippedItemBySlot(ItemSlotType.CHAR_BLUE).getItem().getId());
		writeD(equipment.getEquippedItemBySlot(ItemSlotType.CHAR_HEAD) == null ? 0 : equipment.getEquippedItemBySlot(ItemSlotType.CHAR_HEAD).getItem().getId());
		writeD(equipment.getEquippedItemBySlot(ItemSlotType.CHAR_ITEM) == null ? 0 : equipment.getEquippedItemBySlot(ItemSlotType.CHAR_ITEM).getItem().getId());
		writeD(equipment.getEquippedItemBySlot(ItemSlotType.CHAR_DINO) == null ? 0 : equipment.getEquippedItemBySlot(ItemSlotType.CHAR_DINO).getItem().getId());

		writeD(equipment.getEquippedItemBySlot(ItemSlotType.PRIM) == null ? 0 : equipment.getEquippedItemBySlot(ItemSlotType.PRIM).getItem().getId());
		writeD(equipment.getEquippedItemBySlot(ItemSlotType.SUB) == null ? 0 : equipment.getEquippedItemBySlot(ItemSlotType.SUB).getItem().getId());
		writeD(equipment.getEquippedItemBySlot(ItemSlotType.MELEE) == null ? 0 : equipment.getEquippedItemBySlot(ItemSlotType.MELEE).getItem().getId());
		writeD(equipment.getEquippedItemBySlot(ItemSlotType.THROWING) == null ? 0 : equipment.getEquippedItemBySlot(ItemSlotType.THROWING).getItem().getId());
		writeD(equipment.getEquippedItemBySlot(ItemSlotType.ITEM) == null ? 0 : equipment.getEquippedItemBySlot(ItemSlotType.ITEM).getItem().getId());

	}

	private void writeEquipment(PlayerEqipment equipment) {
		writeD(equipment.getItemsByType(ItemType.CHARACTER).size());
		writeD(equipment.getItemsByType(ItemType.WEAPON).size());
		writeD(equipment.getItemsByType(ItemType.COUPON).size());
		writeD(equipment.getItemsByType(ItemType.NEWITEM).size());
		for (PlayerItem item : equipment.getItemsByType(ItemType.CHARACTER)) {
			writeQ(item.getItem().getConsumeType() == ItemConsumeType.PERMANENT ? 0 : item.getId());
			writeD(item.getItem().getId());
			writeC(item.getItem().getConsumeType().getValue());
			writeD(item.getConsumeLost());
		}
		for (PlayerItem item : equipment.getItemsByType(ItemType.WEAPON)) {
			writeQ(item.getItem().getConsumeType() == ItemConsumeType.PERMANENT ? 0 : item.getId());
			writeD(item.getItem().getId());
			writeC(item.getItem().getConsumeType().getValue());
			writeD(item.getConsumeLost());
		}
		for (PlayerItem item : equipment.getItemsByType(ItemType.COUPON)) {
			writeQ(item.getItem().getConsumeType() == ItemConsumeType.PERMANENT ? 0 : item.getId());
			writeD(item.getItem().getId());
			writeC(item.getItem().getConsumeType().getValue());
			writeD(item.getConsumeLost());
		}
		for (PlayerItem item : equipment.getItemsByType(ItemType.NEWITEM)) {
			writeQ(item.getItem().getConsumeType() == ItemConsumeType.PERMANENT ? 0 : item.getId());
			writeD(item.getItem().getId());
			writeC(item.getItem().getConsumeType().getValue());
			writeD(item.getConsumeLost());
		}
	}
}