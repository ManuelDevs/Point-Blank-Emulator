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

import ru.pb.global.dao.FriendDao;
import ru.pb.global.dao.jdbc.DatabaseFactory;
import ru.pb.global.dao.jdbc.DatabaseUtils;
import ru.pb.global.models.FriendInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.pb.global.dao.enums.QueryList.GET_ALL_FRIENDS;

/**
 * @author: Felixx
 * Date: 27.09.13
 * Time: 14:33
 */
public class FriendDaoImpl implements FriendDao {

	@Override
	public List<FriendInfo> getAllFriends(Long acc) {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<FriendInfo> list = new ArrayList<FriendInfo>();
		try {
			con = DatabaseFactory.getInstance().newConnection();
			statement = con.prepareStatement(GET_ALL_FRIENDS.getQuery());
			statement.setLong(1, acc);
			rs = statement.executeQuery();
			while (rs.next()) {
				FriendInfo fi = new FriendInfo();
				fi.setRank(rs.getInt("rank"));
				fi.setName(rs.getString("name"));
				list.add(fi);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCSR(con, statement, rs);
		}
		return list.size() > 0 ? list : Collections.EMPTY_LIST;
	}
}
