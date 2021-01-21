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

package ru.pb.auth.network.client.packets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pb.auth.network.client.ClientConnection;
import ru.pb.global.network.packets.BaseClientPacket;

/**
 * Базовый класс для пакетов от Гейм сервера в сервер авторизации
 *
 * @author sjke
 */
public abstract class ClientPacket extends BaseClientPacket<ClientPacket, ClientConnection> implements Cloneable {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	protected ClientPacket(int opcode) {
		super(opcode);
	}

	@Override
	public final void run() {
		try {
			runImpl();
		} catch (Throwable e) {
			log.warn("Error handling gs (" + getConnection().getIp() + "), message " + this, e);
		}
	}

	protected void sendPacket(ServerPacket msg) {
		getConnection().sendPacket(msg);
	}

	@Override
	public ClientPacket clonePacket() {
		try {
			return (ClientPacket) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
