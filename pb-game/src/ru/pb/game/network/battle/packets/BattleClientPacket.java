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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pb.game.network.battle.FromBattleToGameConnection;
import ru.pb.global.network.packets.BaseClientPacket;

/**
 * Базовый класс для пакетов от Гейм сервера в сервер авторизации
 *
 * @author sjke
 */
public abstract class BattleClientPacket extends BaseClientPacket<BattleClientPacket, FromBattleToGameConnection> implements Cloneable {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	protected BattleClientPacket(int opcode) {
		super(opcode);
	}

	@Override
	public final void run() {
		try {
			runImpl();
		} catch (Throwable e) {
			log.error("Error handling (" + getConnection().getIp() + "), message " + this, e);
		}
	}

	protected void sendPacket(BattleServerPacket msg) {
		getConnection().sendPacket(msg);
	}

	public BattleClientPacket clonePacket() {
		try {
			return (BattleClientPacket) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
