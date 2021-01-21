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
import ru.pb.global.utils.NetworkUtil;

import java.util.List;

/**
 * @author: Felixx
 */
public class SM_CLAN_ENTER extends ServerPacket {
	private final List<Clan> clanList;
	private final Player player;
	private final Clan clan;
	public SM_CLAN_ENTER(Player player, Clan clan, List<Clan> clanList) {
		super(0x5A2);
        this.clan = clan;
		this.clanList = clanList;
		this.player = player;
	}

	@Override
	public void writeImpl() {
		writeD(clan != null ? 1 : 0 );
		writeD(clan != null ? clan.getOwnerId() == player.getId() ? 1 : 2 : 0); // Привилегии в клане
		writeD(clanList.size()); // количество кланов
		writeB(NetworkUtil.hexStringToByteArray("AA 01 00 80 6C 44 37"));
	}
}