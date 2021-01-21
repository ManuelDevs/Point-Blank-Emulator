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

package ru.pb.global.dao.impl;

import ru.pb.global.dao.AccountDao;
import ru.pb.global.dao.jdbc.DatabaseFactory;
import ru.pb.global.dao.jdbc.DatabaseUtils;
import ru.pb.global.models.Account;

import java.sql.*;

import static ru.pb.global.dao.enums.QueryList.*;

/**
 * Дао для работы с аккаунтами
 *
 * @author Felixx
 */
public class AccountDaoImpl implements AccountDao {

	@Override
	public boolean accountExist(String login) {
		Connection con = null;
		PreparedStatement statement = null;
		try {
			con = DatabaseFactory.getInstance().newConnection();
			statement = con.prepareStatement(ACCOUNT_CHECK_BY_LOGIN.getQuery());
			statement.setString(1, login);
			statement.execute();
			return statement.getResultSet().next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCS(con, statement);
		}
		return false;
	}

	@Override
	public Account accountAuth(String login, String password) {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Account account = null;
		if (login == null || password == null) {
			return null;
		}
		try {
			con = DatabaseFactory.getInstance().newConnection();
			statement = con.prepareStatement(ACCOUNT_GET_BY_LOGIN_PASSWORD.getQuery());
			statement.setString(1, login);
			statement.setString(2, password);
			rs = statement.executeQuery();
			if (rs.next()) {
				account = new Account();
				account.setId(rs.getLong("id"));
				account.setLogin(rs.getString("login"));
				account.setPassword(rs.getString("password"));
				account.setEmail(rs.getString("email"));
				account.setMoney(rs.getInt("money"));
				account.setActive(rs.getBoolean("active"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCSR(con, statement, rs);
		}
		return account == null || account.getId() == null ? null : account;
	}

	@Override
	public boolean checkBanned(String ip, String mac) {
		Connection con = null;
		PreparedStatement statement = null;
		try {
			con = DatabaseFactory.getInstance().newConnection();
			statement = con.prepareStatement(ACCOUNT_CHECK_IN_BANNED.getQuery());
			statement.setString(1, ip);
			statement.setString(2, mac);
			statement.execute();
			return statement.getResultSet().next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCS(con, statement);
		}
		return false;
	}

	@Override
	public void setBanned(String mac, String lock, String unlock) {   // TODO Рома, доработай. Тип бана тоже указывать.
		Connection con = null;
		PreparedStatement statement = null;
		try {
			con = DatabaseFactory.getInstance().newConnection();
			statement = con.prepareStatement(ACCOUNT_BAD_LOGIN_INTERVAL.getQuery());
			statement.setString(1, mac);
			statement.setString(2, lock);
			statement.setString(3, unlock);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCS(con, statement);
		}
	}

	@Override
	public void create(Account account) {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			con = DatabaseFactory.getInstance().newConnection();
			statement = con.prepareStatement(ACCOUNT_CREATE.getQuery(), Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, account.getLogin());
			statement.setString(2, account.getPassword());
			statement.setString(3, account.getEmail());
			statement.setInt(4, account.getMoney());
			statement.setBoolean(5, account.isActive());
			statement.executeUpdate();

			rs = statement.getGeneratedKeys();
			rs.next();
			account.setId((Long) rs.getObject(1));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCSR(con, statement, rs);
		}
	}

	@Override
	public Account read(long id) {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Account account = null;
		try {
			con = DatabaseFactory.getInstance().newConnection();
			statement = con.prepareStatement(ACCOUNT_SELECT.getQuery());
			statement.setLong(1, account.getId());
			rs = statement.executeQuery();
			if (rs.next()) {
				account = new Account();
				account.setId(rs.getLong("id"));
				account.setLogin(rs.getString("login"));
				account.setPassword(rs.getString("password"));
				account.setEmail(rs.getString("email"));
				account.setMoney(rs.getInt("money"));
				account.setActive(rs.getBoolean("active"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCSR(con, statement, rs);
		}
		return account == null || account.getId() == null ? null : account;
	}

	@Override
	public Account readByLogin(String login) {
		if (login == null) {
			return null;
		}
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Account account = null;
		try {
			con = DatabaseFactory.getInstance().newConnection();
			statement = con.prepareStatement(ACCOUNT_SELECT_BY_LOGIN.getQuery());
			statement.setString(1, login);
			rs = statement.executeQuery();
			if (rs.next()) {
				account = new Account();
				account.setId(rs.getLong("id"));
				account.setLogin(rs.getString("login"));
				account.setPassword(rs.getString("password"));
				account.setEmail(rs.getString("email"));
				account.setMoney(rs.getInt("money"));
				account.setActive(rs.getBoolean("active"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCSR(con, statement, rs);
		}
		return account == null || account.getId() == null ? null : account;
	}

	@Override
	public void update(Account account) {
		Connection con = null;
		PreparedStatement statement = null;
		try {
			con = DatabaseFactory.getInstance().newConnection();
			statement = con.prepareStatement(ACCOUNT_UPDATE.getQuery());
			statement.setString(1, account.getLogin());
			statement.setString(2, account.getPassword());
			statement.setString(3, account.getEmail());
			statement.setInt(4, account.getMoney());
			statement.setBoolean(5, account.isActive());
			statement.setLong(6, account.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCS(con, statement);
		}
	}

	@Override
	public void delete(long id) {
		throw new UnsupportedOperationException("You cannot delete an account from the system");
	}
}
