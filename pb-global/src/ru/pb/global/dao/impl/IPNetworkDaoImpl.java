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

import ru.pb.global.dao.IPNetworkDao;
import ru.pb.global.dao.jdbc.DatabaseFactory;
import ru.pb.global.dao.jdbc.DatabaseUtils;
import ru.pb.global.models.IPMask;
import ru.pb.global.models.IPRange;
import ru.pb.global.utils.NetworkUtil;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.pb.global.dao.enums.QueryList.GET_BLOCKED_IP;

/**
 * IPNetworkDao
 *
 * @author sjke
 */
public class IPNetworkDaoImpl implements IPNetworkDao {

	@Override
	public List<IPMask> loadIpMaskAll() {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<IPMask> list = new ArrayList<IPMask>();
		try {
			con = DatabaseFactory.getInstance().newConnection();
			statement = con.prepareStatement(GET_BLOCKED_IP.getQuery());
			statement.setString(1, new String("mask"));
			rs = statement.executeQuery();

			while (rs.next()) {
				IPMask mask = new IPMask();
				mask.setId(rs.getLong("id"));
				mask.setStart(rs.getString("startpoint"));
				mask.setEnd(rs.getString("endpoint"));
				String imask = mask.getStart() + "/" + mask.getEnd();
				int pos = imask.indexOf('/');
				String addr;
				if (pos == -1) {
					addr = imask;
					mask.setMaskCtr((byte) 32);
				} else {
					addr = imask.substring(0, pos);
					mask.setMaskCtr(Byte.parseByte(imask.substring(pos + 1)));
				}
				try {
					mask.setI4addr((Inet4Address) InetAddress.getByName(addr));
				} catch (UnknownHostException e) {
					break;
				}
				mask.setAddrInt(NetworkUtil.addrToInt(mask.getI4addr()));
				mask.setMaskInt(~((1 << (32 - mask.getMaskCtr())) - 1));
				list.add(mask);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCSR(con, statement, rs);
		}
		return list.size() > 0 ? list : Collections.EMPTY_LIST;
	}

	@Override
	public List<IPRange> getIPRangeAll() {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<IPRange> list = new ArrayList<IPRange>();
		try {
			con = DatabaseFactory.getInstance().newConnection();
			statement = con.prepareStatement(GET_BLOCKED_IP.getQuery());
			statement.setString(1, new String("range"));
			rs = statement.executeQuery();

			while (rs.next()) {
				IPRange range = new IPRange();
				range.setId(rs.getLong("id"));
				range.setStart(rs.getString("startpoint"));
				range.setEnd(rs.getString("endpoint"));
				if (range.getStart() == null || range.getEnd() == null) {
					break;
				} else {
					try {
						range.setRawStartIp(NetworkUtil.ipToLong((Inet4Address) InetAddress.getByName(range.getStart())));
						range.setRawEndIp(NetworkUtil.ipToLong((Inet4Address) InetAddress.getByName(range.getEnd())));
					} catch (UnknownHostException e) {
						break;
					}
				}
				if (range.getRawStartIp() > range.getRawEndIp()) {
					break;
				}
				list.add(range);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCSR(con, statement, rs);
		}
		return list.size() > 0 ? list : Collections.EMPTY_LIST;
	}
}
