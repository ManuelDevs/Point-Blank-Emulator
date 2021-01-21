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
import ru.pb.global.models.LevelUpInfo;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;
import ru.pb.global.models.RoomSlot;
import ru.pb.global.service.LevelUpDaoService;

/**
 * Неизместный пакет, отправляется вместе с пекетов авторизации
 *
 * @author sjke
 */
public class SM_BATTLE_END extends ServerPacket {

	private final Player player;
	private final Room room;

	public SM_BATTLE_END(Player player, Room room) {
		super(0xD08);
		this.player = player;
		this.room = room;
	}

	@Override
	public void writeImpl() {

		//TODO переделать
		LevelUpInfo levelUpInfo = LevelUpDaoService.getInstance().getLevelInfoForRank((byte) (player.getRank() + 1));
		RoomSlot slotByPlayer = room.getRoomSlotByPlayer(player);
		player.setExp(player.getExp() + slotByPlayer.getAllExp());
		player.setGp(player.getGp() + slotByPlayer.getAllGp());

		if (player.getRank() <= 52) {
			if (levelUpInfo.getAllExp() < player.getExp()) {
				player.setRank((short) (player.getRank() + 1));
				player.setGp(player.getGp() + levelUpInfo.getRewardGp());
			}
		}
		//

		writeC(room.getRedKills() > room.getBlueKills() ? 0 : 1);// походу команда которая выигрывает
		writeH(383); // что это???
		writeH(266); // что это???

		// Опыт
		for (int i = 0; i < 16; i++) {
			RoomSlot slot = room.getRoomSlot(i);
			writeH(slot.getAllExp());
		}
		// Очки
		for (int i = 0; i < 16; i++) {
			RoomSlot slot = room.getRoomSlot(i);
			writeH(slot.getAllGp());
		}
		//Очки за ботов
		for (int i = 0; i < 16; i++) {
			if (room.getSpecial() == 6) {
				RoomSlot slot = room.getRoomSlot(i);
				int score = slot.getBotScore();
				writeH(score);
			} else writeH(0);
		}

		writeB(new byte[]{
				0x00, 0x00, 0x00, 0x00, 0x00, 0x00,//
				0x00, 0x00, 0x00, 0x00, 0x00, 0x00,//
				0x00, 0x00, 0x00, 0x00, 0x00, 0x00,//
				0x00, 0x00, 0x00, 0x00, 0x00, 0x00,//
				0x00, 0x00, 0x00, 0x00, 0x00, 0x00,//
				0x00, 0x00,//
				// и тут ХЗ
				0x00, 0x00, 0x00, 0x00, 0x00, 0x00,//
				0x00, 0x00, 0x00, 0x00, 0x00, 0x00,//
				0x00, 0x00, 0x00, 0x00
				//
		});

		writeS(player.getName(), Player.MAX_NAME_SIZE); // Имя перса
		writeD(player.getExp()); // опыт
		writeD(player.getRank()); // ранк (0-54)
		writeD(player.getRank()); // Пока не понятно за чего отвечают пустые байты...
		writeD(player.getGp()); // ГП
		writeD(con.getAccount().getMoney()); // Рублики

		writeD(0); // ClanID
		writeD(0); // ClanNameColor
		writeD(0); // Unk
		writeD(0); // Unk
		writeH(player.getPcCafe());
		writeC(player.getColor()); // 0-9 Color name

		if (player.getClan() == null) {
			writeS("", 16);
			writeC(0);
			writeH(0);
			writeC(255);
			writeC(255);
			writeC(255);
			writeC(255);
			writeH(0);
		} else {
			writeS(player.getClan().getName(), 16);
			writeC(0); // unk - Видимо разделитель
			writeH(player.getClan().getRank());
			writeC(player.getClan().getLogo1());
			writeC(player.getClan().getLogo2());
			writeC(player.getClan().getLogo3());
			writeC(player.getClan().getLogo4());
			writeH(player.getClan().getColor());
		}

		writeD(0); // Непонятно чо

		writeB(new byte[9]);

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

		writeB(new byte[53]);
	}
}