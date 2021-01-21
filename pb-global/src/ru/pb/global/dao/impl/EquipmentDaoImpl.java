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

import static ru.pb.global.dao.enums.QueryList.*;

public class EquipmentDaoImpl implements EquipmentDao {


	/**
	 * !Как только игрок научится тратить итемы ДО 0 надо немного дописать класс!
	 * Метод recordEquipped выполняет чтение данных из коллекции equiped предназначеной для хранения одетых вещей.
	 * На этапе чтения выполняется проверка флага по которому принимается решение об отправке запроса в БД
	 * - Не требует входных данных
	 * - Не возвращает выходные данные
	 */
	//TODO Походу тут пизда, но все же...
	public void recordEquipped(Player player) {
		LoggerFactory.getLogger(getClass()).info("recordEquipped");
		Connection con = null;
		PreparedStatement statement = null;
		try {
			for (PlayerItem playerItem : player.getEqipment().getEquippedMap().values()) {
				if (playerItem.getFlag() == ItemState.INSERT) {
					con = DatabaseFactory.getInstance().newConnection();
					statement = con.prepareStatement(PLAYER_EQUIPMENT_INSERT.getQuery());
					statement.setLong(1, player.getId());
					statement.setInt(2, playerItem.getItem().getId());
					statement.setInt(3, playerItem.getCount());
					statement.setString(4, playerItem.getItemLocation().toString());
					statement.setInt(5, playerItem.getConsumeLost());
					LoggerFactory.getLogger(getClass()).info("[0]: InsertItem" + playerItem.getItem().getId() + "; consumeLost: " + playerItem.getConsumeLost());
					statement.executeUpdate();
				}
				if (playerItem.getFlag() == ItemState.UPDATE) {
					con = DatabaseFactory.getInstance().newConnection();
					statement = con.prepareStatement(PLAYER_EQUIPMENT_UPDATE.getQuery());
					statement.setLong(1, playerItem.getItem().getId());
					statement.setInt(2, playerItem.getCount());
					statement.setString(3, playerItem.getItemLocation().toString());
					statement.setInt(4, playerItem.getConsumeLost());
					statement.setLong(5, player.getId());
					statement.setInt(6, playerItem.getItem().getId());

					if (!containsItem(player, playerItem)) {
						statement = con.prepareStatement(PLAYER_EQUIPMENT_INSERT.getQuery());
						statement.setLong(1, player.getId());
						statement.setInt(2, playerItem.getItem().getId());
						statement.setInt(3, playerItem.getCount());
						statement.setString(4, playerItem.getItemLocation().toString());
						statement.setLong(5, playerItem.getConsumeLost());
						LoggerFactory.getLogger(getClass()).info("[1]: InsertItem" + playerItem.getItem().getId() + "; consumeLost: " + playerItem.getConsumeLost());
					} else {
						statement.executeUpdate();
						LoggerFactory.getLogger(getClass()).info("[2]: UpdateItem" + playerItem.getItem().getId() + "; consumeLost: " + playerItem.getConsumeLost());
					}
				}
				if (playerItem.getFlag() == ItemState.DELETE) {

					con = DatabaseFactory.getInstance().newConnection();
					statement = con.prepareStatement(PLAYER_EQUIPMENT_DELETE.getQuery());
					statement.setLong(1, player.getId());
					statement.setLong(2, playerItem.getId());
					LoggerFactory.getLogger(getClass()).info("[3]: DeletetItem" + playerItem.getItem().getId() + "; consumeLost: " + playerItem.getConsumeLost());
					statement.execute();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCS(con, statement);
		}
	}

	/**
	 * Метод recordItems выполняет чтение данных из коллекции items предназначеной для хранения одетых вещей.
	 * На этапе чтения выполняется проверка флага по которому принимается решение об отправке запроса в БД
	 * - Не требует входных данных
	 * - Не возвращает выходные данные
	 * *Вызывается при выходе персонажа из игры (см. CM_DISCONNECT.java)
	 */

	public void recordItems(Player player) {
		LoggerFactory.getLogger(getClass()).info("recordItems");

		Connection con = null;
		PreparedStatement statement = null;
		try {
			for (ItemType t : ItemType.values()) {
				for (PlayerItem playerItem : player.getEqipment().getItemMap().get(t)) {
					if (!playerItem.isEquipped()) {
						if (playerItem.getFlag() == ItemState.INSERT) {
							con = DatabaseFactory.getInstance().newConnection();
							statement = con.prepareStatement(PLAYER_EQUIPMENT_INSERT.getQuery());
							statement.setLong(1, player.getId());
							statement.setInt(2, playerItem.getItem().getId());
							statement.setInt(3, playerItem.getCount());
							statement.setString(4, playerItem.getItemLocation().toString());
							statement.setInt(5, playerItem.getConsumeLost());
							statement.execute();
							LoggerFactory.getLogger(getClass()).info("[4]: InsertItem" + playerItem.getItem().getId() + "; consumeLost: " + playerItem.getConsumeLost());
						}
						if (playerItem.getFlag() == ItemState.UPDATE) {
							con = DatabaseFactory.getInstance().newConnection();
							statement = con.prepareStatement(PLAYER_EQUIPMENT_UPDATE.getQuery());
							statement.setLong(1, playerItem.getItem().getId());
							statement.setInt(2, playerItem.getCount());
							statement.setString(3, playerItem.getItemLocation().toString());
							statement.setInt(4, playerItem.getConsumeLost());
							statement.setLong(5, player.getId());
							statement.setInt(6, playerItem.getItem().getId());

							if (!containsItem(player, playerItem)) {
								statement = con.prepareStatement(PLAYER_EQUIPMENT_INSERT.getQuery());
								statement.setLong(1, player.getId());
								statement.setInt(2, playerItem.getItem().getId());
								statement.setInt(3, playerItem.getCount());
								statement.setString(4, playerItem.getItemLocation().toString());
								statement.setLong(5, playerItem.getConsumeLost());
								LoggerFactory.getLogger(getClass()).info("[5]: InsertItem" + playerItem.getItem().getId() + "; consumeLost: " + playerItem.getConsumeLost());
								statement.execute();
							} else {
								LoggerFactory.getLogger(getClass()).info("[6]: UpdateItem" + playerItem.getItem().getId() + "; consumeLost: " + playerItem.getConsumeLost());
								statement.executeUpdate();
							}
						}
						if (playerItem.getFlag() == ItemState.DELETE) {

							con = DatabaseFactory.getInstance().newConnection();
							statement = con.prepareStatement(PLAYER_EQUIPMENT_DELETE.getQuery());
							statement.setLong(1, player.getId());
							statement.setLong(2, playerItem.getId());
							LoggerFactory.getLogger(getClass()).info("[7]: DeleteItem" + playerItem.getItem().getId() + "; consumeLost: " + playerItem.getConsumeLost());
							statement.execute();
						}
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
		LoggerFactory.getLogger(getClass()).info("containsItem");
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
}

