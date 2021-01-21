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
 * Copyright (C) 2013 PBDevâ„¢
 */

package ru.pb.auth.controller;

import ru.pb.auth.network.client.ClientConnection;
import ru.pb.auth.properties.AuthServerProperty;
import ru.pb.global.controller.BaseController;
import ru.pb.global.enums.State;
import ru.pb.global.models.Account;
import ru.pb.global.service.AccountDaoService;

import java.security.MessageDigest;
import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ÐšÐ¾Ð½Ñ‚Ñ€Ð¾Ð»Ð»ÐµÑ€ Ð´Ð»Ñ� Ñ€Ð°Ð±Ð¾Ñ‚Ñ‹ Ñ� Ð°ÐºÐºÐ°ÑƒÐ½Ñ‚Ð°Ð¼Ð¸
 *
 * @author sjke
 */
public class AccountController extends BaseController {

	private ConcurrentMap<String, Account> accounts = new ConcurrentHashMap<String, Account>();
	private ConcurrentMap<String, Long> trialSessions = new ConcurrentHashMap<String, Long>();
	private Lock lock = new ReentrantLock();
	private Calendar c;

	private AccountController() {
		log.info("Loaded");
	}

	public static AccountController getInstance() {
		return Singleton.INSTANCE;
	}

	public void accountConnect(Account account) {
		accounts.put(account.getLogin(), account);
	}

	public boolean isAccountConnected(String login) {
		return accounts.containsKey(login);
	}

	public void accountDisconnect(String login) {
		if (isAccountConnected(login)) {
			accounts.remove(login);
		}
	}

	public Account getAccount(String account) {
		return accounts.get(account);
	}

	private boolean lengthInValid(String text, int length) {
		return text.length() < length;
	}

	private boolean hasBadSymbols(String checkLine, String badLine) {
		char[] badSymbols = badLine.toCharArray();
		for (char i : badSymbols) {
			if (checkLine.contains("" + i)) {
				return true;
			}
		}
		return false;
	}

	public State accountLogin(String login, String password, ClientConnection client, String clientVersion) {
		State state = State.INVALID;
		lock.lock();
		if (trialSessions.containsKey(client.getMac())) {
			long interval = System.currentTimeMillis() - trialSessions.get(client.getMac());
			if (interval <= AuthServerProperty.getInstance().INTERVAL_BETWEEN_LOGIN) {
				if (!AccountDaoService.getInstance().checkBanned(client.getIp(), client.getMac())) {
					c = Calendar.getInstance();
					String DATE_LOCK = c.getTime().toLocaleString();
					c.roll(12, 5);
					String DATE_UNLOCK = c.getTime().toLocaleString();
					AccountDaoService.getInstance().setBanned(client.getMac(), DATE_LOCK, DATE_UNLOCK);
				}
				log.info("User from IP: " + client.getIp() + " MAC: " + client.getMac() + " using password parser.");
			}
		} else {
			trialSessions.put(client.getMac(), System.currentTimeMillis());
		}
		
		if(!AuthServerProperty.getInstance().GAME_VERSION.equals(clientVersion))
		{
			state = State.INVALID;
			log.info("Access denied for account: " + login + ". Reason: wrong client. ['" + clientVersion + "' != '" + 
			AuthServerProperty.getInstance().GAME_VERSION + "']");
		}
		else if (lengthInValid(login, AuthServerProperty.getInstance().ACCOUNT_MIN_LENGTH) ||
				!lengthInValid(login, AuthServerProperty.getInstance().ACCOUNT_MAX_LENGTH) ||
				lengthInValid(password, AuthServerProperty.getInstance().PASSWORD_MIN_LENGTH) ||
				!lengthInValid(password, AuthServerProperty.getInstance().PASSWORD_MAX_LENGTH) ||
				hasBadSymbols(login, AuthServerProperty.getInstance().ACCOUNT_BAD_SYMBOLS) ||
				hasBadSymbols(password, AuthServerProperty.getInstance().PASSWORD_BAD_SYMBOLS)) {
			log.info("Account: " + login + " contains bad symbols or bad length.[Check the contents of login & password]");
			state = State.ERROR_AUTH;
		} else if (isAccountConnected(login)) {
			boolean disconect = false;  // TODO Config
			if(disconect){
				accountDisconnect(login);
			}
			state = State.ALREADY_EXIST;
			log.info("Access denied for account: " + login + ". Reason: already connected.");
		} else if (AccountDaoService.getInstance().checkBanned(client.getIp(), client.getMac())) {
			state = State.IP_BLOCKED;
			log.info("Access denied for account: " + login + ". Reason: ip blocked.");
		} else if (IPSystemController.getInstance().isInBlockedNetwork(client.getIp())) {
			state = State.REGION_BLOCKED;
			log.info("Access denied for account: " + login + ". Reason: region blocked.");
		} else {
			try {
				MessageDigest md = MessageDigest.getInstance("SHA-1");
				md.update(password.getBytes());
				StringBuffer result = new StringBuffer();
				for (byte byt : md.digest()) {
					result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
				}
				password = result.toString();
			} catch (Exception e) {
			}
			Account account = AccountDaoService.getInstance().accountAuth(login, password);
			if (account == null) {
				if (AuthServerProperty.getInstance().ACCOUNT_AUTO_CREATE && !AccountDaoService.getInstance().accountExist(login)) {
					account = new Account();
					account.setLogin(login);
					account.setPassword(password);
					account.setActive(true);
					account.setEmail(login + "@none.ru");
					account.setMoney(0);
					AccountDaoService.getInstance().create(account);
					log.info("Created new account: " + login);
					if (account.getId() != null) {
						state = authAccount(client, account);
					} else {
						state = State.ERROR_AUTH;
						log.info("Error create new account: " + login);
					}
				} else {
					state = State.ERROR_AUTH;
					log.info("Authorization failed to account: " + login);
				}
			} else {
				state = authAccount(client, account);
			}
		}
		lock.unlock();
		return state;
	}

	private State authAccount(ClientConnection client, Account account) {
		client.setAccount(account);
		account.setConnection(client);
		accountConnect(account);
		log.debug("Successful login to account: " + account);
		trialSessions.remove(client.getMac());
		return State.AUTHED;
	}

	private static class Singleton {
		private static final AccountController INSTANCE = new AccountController();
	}
}
