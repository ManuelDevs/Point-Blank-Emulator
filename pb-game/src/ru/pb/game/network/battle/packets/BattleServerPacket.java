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

package ru.pb.game.network.battle.packets;

import ru.pb.game.network.battle.FromBattleToGameConnection;
import ru.pb.global.network.packets.BaseServerPacket;

/**
 * Базовый класс пакетов для связи сервера авторизации и гейм сервера
 *
 * @author sjke
 */
public abstract class BattleServerPacket extends BaseServerPacket {

	protected FromBattleToGameConnection con;

	public BattleServerPacket(int opcode) {
		super(opcode);
	}

	public void writeImpl(FromBattleToGameConnection con) {
		this.con = con;
		writeImpl();
		byte[] data = getBuf().array();
		getBuf().clear();
		getBuf().capacity(data.length + 4);
		writeH(data.length);
		writeH(getOpcode());
		writeB(data);
		con.showPackageHex(getBuf());
		con.getChannel().writeAndFlush(getBuf());
	}

	public abstract void writeImpl();
}
