package ru.pb.global.dao.impl;

import ru.pb.global.dao.ItemsDao;
import ru.pb.global.dao.jdbc.DatabaseFactory;
import ru.pb.global.dao.jdbc.DatabaseUtils;
import ru.pb.global.enums.item.ItemConsumeType;
import ru.pb.global.enums.item.ItemRepair;
import ru.pb.global.enums.item.ItemSlotType;
import ru.pb.global.enums.item.ItemType;
import ru.pb.global.models.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import static ru.pb.global.dao.enums.QueryList.GET_ALL_ITEMS;

/**
 * @author Felixx
 */
public class ItemsDaoImpl implements ItemsDao {


	@Override
	public Map<Integer, Item> getAllItems() {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Map<Integer, Item> map = new ConcurrentSkipListMap<Integer, Item>();
		try {
			con = DatabaseFactory.getInstance().newConnection();
			statement = con.prepareStatement(GET_ALL_ITEMS.getQuery());
			rs = statement.executeQuery();

			while (rs.next()) {
				map.put(rs.getInt("id"), new Item(rs.getInt("id"),
						ItemType.valueOf(rs.getString("type")),
						ItemSlotType.valueOf(rs.getString("slot_type")),
						ItemConsumeType.valueOf(rs.getString("consume_type")),
						rs.getInt("consume_value"),
						new ItemRepair(rs.getInt("repair_credits"),
								rs.getInt("repair_points"),
								rs.getInt("repair_quantity"))
				));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCSR(con, statement, rs);
		}
		return map.size() > 0 ? map : Collections.EMPTY_MAP;
	}
}
