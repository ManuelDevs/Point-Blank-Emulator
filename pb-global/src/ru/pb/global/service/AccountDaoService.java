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

package ru.pb.global.service;

import ru.pb.global.dao.AccountDao;
import ru.pb.global.dao.DaoManager;
import ru.pb.global.models.Account;

/**
 * Сервис для работы с Аккаунтами
 *
 * @author sjke
 */
public class AccountDaoService {

	private static final AccountDao accountDao = DaoManager.getInstance().getAccountDao();

	private AccountDaoService() {
	}

	public static AccountDaoService getInstance() {
		return Singleton.INSTANCE;
	}

	public boolean checkBanned(String ip, String mac) {
		return accountDao.checkBanned(ip, mac);
	}

	public void setBanned(String mac, String lock, String unlock) {
		setBanned(mac, lock, unlock);
	}

	public Account accountAuth(String login, String password) {
		return accountDao.accountAuth(login, password);
	}

	public boolean accountExist(String login) {
		return accountDao.accountExist(login);
	}

	public void create(Account entity) {
		accountDao.create(entity);
	}

	public void update(Account entity) {
		accountDao.update(entity);
	}

	public Account read(Long id) {
		return accountDao.read(id);
	}

	public Account readByLogin(String login) {
		return accountDao.readByLogin(login);
	}

	private static class Singleton {
		private static final AccountDaoService INSTANCE = new AccountDaoService();
	}
}
