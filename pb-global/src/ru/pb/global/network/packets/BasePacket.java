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

/**
 * Базовый экземпляр пакета
 *
 * @author sjke
 */
public abstract class BasePacket {

	public static final String TYPE_PATTERN = "[%s] 0x%02X %s";
	private final PacketType packetType;
	private final int opcode;
	private ByteBuf buf;

	protected BasePacket(PacketType packetType, int opcode) {
		this.packetType = packetType;
		this.opcode = opcode;
	}

	protected BasePacket(PacketType packetType, ByteBuf buf, int opcode) {
		this.packetType = packetType;
		this.opcode = opcode;
		this.buf = buf;
	}

	public final int getOpcode() {
		return opcode;
	}

	public ByteBuf getBuf() {
		return buf;
	}

	public void setBuf(ByteBuf buf) {
		this.buf = buf;
	}

	public final PacketType getPacketType() {
		return packetType;
	}

	public String getPacketName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public String toString() {
		return String.format(TYPE_PATTERN, getPacketType().getName(), getOpcode(), getPacketName());
	}

	public static enum PacketType {
		SERVER("S"),
		CLIENT("C");
		private final String name;

		private PacketType(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
}
