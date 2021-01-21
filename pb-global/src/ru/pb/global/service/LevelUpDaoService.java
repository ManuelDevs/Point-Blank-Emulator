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

import ru.pb.global.dao.DaoManager;
import ru.pb.global.dao.LevelUpDao;
import ru.pb.global.models.LevelUpInfo;

import java.util.Map;

/**
 * @author: Felixx
 * Date: 18.10.13
 * Time: 4:27
 */
public class LevelUpDaoService {

	private static final LevelUpDao levelUpDao = DaoManager.getInstance().getLevelupDao();
	private Map<Byte, LevelUpInfo> list;

	private LevelUpDaoService() {
	}

	public static LevelUpDaoService getInstance() {
		return Singleton.INSTANCE;
	}

	public Map<Byte, LevelUpInfo> getAll() {
		if (list == null) {
			list = levelUpDao.getAll();
		}
		return list;
	}

	public LevelUpInfo getLevelInfoForRank(byte rank) {
		return getAll().get(rank);
	}

	private static class Singleton {
		private static final LevelUpDaoService INSTANCE = new LevelUpDaoService();
	}
}
