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

import io.netty.buffer.Unpooled;

import java.nio.ByteOrder;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Обработчик пакетов
 *
 * @author sjke
 */
public class PacketSheduler<P extends BaseServerPacket, C extends Connection> {

	private static final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
	private P packet;
	private C connect;
	private int delay;

	public PacketSheduler(final P packet, final C connect, int delay) {
		this.packet = packet;
		this.connect = connect;
		this.delay = delay;
	}

	public void startSheduler() {
		service.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				connect.sendPacket(packet);
				packet.setBuf(Unpooled.buffer(4).order(ByteOrder.LITTLE_ENDIAN));
			}
		}, 0, delay, TimeUnit.SECONDS);
	}

	public void stopSheduler() {
		service.shutdown();
	}

}
