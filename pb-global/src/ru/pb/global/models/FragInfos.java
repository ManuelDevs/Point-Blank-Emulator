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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Модель хранящая в себе информацию об одном событии при убийстве.
 *
 * @author: Felixx
 */
public class FragInfos {
	private int victimIdx; // Не известно
	private int killer; // Кто убил
	private int killsCount; // Сколько человек померло
	private int weapon; // Оружие которым убило

	private byte[] bytes13; // Не разобранные байты.

	/**
	 * Карта хранящая обьекты с Инфой о одной смерти
	 */
	private Map<Integer, Frag> frags = new ConcurrentHashMap<Integer, Frag>(16);

	public FragInfos() {
	}

	/**
	 * Устанавливает слот убившего.
	 *
	 * @param id - номер cлота
	 */
	public void setKiller(int id) {
		killer = id;
	}

	/**
	 * Возвращает слот убившего.
	 *
	 * @return int номер слота
	 */
	public int getKiller() {
		return killer;
	}

	/**
	 * Добавляет обьект типа {@link Frag}
	 *
	 * @param i    - Позиция
	 * @param frag - Смерть
	 */
	public void addFrag(int i, Frag frag) {
		frags.put(i, frag);
	}

	/**
	 * Возвращает одну смерть из указанной позиции.
	 *
	 * @param i - Позиция
	 * @return - Смерть
	 */
	public Frag getFrag(int i) {
		return frags.get(i);
	}

	/**
	 * Устанавливает каким оружием было совершено событие убийства.
	 *
	 * @param id
	 */
	public void setKillWeapon(int id) {
		weapon = id;
	}

	/**
	 * Возвращает каким оружием было совершено событие убийства.
	 *
	 * @return int
	 */
	public int getKillWeaapon() {
		return weapon;
	}

	/**
	 * Возвращает картинку оружия которым попали в голову.
	 *
	 * @return
	 */
	public int getWeaponHeadNum() {
		return weapon / 100000;
	}

	/**
	 * Устанавливает сколько всего было Смертей в данном событии убийства.
	 *
	 * @param count
	 */
	public void setKillsCount(int count) {
		killsCount = count;
	}

	/**
	 * Возвращает сколько всего было Смертей в данном событии убийства.
	 *
	 * @return
	 */
	public int getKillsCount() {
		return killsCount;
	}

	/**
	 * Пока точно не известно.
	 *
	 * @param id
	 */
	public void setVicTimIdx(int id) { // ИД команды убийци? красн, син?
		victimIdx = id;
	}

	/**
	 * Пока точно не известно.
	 *
	 * @return
	 */
	public int getVicTimIdx() {
		return victimIdx;
	}

	/**
	 * Неразобранные байты.
	 *
	 * @param bytes
	 */
	public void setUnkBytes(byte[] bytes) {
		bytes13 = bytes;
	}

	/**
	 * Неразобранные байты.
	 *
	 * @return
	 */
	public byte[] getUnkBytes() {
		return bytes13;
	}
}