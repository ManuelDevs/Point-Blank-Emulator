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

/**
 * Ответ информации об аккаунте
 *
 * @author sjke
 */
public class SM_ACCOUNT_INFO extends ServerPacket {
	private Player player;
	private Clan clan;
	private Account account;

	public SM_ACCOUNT_INFO(Player player, Account account) {
		super(0xA06);
		this.account = account;
		this.player = player;
		this.clan = player.getClan();
	}

	@Override
	public void writeImpl() {
		writeD(0);
		writeC(34); // unk
		writeS(player.getName(), Player.MAX_NAME_SIZE);
		writeD(player.getExp());
		writeD(player.getRank());
		writeD(player.getRank()); // как ни странно летит тоже число что и в ранге...
		writeD(player.getGp());
		writeD(account.getMoney());
		writeD(0); // ClanID
		writeD(0); // ClanNameColor
		writeD(0); // Unk
		writeD(0); // Unk
		writeH(player.getPcCafe());
		writeC(player.getColor()); // 0-9 Color name

		writeS(clan == null ? "" : player.getClan().getName(), Clan.CLAN_NAME_SIZE);
		writeH(clan == null ? 0 : player.getClan().getRank()); // Ранг клана
		writeC(clan == null ? 255 : player.getClan().getLogo1()); //D -1
		writeC(clan == null ? 255 : player.getClan().getLogo2());
		writeC(clan == null ? 255 : player.getClan().getLogo3());
		writeC(clan == null ? 255 : player.getClan().getLogo4());
		writeC(clan == null ? 0 : player.getClan().getColor()); // Цвет клана

		writeC(0); // unk
		writeD(0); // unk
		writeD(0);  // TODO посмотреть снифф
		writeD(0);  // TODO посмотреть снифф

		writeD(player.getStats().getFights());
		writeD(player.getStats().getWins());
		writeD(player.getStats().getLosts());
		writeD(0); // unk
		writeD(player.getStats().getKills());
		writeD(player.getStats().getSeriaWins());
		writeD(player.getStats().getDeaths());
		writeD(0); // unk
		writeD(player.getStats().getKpd());
		writeD(player.getStats().getEscapes());

		/* TODO Почему так?
		 * WriteD(_stats.SeasonBattles);
		 * WriteD(_stats.SeasonWins);
		 * WriteD(_stats.SeasonLosses);
		 * WriteD(o);
		 * WriteD(_stats.SeasonKills);
		 * WriteD(_stats.SeasonHeadshots);
		 * WriteD(_stats.SeasonDeaths);
		 * WriteD(_stats.SeasonBattles);
		 * WriteD(_stats.SeasonKills);
		 * WriteD(_stats.SeasonLeaves);
		 */
		writeD(player.getStats().getSeasonFights());
		writeD(player.getStats().getSeasonWins());
		writeD(player.getStats().getSeasonLosts());
		writeD(0); // unk
		writeD(player.getStats().getSeasonKills());
		writeD(player.getStats().getSeasonSeriaWins());
		writeD(player.getStats().getSeasonDeaths());
		writeD(0); // unk
		writeD(player.getStats().getSeasonKpd());
		writeD(player.getStats().getSeasonEscapes());

		writeEquipedItems(player.getEqipment());

		writeB(new byte[40]);
		writeC(1);  // WriteC(Auth.AuthConfig.EnableInventory); ??

		writeEquipment(player.getEqipment());

		writeC(0);  // Outpost
		writeD(0);  // Медаль: Лента.
		writeD(0);  // Медаль: Знаки отличия.
		writeD(0);  // Медаль: сама медаль.
		writeD(0);  // Медаль: Медаль мастера.
		writeB(new byte[]{
				0x00, //текущий номер карточки
				0x01, 0x00, 0x00, 0x00,
				(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
				(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
				(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
				(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
				(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
				0x00, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x01, 0x00,
				0x00, 0x00, 0x00, 0x00,
		});

		writeB(new byte[]{
				0x01, 0x01, 0x01, 0x01, 0x02, 0x02, //выполнение карточек(килов входов(количество)
				0x01, 0x01, 0x02, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x02, 0x02, 0x01, 0x01, 0x01, 0x01, 0x02,
				0x01, 0x03, 0x01, 0x00, 0x01, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		});
		writeB(new byte[8]);
		writeB(new byte[3]);
		writeD(1);
		writeB(new byte[]{
				0x00, 0x00, 0x00, 0x00, 0x39, 0x00, 0x00, 0x00, 0x19, 0x00, 0x00, 0x00, 0x23, 0x00, 0x00, 0x00,
				0x01, 0x00, 0x00, 0x00, 0x27, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, //52 байта
				0x00, 0x28, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x2C, 0x00, 0x00,
				0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		});
		writeC(0x3C);
		writeC(2);
		writeB(new byte[]{
				(byte) 0xFE, (byte) 0xFF, (byte) 0xFE, (byte) 0xBF, (byte) 0xCF, 0x77, 0x07, 0x02,
		});
		//Карты, режимы
		writeB(new byte[]{
				0x00, 0x00, (byte) 0x8D, 0x01, (byte) 0x88, 0x00, (byte) 0x89, 0x00, (byte) 0x8D, 0x00,
				(byte) 0x8D, 0x00, (byte) 0x8D, 0x00, (byte) 0x8D, 0x01, 0x09, 0x00, 0x01, 0x00, 0x00, 0x00,
				(byte) 0x8D, 0x00, (byte) 0x80, 0x00, (byte) 0x88, 0x00, (byte) 0x88, 0x00, (byte) 0x88, 0x00,
				0x00, 0x00, 0x00, 0x00, (byte) 0x8C, 0x00, (byte) 0x88, 0x00, (byte) 0x88, 0x00, (byte) 0x88,
				0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00, (byte) 0x80, 0x00, (byte) 0x80, 0x00, 0x00, 0x00,
				(byte) 0x80, 0x00, (byte) 0x80, 0x00, (byte) 0x80, 0x00, (byte) 0x80, 0x00, (byte) 0x80, 0x00,
				(byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00, 0x08,
				0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		});
		writeB(new byte[]{
				0x00, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
				0x00, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00,
				0x01, 0x01, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00,
		});
		writeB(new byte[]{
				0x01, (byte) 0xEE, (byte) 0xDF, 0x03, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x05, 0x00, 0x6E, 0x6F, 0x6E, 0x65, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
		});

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
			writeQ(item.getItem().getConsumeType() == ItemConsumeType.PERMANENT ? 0 : item.getId()); // Постоянным айди не пишем
			writeD(item.getItem().getId()); // айди итемки
			writeC(item.getItem().getConsumeType().getValue()); // Тип: На время на бои или постоянная
			writeD(item.getConsumeLost()); // Сколько боев осталось или время когда кончается
		}
		for (PlayerItem item : equipment.getItemsByType(ItemType.WEAPON)) {

			writeQ(item.getItem().getConsumeType() == ItemConsumeType.PERMANENT ? 0 : item.getId()); // Постоянным айди не пишем
			writeD(item.getItem().getId()); // айди итемки
			writeC(item.getItem().getConsumeType().getValue()); // Тип: На время на бои или постоянная
			writeD(item.getConsumeLost()); // Сколько боев осталось или время когда кончается


			/*writeD((int)item.getId()); // Присваиваем ип интемки в инвентаре
			writeD(0);// Хз
			writeD(item.getItem().getId()); // Ид интемки
			writeC(1); // Тип оружия
			writeH(67); // Кол-во
			writeH(0);// хз */
		}
		for (PlayerItem item : equipment.getItemsByType(ItemType.COUPON)) {
			writeQ(item.getItem().getConsumeType() == ItemConsumeType.PERMANENT ? 0 : item.getId()); // Постоянным айди не пишем
			writeD(item.getItem().getId()); // айди итемки
			writeC(item.getItem().getConsumeType().getValue()); // Тип: На время на бои или постоянная
			writeD(item.getConsumeLost()); // Сколько боев осталось или время когда кончается
		}
		for (PlayerItem item : equipment.getItemsByType(ItemType.NEWITEM)) {
			writeQ(item.getItem().getConsumeType() == ItemConsumeType.PERMANENT ? 0 : item.getId()); // Постоянным айди не пишем
			writeD(item.getItem().getId()); // айди итемки
			writeC(item.getItem().getConsumeType().getValue()); // Тип: На время на бои или постоянная
			writeD(item.getConsumeLost()); // Сколько боев осталось или время когда кончается
		}
	}
}