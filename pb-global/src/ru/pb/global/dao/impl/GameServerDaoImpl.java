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

import ru.pb.global.dao.GameServerDao;
import ru.pb.global.dao.jdbc.DatabaseFactory;
import ru.pb.global.dao.jdbc.DatabaseUtils;
import ru.pb.global.enums.ChannelType;
import ru.pb.global.models.GameServerInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.pb.global.dao.enums.QueryList.GET_ALL_GAMESERVERS;

/**
 * Работа с серверами
 *
 * @author sjke
 */
public class GameServerDaoImpl implements GameServerDao {

	@Override
	public List<GameServerInfo> getAll() {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<GameServerInfo> list = new ArrayList<GameServerInfo>();
		try {
			con = DatabaseFactory.getInstance().newConnection();
			statement = con.prepareStatement(GET_ALL_GAMESERVERS.getQuery());
			rs = statement.executeQuery();

			while (rs.next()) {
				GameServerInfo server = new GameServerInfo();
				server.setId(rs.getInt("id"));
				server.setPassword(rs.getString("password"));
				server.setType(ChannelType.valueOf(rs.getString("type")));
				server.setMaxPlayer(rs.getInt("max_players"));
				server.getOnline().set(0);
				list.add(server);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCSR(con, statement, rs);
		}
		return list.size() > 0 ? list : Collections.EMPTY_LIST;
	}
}
