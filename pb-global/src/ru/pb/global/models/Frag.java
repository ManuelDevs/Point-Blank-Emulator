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

/**
 * @author: Felixx
 * Date: 02.10.13
 * Time: 6:42
 */
public class Frag {
	private int unk_c_1;
	private int deathMask;
	private int unk_c_3;
	private int unk_c_4;
	private byte[] bytes13;


	/**
	 * Пока что не известно.
	 *
	 * @param unk
	 */
	public void setUnkC1(int unk) {
		unk_c_1 = unk;
	}


	public int getUnkC1() {
		return unk_c_1;
	}

	/**
	 * Пока что не известно.
	 *
	 * @param unk
	 */
	public void setUnkC3(int unk) {
		unk_c_3 = unk;
	}

	/**
	 * Пока что не известно.
	 *
	 * @return
	 */
	public int getUnkC3() {
		return unk_c_3;
	}

	/**
	 * Пока что не известно.
	 *
	 * @param unk
	 */
	public void setUnkC4(int unk) {
		unk_c_4 = unk;
	}

	/**
	 * Пока что не известно.
	 *
	 * @return
	 */
	public int getUnkC4() {
		return unk_c_4;
	}

	/**
	 * Пока что не известно.
	 *
	 * @param unk
	 */
	public void setUnk13bytes(byte[] unk) {
		bytes13 = unk;
	}

	/**
	 * Пока что не известно.
	 *
	 * @return
	 */
	public byte[] getUnk13bytes() {
		return bytes13;
	}

	/**
	 * Возвращяет слот умершего.
	 *
	 * @return
	 */
	public int getDeatSlot() {
		return deathMask & 15;
	}

	/**
	 * Устанавливает маску для вычисления информации о Смерти
	 *
	 * @param mask
	 */
	public void setDeathMask(int mask) {
		deathMask = mask;
	}

	public int getDeathMask() {
		return deathMask;
	}
}