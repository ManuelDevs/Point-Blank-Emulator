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

package ru.pb.global.dao;

import ru.pb.global.models.Account;

/**
 * Дао для работы с аккаунтами
 *
 * @author Felixx
 */
public interface AccountDao {

	/**
	 * Проверяем на существование логина
	 *
	 * @param login логин
	 * @return boolean
	 */
	boolean accountExist(String login);

	/**
	 * Получаем аккаунт по логину и паролю
	 *
	 * @param login    логин
	 * @param password пароль
	 * @return аккаунт
	 */
	Account accountAuth(String login, String password);

	/**
	 * Проверка на заблокированных пользователей
	 *
	 * @param ip  адрес
	 * @param mac адрес
	 * @return boolean
	 */
	boolean checkBanned(String ip, String mac);

	/**
	 * Запись заблокированного пользователя
	 *
	 * @param mac  аддрес
	 * @param lock дата блокировки
	 * @paran unlock дата разблокировки
	 */
	void setBanned(String mac, String lock, String unlock);

	/**
	 * Читаем аккаунт по логину (только для ГС)
	 *
	 * @param login логин
	 * @return аккаунт
	 */
	Account readByLogin(String login);


	void create(Account account);

	Account read(long id);

	void update(Account account);

	void delete(long id);

}
