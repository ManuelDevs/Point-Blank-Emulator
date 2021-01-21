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

package ru.pb.global.models;

import java.net.Inet4Address;

/**
 * @author: Felixx, sjke
 * Представляет IP-диапазона формата address/mask.
 */

public class IPMask extends IPNetwork {

	private Inet4Address i4addr;
	private byte maskCtr;
	private int addrInt;
	private int maskInt;

	public int getMaskInt() {
		return maskInt;
	}

	public void setMaskInt(int maskInt) {
		this.maskInt = maskInt;
	}

	public Inet4Address getI4addr() {
		return i4addr;
	}

	public void setI4addr(Inet4Address i4addr) {
		this.i4addr = i4addr;
	}

	public byte getMaskCtr() {
		return maskCtr;
	}

	public void setMaskCtr(byte maskCtr) {
		this.maskCtr = maskCtr;
	}

	public int getAddrInt() {
		return addrInt;
	}

	public void setAddrInt(int addrInt) {
		this.addrInt = addrInt;
	}

	@Override
	public String toString() {
		return "IPMask(" + getStart() + "/" + getEnd() + ")";
	}
}
