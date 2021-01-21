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

package ru.pb.global.enums;

/**
 * @author sjke
 */
public enum LoginAccess {
	/**
	 * Успешная авторизация.
	 */
	EVENT_ERROR_SUCCESS(0x00000001),

	/**
	 * Не удалось обработать запрос на вход (клиент закрывается)
	 */
	EVENT_ERROR_LOGIN(0x80000100),

	/**
	 * Данный логин в процессе подключения. (клиент закрывается)
	 */
	EVENT_ERROR_EVENT_LOG_IN_ALEADY_LOGIN(0x80000101),

	/**
	 * Неверный логин/пароль (клиент не закрывается)
	 */
	EVENT_ERROR_EVENT_LOG_IN_INVALID_ACCOUNT(0x80000102),

	/**
	 * Ваш аккаунт по-прежнему выходить из системы, пожалуйста, подождите (клиент не закрывается)
	 */
	EVENT_ERROR_EVENT_LOGOUTING(0x80000104),

	/**
	 * Неудалось войти - сеть перегружена. (Скорее всего када херовый пинг.) (клиент не закрывается)
	 */
	EVENT_ERROR_EVENT_LOG_IN_TIME_OUT1(0x80000105),

	/**
	 * Не удалось войти, попробуйте еще раз (клиент не закрывается)
	 */
	EVENT_ERROR_EVENT_LOG_IN_TIME_OUT2(0x80000106),

	/**
	 * Ваш аккаунт заблокирован изза нарушения правил (клиент не закрывается)
	 */
	EVENT_ERROR_EVENT_LOG_IN_BLOCK_ACCOUNT(0x80000107),

	/**
	 * Cервер переполнен (клиент не закрывается)
	 */
	EVENT_ERROR_EVENT_LOG_IN_MAXUSER(0x8000010E),

	/**
	 * Заблокирован по региону (клиент не закрывается)
	 */
	EVENT_ERROR_EVENT_LOG_IN_REGION_BLOCKED(0x80000121);

	private int _value;

	LoginAccess(int code) {
		_value = code;
	}

	public int get() {
		return _value;
	}
}
