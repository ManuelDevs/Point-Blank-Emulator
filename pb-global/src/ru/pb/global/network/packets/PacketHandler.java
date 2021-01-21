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

package ru.pb.global.network.packets;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.pb.global.utils.NetworkUtil;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Базовый обработчик пакетов
 *
 * @author sjke
 */
public abstract class PacketHandler<P extends BaseClientPacket, C extends Connection> {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	protected final ConcurrentMap<Integer, P> packetsPrototypes = new ConcurrentSkipListMap<Integer, P>();

	protected void addPacketPrototype(P packetPrototype) {
		packetsPrototypes.put(packetPrototype.getOpcode(), packetPrototype);
	}

	protected P getPacket(int id, ByteBuf buffer, C client) {
		P prototype = packetsPrototypes.get(id);
		if (prototype == null) {
			unknownPacket(id, buffer, client);
			return null;
		}
		P res = (P) prototype.clonePacket();
		res.setBuf(buffer);
		res.setConnection(client);
		return res;
	}

	private void unknownPacket(int id, ByteBuf buffer, C client) {
		LoggerFactory.getLogger(PacketHandler.class).warn(client + NetworkUtil.printData(String.format(" received unknown packet : 0x%02X [int: %d]", id, id), buffer));
	}
}
