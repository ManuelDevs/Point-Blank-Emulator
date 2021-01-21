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

import java.io.UnsupportedEncodingException;
import java.nio.ByteOrder;

/**
 * Базовый пакет серверов
 *
 * @author sjke
 */
public abstract class BaseServerPacket extends BasePacket {

	protected BaseServerPacket(int opcode) {
		super(PacketType.SERVER, Unpooled.buffer(4).order(ByteOrder.LITTLE_ENDIAN), opcode);
	}

	/**
	 * Write int to buffer.
	 *
	 * @param value
	 */
	protected final void writeD(int value) {
		getBuf().writeInt(value);
	}

	/**
	 * Write short to buffer.
	 *
	 * @param value
	 */
	protected final void writeH(int value) {
		getBuf().writeShort((short) value);
	}

	/**
	 * Write byte to buffer.
	 *
	 * @param value
	 */
	protected final void writeC(int value) {
		getBuf().writeByte(value);
	}

	/**
	 * Write boolean to buffer.
	 *
	 * @param value
	 */
	protected final void writeBool(boolean value) {
		writeD(value ? 1 : 0);
	}

	/**
	 * Write double to buffer.
	 *
	 * @param value
	 */
	protected final void writeDF(double value) {
		getBuf().writeDouble(value);
	}

	/**
	 * Write float to buffer.
	 *
	 * @param value
	 */
	protected final void writeF(float value) {
		getBuf().writeFloat(value);
	}

	/**
	 * Write long to buffer.
	 *
	 * @param value
	 */
	protected final void writeQ(long value) {
		getBuf().writeLong(value);
	}

	/**
	 * Write String to buffer
	 *
	 * @param text
	 */
	protected final void writeS(String text) {
		writeS(text, text.length());
	}

	/**
	 * Writing string to buffer
	 *
	 * @param name
	 * @param count
	 */
	protected final void writeS(String name, int count) {
		if (name == null) {
			name = "";
		}
		try {
			writeB(name.getBytes("windows-1251"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		writeB(new byte[count - name.length()]);
	}

	/**
	 * Write byte array to buffer.
	 *
	 * @param data
	 */
	protected final void writeB(byte[] data) {
		getBuf().writeBytes(data, 0, data.length);
	}
}
