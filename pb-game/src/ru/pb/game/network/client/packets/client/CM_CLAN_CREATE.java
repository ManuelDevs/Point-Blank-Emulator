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
import ru.pb.game.network.client.packets.server.SM_CLAN_CREATE;
import ru.pb.global.models.Clan;
import ru.pb.global.service.ClanDaoService;

/**
 * Создание клана, выполняется после тех проверок
 *
 * @author DarkSkeleton
 */
public class CM_CLAN_CREATE extends ClientPacket {

    private Clan clan;
	public CM_CLAN_CREATE(int opcode) {
		super(opcode);
        clan = new Clan();
	}

	@Override
	protected void readImpl() {
		int clanLen = readC();
		int descLen = readC();
		readC();
		clan.setName(readS(clanLen - 1));
        clan.setRank((short)0);
        clan.setLogo1((short)0);
        clan.setLogo2((short)0);
        clan.setLogo3((short)0);
        clan.setLogo4((short)0);
        clan.setOwnerId(getConnection().getPlayer().getId());
        clan.setColor((short)0);
        //clan.setOwnerId();
		String clanDescription = readS(descLen);
        ClanDaoService.getInstance().CreateClan(clan);
		getConnection().getPlayer().setClan(clan);
		//log.debug("Request create clan name=" + clanName);
	}

	@Override
	protected void runImpl() {
		sendPacket(new SM_CLAN_CREATE());
	}
}
