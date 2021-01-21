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

package ru.pb.global.service;

import ru.pb.global.dao.ChannelDao;
import ru.pb.global.dao.DaoManager;
import ru.pb.global.models.Channel;

import java.util.List;

/**
 * Сервис "Каналы сервера" для работы с дао
 *
 * @author sjke
 */
public class ChannelDaoService {

	private static final ChannelDao channelDao = DaoManager.getInstance().getChannelDao();

	private ChannelDaoService() {
	}

	public static ChannelDaoService getInstance() {
		return Singleton.INSTANCE;
	}

	public List<Channel> getAllChannelByServer(int serverId) {
		return channelDao.getAllChannelByServer(serverId);
	}

	private static class Singleton {
		private static final ChannelDaoService INSTANCE = new ChannelDaoService();
	}
}
