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

import ru.pb.global.dao.GoodsDao;
import ru.pb.global.dao.jdbc.DatabaseFactory;
import ru.pb.global.dao.jdbc.DatabaseUtils;
import ru.pb.global.data.holder.ItemHolder;
import ru.pb.global.models.Good;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import static ru.pb.global.dao.enums.QueryList.SHOP_GOOD_LIST;

/**
 * Работа с магазином
 *
 * @author sjke
 */
public class GoodsDaoImpl implements GoodsDao {

	@Override
	public Map<Integer, Good> getAllGoods() {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Map<Integer, Good> map = new ConcurrentSkipListMap<Integer, Good>();
		try {
			con = DatabaseFactory.getInstance().newConnection();
			statement = con.prepareStatement(SHOP_GOOD_LIST.getQuery());
			rs = statement.executeQuery();
			while (rs.next()) {
				map.put(rs.getInt("good_id"),
						new Good(rs.getInt("good_id"),
								ItemHolder.getInstance().getTemplate(rs.getInt("item_id")),
								rs.getInt("quantity"),
								rs.getInt("priceCredits"),
								rs.getInt("pricePoints"),
								rs.getInt("stockType")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCSR(con, statement, rs);
		}
		return map.size() > 0 ? map : Collections.EMPTY_MAP;
	}
}
