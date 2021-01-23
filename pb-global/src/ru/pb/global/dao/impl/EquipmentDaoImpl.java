package ru.pb.global.dao.impl;

import org.slf4j.LoggerFactory;

import ru.pb.global.dao.EquipmentDao;
import ru.pb.global.dao.jdbc.DatabaseFactory;
import ru.pb.global.dao.jdbc.DatabaseUtils;
import ru.pb.global.enums.ItemState;
import ru.pb.global.enums.item.ItemType;
import ru.pb.global.models.Player;
import ru.pb.global.models.PlayerItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static ru.pb.global.dao.enums.QueryList.*;

public class EquipmentDaoImpl implements EquipmentDao {
	
	public void recordEquipped(Player player) {
		Connection con = null;
		PreparedStatement statement = null;
		try {
			for (PlayerItem playerItem : player.getEqipment().getEquippedMap().values()) {
				if (playerItem.getFlag() == ItemState.UPDATE) {
					con = DatabaseFactory.getInstance().newConnection();
					statement = con.prepareStatement(PLAYER_EQUIPMENT_UPDATE.getQuery());
					statement.setLong(1, playerItem.getItem().getId());
					statement.setInt(2, playerItem.getCount());
					statement.setString(3, playerItem.getItemLocation().toString());
					statement.setInt(4, playerItem.getConsumeLost());
					statement.setLong(5, player.getId());
					statement.setInt(6, playerItem.getItem().getId());
					statement.executeUpdate();
				}
				if (playerItem.getFlag() == ItemState.DELETE) {

					con = DatabaseFactory.getInstance().newConnection();
					statement = con.prepareStatement(PLAYER_EQUIPMENT_DELETE.getQuery());
					statement.setLong(1, player.getId());
					statement.setLong(2, playerItem.getId());
					statement.execute();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCS(con, statement);
		}
	}

	public void recordItems(Player player) {
		Connection con = null;
		PreparedStatement statement = null;
		try {
			for (ItemType t : ItemType.values()) {
				for (PlayerItem playerItem : player.getEqipment().getItemMap().get(t)) {
					if (playerItem.getFlag() == ItemState.UPDATE) {
						con = DatabaseFactory.getInstance().newConnection();
						statement = con.prepareStatement(PLAYER_EQUIPMENT_UPDATE.getQuery());
						statement.setLong(1, playerItem.getItem().getId());
						statement.setInt(2, playerItem.getCount());
						statement.setString(3, playerItem.getItemLocation().toString());
						statement.setInt(4, playerItem.getConsumeLost());
						statement.setLong(5, player.getId());
						statement.setInt(6, playerItem.getItem().getId());
						statement.executeUpdate();
					}
					if (playerItem.getFlag() == ItemState.DELETE) {

						con = DatabaseFactory.getInstance().newConnection();
						statement = con.prepareStatement(PLAYER_EQUIPMENT_DELETE.getQuery());
						statement.setLong(1, player.getId());
						statement.setLong(2, playerItem.getId());
						statement.execute();
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCS(con, statement);
		}
	}

	@Override
	public boolean containsItem(Player player, PlayerItem item) {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			con = DatabaseFactory.getInstance().newConnection();
			statement = con.prepareStatement(PLAYER_EQUIPMENT_CHECK.getQuery());
			statement.setLong(1, player.getId());
			statement.setInt(2, item.getItem().getId());
			rs = statement.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCSR(con, statement, rs);
		}
		return false;
	}

	@Override
	public void registerItem(Player player, PlayerItem playerItem) {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			con = DatabaseFactory.getInstance().newConnection();
			statement = con.prepareStatement(PLAYER_EQUIPMENT_INSERT.getQuery(), Statement.RETURN_GENERATED_KEYS);
			statement.setLong(1, player.getId());
			statement.setInt(2, playerItem.getItem().getId());
			statement.setInt(3, playerItem.getCount());
			statement.setString(4, playerItem.getItemLocation().toString());
			statement.setLong(5, playerItem.getConsumeLost());
			statement.executeUpdate();
			
			rs = statement.getGeneratedKeys();
			rs.next();

			playerItem.setId((Long) rs.getObject(1));
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCSR(con, statement, rs);
		}
	}
}

