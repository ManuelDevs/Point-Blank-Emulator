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

package ru.pb.global.dao.enums;

public enum QueryList {

	/**
	 * Gameservers querys
	 */
	GET_ALL_GAMESERVERS("SELECT id, password, type, max_players FROM gameservers"),

	/**
	 * Channels querys
	 */
	GET_CHANNELS_BY_GAMESERVER_ID("SELECT id, type, announce FROM channels WHERE gameserver_id = ? ORDER BY id ASC;"),

	/**
	 * Account querys
	 */
	ACCOUNT_CREATE("INSERT INTO accounts(login, password, email, money, active) VALUES (?, ?, ?, ?, ?);"),
	ACCOUNT_SELECT("SELECT id, login, password, email, money, active FROM accounts WHERE id = ?"),
	ACCOUNT_SELECT_BY_LOGIN("SELECT id, login, password, email, money, active FROM accounts WHERE login = ?"),
	ACCOUNT_UPDATE("UPDATE accounts SET login=?, password=?, email=?, money=?, active= ? WHERE id = ?"),
	ACCOUNT_GET_BY_LOGIN_PASSWORD("SELECT id, login, password, email, money, active FROM accounts WHERE login = ? " +
			"and password  = ?"),
	ACCOUNT_CHECK_BY_LOGIN("SELECT id FROM accounts WHERE login = ?"),
	ACCOUNT_CHECK_IN_BANNED("SELECT type FROM account_banned WHERE (type = 'IP' and value = ?) OR (type = 'MAC' and value = ?)"),
	ACCOUNT_BAD_LOGIN_INTERVAL("INSERT INTO account_banned(type, value, date_lock, date_unlock, owner_id, reason) VALUES ('MAC', ?, ?, ?, '0', 'AutoBan: bad login interval')"),

	/**
	 * Player querys
	 */
	PLAYER_CREATE("INSERT INTO players(account_id, name, color, rank, gp, exp, pc_cafe, online) VALUES (?, ?, ?, ?, ?, ?, ?, ?);"),
	PLAYER_CREATE_STATS("INSERT INTO player_stats(player_id) VALUES(?);"),
	PLAYER_STATS_UPDATE("UPDATE player_stats SET season_fights=?, season_wins=?, season_losts=?, season_kills=?, " +
			"season_deaths=?, season_seria_wins=?, season_kpd=?, season_escapes=?, fights=?, wins=?, losts=?, " +
			"kills=?," +
			" deaths=?, seria_wins=?, kpd=?, escapes=? WHERE player_id=?"),
	PLAYER_EQUIPMENT_CHECK("SELECT * FROM player_eqipment WHERE player_id=? AND item_id=?"),
	PLAYER_EQUIPMENT_UPDATE("UPDATE player_eqipment SET item_id=?, count=?, loc=?, consume_lost=? WHERE player_id=? AND item_id=?"),
	PLAYER_EQUIPMENT_DELETE("DELETE FROM player_eqipment WHERE player_id=?"),
	PLAYER_EQUIPMENT_DELETE_BY_ITEM("DELETE FROM player_eqipment WHERE player_id=? AND item_id=?"),
	PLAYER_EQUIPMENT_INSERT("INSERT INTO player_eqipment(player_id, item_id, count, loc, consume_lost) VALUES (?, ?, ?, ?, ?);"),
	GET_ALL_ADMINS("SELECT accountID, level FROM account_access"),
	PLAYER_UPDATE("UPDATE players SET account_id=?, name=?, color=?, rank=?, gp=?, exp=?, pc_cafe=?, online=? WHERE id = ?"),
	PLAYER_SELECT("SELECT * FROM players WHERE account_id = ?;"),
	PLAYER_SELECT_STATS("SELECT * FROM player_stats WHERE player_id = ?;"),
	PLAYER_SELECT_EQIPMENT("SELECT pe.id, pe.count, pe.loc, pe.consume_lost, pe.item_id\n" +
			"FROM players p \n" +
			"LEFT JOIN player_eqipment pe ON (pe.player_id = p.id) \n" +
			"WHERE p.id = ?;"),
	TEMPLATE_SELECT_START_EQIPMENT("SELECT ti.item_id, tt.startmoney, tt.startgp, ti.template_id, ti.count, ti.equipped\n" +
			"FROM templates t LEFT JOIN template_items ti ON (ti.template_id = t.id) \n" +
			"LEFT join template_other tt on t.id = tt.template_id \n" +
			"WHERE active = TRUE AND date_start < CURRENT_TIMESTAMP AND date_stop > CURRENT_TIMESTAMP;"),

	PLAYER_CHECK_BY_NAME("SELECT id FROM players WHERE name = ?"),

	/**
	 * Clan
	 */
	CLAN_LIST("SELECT * FROM clans ORDER BY id ASC;"),
	CLAN_CREATE("INSERT INTO clans (name, rank, owner_id, color, logo1, logo2, logo3, logo4) VALUES (?,?,?,?,?,?,?,?);"),
	CLAN_GET("SELECT * FROM clans WHERE owner_id = ?"),

	/**
	 * Shop
	 */
	SHOP_GOOD_LIST("SELECT item_id, good_id, quantity, pricecredits, pricepoints, stocktype FROM goods;"),

	GET_ALL_FRIENDS("SELECT name, rank FROM players WHERE id IN (SELECT friend_id FROM player_friends WHERE player_account_id = ?)"),

	GET_LEVEL_LUP_INFOS("SELECT * FROM levelup"),
	GET_ALL_ITEMS("SELECT * FROM items"),

	GET_ALL_MAPS("SELECT * FROM maps ORDER BY id"),
	
	/**
	 * IPSystem querys
	 */
	GET_BLOCKED_IP("SELECT id, startpoint, endpoint FROM ipsystem WHERE type = ?");
	private String query;

	QueryList(final String query) {
		this.query = query;
	}

	public String getQuery(String... args) {
		return query;
	}

}
