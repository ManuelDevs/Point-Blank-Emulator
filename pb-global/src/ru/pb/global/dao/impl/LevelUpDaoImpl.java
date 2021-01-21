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

import ru.pb.global.dao.LevelUpDao;
import ru.pb.global.dao.jdbc.DatabaseFactory;
import ru.pb.global.dao.jdbc.DatabaseUtils;
import ru.pb.global.models.LevelUpInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import static ru.pb.global.dao.enums.QueryList.GET_LEVEL_LUP_INFOS;

/**
 * @author: Felixx
 * Date: 18.10.13
 * Time: 4:58
 */
public class LevelUpDaoImpl implements LevelUpDao {

	@Override
	public Map<Byte, LevelUpInfo> getAll() {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		TreeMap<Byte, LevelUpInfo> map = new TreeMap<Byte, LevelUpInfo>();
		try {
			con = DatabaseFactory.getInstance().newConnection();
			statement = con.prepareStatement(GET_LEVEL_LUP_INFOS.getQuery());
			rs = statement.executeQuery();

			while (rs.next()) {
				map.put(rs.getByte("rank"), new LevelUpInfo(rs.getByte("rank"), rs.getInt("needExp"), rs.getInt("allExp"), rs.getInt("rewardGP"), rs.getArray("rewardItems").getArray()));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCSR(con, statement, rs);
		}
		return map.size() > 0 ? map : Collections.EMPTY_MAP;
	}
}
