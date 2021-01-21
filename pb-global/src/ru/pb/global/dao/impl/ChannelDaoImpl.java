package ru.pb.global.dao.impl;

import ru.pb.global.dao.ChannelDao;
import ru.pb.global.dao.jdbc.DatabaseFactory;
import ru.pb.global.dao.jdbc.DatabaseUtils;
import ru.pb.global.enums.ChannelType;
import ru.pb.global.models.Channel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.pb.global.dao.enums.QueryList.GET_CHANNELS_BY_GAMESERVER_ID;

/**
 * Created with IntelliJ IDEA.
 * User: Пользователь
 * Date: 20.01.14
 * Time: 0:52
 * To change this template use File | Settings | File Templates.
 */
public class ChannelDaoImpl implements ChannelDao {
	@Override
	public List<Channel> getAllChannelByServer(int serverId) {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<Channel> list = new ArrayList<Channel>();
		try {
			con = DatabaseFactory.getInstance().newConnection();
			statement = con.prepareStatement(GET_CHANNELS_BY_GAMESERVER_ID.getQuery());
			statement.setInt(1, serverId);
			rs = statement.executeQuery();
			while (rs.next()) {
				Channel channel = new Channel();
				channel.setId(rs.getInt("id"));
				channel.setType(ChannelType.valueOf(rs.getString("type")));
				channel.setAnnounce(rs.getString("announce"));
				list.add(channel);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCSR(con, statement, rs);
		}
		return list.size() > 0 ? list : Collections.EMPTY_LIST;
	}
}
