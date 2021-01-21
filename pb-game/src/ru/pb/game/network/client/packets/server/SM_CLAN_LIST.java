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

import java.util.List;

/**
 * Пакет списка кланов
 * TODO: Доделать определение количества участников в клане и макс количество участников
 *
 * @author DarkSkeleton
 */
public class SM_CLAN_LIST extends ServerPacket {

	private List<Clan> clans = null;

	public SM_CLAN_LIST(List<Clan> clans) {
		super(0x5A6);
		this.clans = clans;
	}

	@Override
	public void writeImpl() {
		writeD(0); //номер пачки
		//цикл?
		for (Clan clan : clans) {
			writeC(0xAA);
			writeD(clan.getId().intValue());  //номер клана в списке? //0x41
			writeS(clan.getName(), 17);
			writeC(clan.getRank()); //хз что может ранг клана?
			writeC(1); //количество участников в клане --- TODO: Количество участников... как подсчитать
			writeC(50); //макс состав...?
			writeD(20991231);//дата создания клана 2099-12-31(Epic Fail)
			writeB(new byte[]{0x01, 0x08, 0x1e, 0x1a}); //остальные параметры наверное
		}
	}
}
