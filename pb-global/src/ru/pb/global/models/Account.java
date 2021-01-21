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


import ru.pb.global.network.packets.Connection;

/**
 * Модель аккаунта
 *
 * @author sjke
 */
public class Account {

	private Long id;
	private String login;
	private String password;
	private String email;
	private Integer money;
	private boolean active;
	private AccountActivity accountActivity;
	private Connection connection;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public AccountActivity getAccountActivity() {
		return accountActivity;
	}

	public void setAccountActivity(AccountActivity accountActivity) {
		this.accountActivity = accountActivity;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public String toString() {
		return "Account{" +
				"id=" + id +
				", login='" + login + '\'' +
				", password='" + password + '\'' +
				", email='" + email + '\'' +
				", money=" + money +
				", active=" + active +
				", accountActivity=" + accountActivity +
				", connection=" + connection +
				'}';
	}

	public Object[] toObject(boolean includeId) {    // TODO To Remove
		int size = 5;
		if (includeId) {
			size++;
		}
		Object[] args = new Object[size];
		args[0] = this.getLogin();
		args[1] = this.getPassword();
		args[2] = this.getEmail();
		args[3] = this.getMoney();
		args[4] = this.isActive();
		if (includeId) {
			args[5] = this.getId();
		}
		return args;
	}
}
