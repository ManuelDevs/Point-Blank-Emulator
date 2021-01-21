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

package ru.pb.global.dao.impl;

import ru.pb.global.controller.PlayerTemplateController;
import ru.pb.global.dao.PlayerDao;
import ru.pb.global.dao.jdbc.DatabaseFactory;
import ru.pb.global.dao.jdbc.DatabaseUtils;
import ru.pb.global.data.holder.ItemHolder;
import ru.pb.global.enums.item.ItemLocation;
import ru.pb.global.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ru.pb.global.dao.enums.QueryList.*;

/**
 * Ð”Ð°Ð¾ Ð´Ð»Ñ� Ñ€Ð°Ð±Ð¾Ñ‚Ñ‹ Ñ� Ð¸Ð³Ñ€Ð¾ÐºÐ°Ð¼Ð¸
 *
 * @author sjke
 */
public class PlayerDaoImpl implements PlayerDao {

	@Override
	public boolean playerExist(String name) {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			con = DatabaseFactory.getInstance().newConnection();
			statement = con.prepareStatement(PLAYER_CHECK_BY_NAME.getQuery());
			statement.setString(1, name);
			statement.execute();
			rs = statement.getResultSet();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCSR(con, statement, rs);
		}
		return false;
	}

	@SuppressWarnings("resource")
	@Override
	public void create(Player entity) {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			con = DatabaseFactory.getInstance().newConnection();
			statement = con.prepareStatement(PLAYER_CREATE.getQuery(), Statement.RETURN_GENERATED_KEYS);
			statement.setLong(1, entity.getAccountId());
			statement.setString(2, entity.getName());
			statement.setByte(3, entity.getColor());
			statement.setInt(4, entity.getRank());
			statement.setInt(5, entity.getGp());
			statement.setInt(6, entity.getExp());
			statement.setShort(7, entity.getPcCafe());
			statement.setBoolean(8, entity.getOnline());
			statement.executeUpdate();
			
			rs = statement.getGeneratedKeys();
			rs.next();

			entity.setId((Long) rs.getObject(1));
			createEquipment(entity);
			
			statement = con.prepareStatement(PLAYER_CREATE_STATS.getQuery(), Statement.RETURN_GENERATED_KEYS);
			statement.setLong(1, entity.getId());
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCSR(con, statement, rs);
		}
	}

	private void createEquipment(Player entity) {
		Connection con = null;
		PreparedStatement statement = null;
		try {
			con = DatabaseFactory.getInstance().newConnection();
			statement = con.prepareStatement(PLAYER_EQUIPMENT_INSERT.getQuery());
			statement.setLong(1, entity.getId());
			for (PlayerItem item : PlayerTemplateController.getInstance().getTemplate().getItems()) {
				statement.setLong(2, item.getItem().getId());
				statement.setLong(3, item.getCount());
				statement.setString(4, item.getItemLocation().name());
				statement.setLong(5, item.getConsumeLost());
				statement.executeUpdate();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCS(con, statement);
		}
	}

	@SuppressWarnings("resource")
	@Override
	public Player read(Long accountID) {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Player player = null;
		if (accountID <= 0) {
			return null;
		}
		try {
			con = DatabaseFactory.getInstance().newConnection();
			statement = con.prepareStatement(PLAYER_SELECT.getQuery());
			statement.setLong(1, accountID);
			rs = statement.executeQuery();
			if(rs.next())
			{
				player = new Player();
				player.setId(rs.getLong("id"));
				player.setAccountId(rs.getLong("account_id"));
				player.setName(rs.getString("name"));
				player.setColor(rs.getByte("color"));
				player.setRank(rs.getShort("rank"));
				player.setGp(rs.getInt("gp"));
				player.setExp(rs.getInt("exp"));
				player.setPcCafe(rs.getShort("pc_cafe"));
				player.setOnline(rs.getBoolean("online"));
			}
			

			if(player != null)
			{
				statement = con.prepareStatement(PLAYER_SELECT_STATS.getQuery());
				statement.setLong(1, player.getId());
				rs = statement.executeQuery();
				if(rs.next())
				{
					PlayerStats stats = new PlayerStats();
					stats.setSeasonFights(rs.getInt("season_fights"));
					stats.setSeasonWins(rs.getInt("season_wins"));
					stats.setSeasonLosts(rs.getInt("season_losts"));
					stats.setSeasonKills(rs.getInt("season_kills"));
					stats.setSeasonDeaths(rs.getInt("season_deaths"));
					stats.setSeasonEscapes(rs.getInt("season_escapes"));
					stats.setFights(rs.getInt("fights"));
					stats.setWins(rs.getInt("wins"));
					stats.setLosts(rs.getInt("losts"));
					stats.setKills(rs.getInt("kills"));
					stats.setDeaths(rs.getInt("deaths"));
					stats.setEscapes(rs.getInt("escapes"));
					stats.setSeriaWins(rs.getInt("seria_wins"));
					stats.setKpd(rs.getInt("kpd"));
					stats.setSeasonSeriaWins(rs.getInt("season_seria_wins"));
					stats.setSeasonKpd(rs.getInt("season_kpd"));
					player.setStats(stats);
				}
				
				statement = con.prepareStatement(PLAYER_SELECT_EQIPMENT.getQuery());
				statement.setLong(1, player.getId());
				rs = statement.executeQuery();
				PlayerEqipment eqipment = new PlayerEqipment();
				while (rs.next()) {
					if (rs.getInt("item_id") == 0) {
						break;
					}
					PlayerItem item = new PlayerItem(
							rs.getLong("id"),
							ItemLocation.valueOf(rs.getString("loc")),
							ItemHolder.getInstance().getTemplate(rs.getInt("item_id")),
							rs.getInt("count"),
							false);
					eqipment.addItem(item, ItemLocation.EQUIPPED == ItemLocation.valueOf(rs.getString("loc")));
				}
				player.setEqipment(eqipment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCSR(con, statement, rs);
		}

		return player;
	}

	@Override
	public PlayerCreateTemplate readStartTemplate() {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		PlayerCreateTemplate template = null;
		try {
			con = DatabaseFactory.getInstance().newConnection();
			statement = con.prepareStatement(TEMPLATE_SELECT_START_EQIPMENT.getQuery());
			rs = statement.executeQuery();

			int startmoney = 0;
			int startgp = 0;
			List<PlayerItem> list = new ArrayList<PlayerItem>();
			while (rs.next()) {
				if (rs.getInt("item_id") == 0) {
					startmoney = rs.getInt("startmoney");
					startgp = rs.getInt("startgp");
					continue;
				}
				list.add(new PlayerItem(
						rs.getBoolean("equipped") ? ItemLocation.valueOf(ItemLocation.EQUIPPED.name()) : ItemLocation.valueOf(ItemLocation.INVENTORY.name()),
						ItemHolder.getInstance().getTemplate(rs.getInt("item_id")),
						rs.getInt("count"),
						false));
			}
			template = new PlayerCreateTemplate(startmoney, startgp);
			for (PlayerItem item : list)
				template.addPlayerItem(item);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCSR(con, statement, rs);
		}
		return template;
	}

	@Override
	public void update(Player entity) {
		entity.setOnline(false);

		Connection con = null;
		PreparedStatement statement = null;
		try {
			con = DatabaseFactory.getInstance().newConnection();
			statement = con.prepareStatement(PLAYER_UPDATE.getQuery());
			statement.setLong(1, entity.getAccountId());
			statement.setString(2, entity.getName());
			statement.setByte(3, entity.getColor());
			statement.setInt(4, entity.getRank());
			statement.setInt(5, entity.getGp());
			statement.setInt(6, entity.getExp());
			statement.setShort(7, entity.getPcCafe());
			statement.setBoolean(8, entity.getOnline());
			statement.setLong(9, entity.getId());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCS(con, statement);
		}
	}

	@Override
	public void delete(Long key) {
		throw new UnsupportedOperationException("You cannot delete an player from the system");
	}
}
