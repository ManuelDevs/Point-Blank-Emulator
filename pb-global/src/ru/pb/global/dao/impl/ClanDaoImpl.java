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

import ru.pb.global.dao.ClanDao;
import ru.pb.global.dao.jdbc.DatabaseFactory;
import ru.pb.global.dao.jdbc.DatabaseUtils;
import ru.pb.global.models.Clan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.pb.global.dao.enums.QueryList.CLAN_CREATE;
import static ru.pb.global.dao.enums.QueryList.CLAN_LIST;

public class ClanDaoImpl implements ClanDao {

	@Override
	public List<Clan> getAllClans() {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<Clan> list = new ArrayList<Clan>();
		try {
			con = DatabaseFactory.getInstance().newConnection();
			statement = con.prepareStatement(CLAN_LIST.getQuery());
			rs = statement.executeQuery();

			while (rs.next()) {
				Clan clan = new Clan();
				clan.setId(rs.getLong("id"));
				clan.setName(rs.getString("name"));
				clan.setColor(rs.getShort("color"));
				clan.setRank(rs.getShort("rank"));
				clan.setLogo1(rs.getShort("logo1"));
				clan.setLogo2(rs.getShort("logo2"));
				clan.setLogo3(rs.getShort("logo3"));
				clan.setLogo4(rs.getShort("logo4"));
				list.add(clan);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCSR(con, statement, rs);
		}
		return list.size() > 0 ? list : Collections.EMPTY_LIST;
	}

	@Override
	public void CreateClan(Clan clan) {
		Connection con = null;
		PreparedStatement statement = null;
		try {
			con = DatabaseFactory.getInstance().newConnection();
			statement = con.prepareStatement(CLAN_CREATE.getQuery());

			statement.setString(1, clan.getName());
			statement.setShort(2, clan.getRank());
			statement.setLong(3, clan.getOwnerId());
			statement.setShort(4, clan.getColor());
			statement.setShort(5, clan.getLogo1());
			statement.setShort(6, clan.getLogo2());
			statement.setShort(7, clan.getLogo3());
			statement.setShort(8, clan.getLogo4());

			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCS(con, statement);
		}
	}
}
