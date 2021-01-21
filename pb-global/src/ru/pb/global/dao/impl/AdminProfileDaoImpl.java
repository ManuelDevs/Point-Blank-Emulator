package ru.pb.global.dao.impl;

import ru.pb.global.dao.AdminProfileDao;
import ru.pb.global.dao.jdbc.DatabaseFactory;
import ru.pb.global.dao.jdbc.DatabaseUtils;
import ru.pb.global.models.AdminProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.pb.global.dao.enums.QueryList.GET_ALL_ADMINS;

/**
 * Created with IntelliJ IDEA.
 * User: Felixx
 * Date: 21.01.14
 * Time: 19:07
 */
public class AdminProfileDaoImpl implements AdminProfileDao {
	@Override
	public List<AdminProfile> getAll() {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		ArrayList<AdminProfile> list = new ArrayList<AdminProfile>();
		try {
			con = DatabaseFactory.getInstance().newConnection();
			statement = con.prepareStatement(GET_ALL_ADMINS.getQuery());
			rs = statement.executeQuery();
			if (rs.next()) {
				AdminProfile profile = new AdminProfile();
				profile.setAccId(rs.getLong("accountID"));
				profile.setLevel(rs.getInt("level"));
				list.add(profile);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCSR(con, statement, rs);
		}
		return list.size() > 0 ? list : Collections.EMPTY_LIST;
	}
}
