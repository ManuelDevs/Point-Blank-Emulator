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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Базовый пкет клиента
 *
 * @author sjke
 */
public abstract class BaseClientPacket<P extends BaseClientPacket, T extends Connection> extends BasePacket implements Cloneable, Runnable {

	private static final Logger log = LoggerFactory.getLogger(BaseClientPacket.class);
	private T client;

	protected BaseClientPacket(int opcode) {
		super(PacketType.CLIENT, opcode);
	}

	public final boolean read() {
		try {
			readImpl();
			return true;
		} catch (Exception re) {
			log.error("Reading failed for packet " + this, re);
			return false;
		}
	}

	protected abstract void readImpl();

	protected abstract void runImpl();

	public final T getConnection() {
		return client;
	}

	public void setConnection(T client) {
		this.client = client;
	}

	public final int getRemainingBytes() {
		return getBuf().readableBytes();
	}

	protected final int readD() {
		try {
			return getBuf().readInt();
		} catch (Exception e) {
			log.error("Missing D for: " + this);
		}
		return 0;
	}

	protected final int readC() {
		try {
			return getBuf().readByte() & 0xFF;
		} catch (Exception e) {
			log.error("Missing C for: " + this);
		}
		return 0;
	}

	protected final boolean readBool() {
		try {
			return getBuf().readInt() == 1;
		} catch (Exception e) {
			log.error("Missing Bool for: " + this);
		}
		return false;
	}

	protected final int readH() {
		try {
			return getBuf().readShort() & 0xFFFF;
		} catch (Exception e) {
			log.error("Missing H for: " + this);
		}
		return 0;
	}

	protected final double readDF() {
		try {
			return getBuf().readDouble();
		} catch (Exception e) {
			log.error("Missing DF for: " + this);
		}
		return 0;
	}

	protected final float readF() {
		try {
			return getBuf().readFloat();
		} catch (Exception e) {
			log.error("Missing F for: " + this);
		}
		return 0;
	}

	protected final long readQ() {
		try {
			return getBuf().readLong();
		} catch (Exception e) {
			log.error("Missing Q for: " + this);
		}
		return 0;
	}

	protected String readS(int size) {
		try {
			byte[] data = new byte[size];
			getBuf().readBytes(data);
			return new String(data, "windows-1251");
		} catch (Exception e) {
			log.error("Missing S for: " + this);
			e.printStackTrace();
		}
		return "";
	}

	protected final byte[] readB(int length) {
		byte[] result = new byte[length];
		try {
			getBuf().readBytes(result);
		} catch (Exception e) {
			log.error("Missing byte[] for: " + this);
		}
		return result;
	}

	protected final byte readB() {
		try {
			return getBuf().readByte();
		} catch (Exception e) {
			log.error("Missing byte[] for: " + this);
		}
		return -1;
	}

	public abstract P clonePacket();

}
