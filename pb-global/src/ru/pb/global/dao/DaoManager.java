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

package ru.pb.global.dao;

import ru.pb.global.dao.impl.*;

/**
 * Менеджер дао
 *
 * @author sjke
 */
public class DaoManager {
	private static final DaoManager INSTANCE = new DaoManager();
	private static AccountDao accountDao;
	private static GameServerDao gameServerDao;
	private static IPNetworkDao iPNetworkDao;
	private static PlayerDao playerDao;
	private static ChannelDao channelDao;
	private static GoodsDao goodsDao;
	private static FriendDao friendDao;
	private static LevelUpDao levelupDao;
	private static ClanDao clanDao;
	private static EquipmentDao equipmentDao;
	private static ItemsDao itemsDao;
	private static AdminProfileDao adminDao;
	private static MapsDao mapsDao;
	
	private DaoManager() {
		gameServerDao = new GameServerDaoImpl();
		accountDao = new AccountDaoImpl();
		iPNetworkDao = new IPNetworkDaoImpl();
		playerDao = new PlayerDaoImpl();
		channelDao = new ChannelDaoImpl();
		goodsDao = new GoodsDaoImpl();
		friendDao = new FriendDaoImpl();
		levelupDao = new LevelUpDaoImpl();
		clanDao = new ClanDaoImpl();
		equipmentDao = new EquipmentDaoImpl();
		itemsDao = new ItemsDaoImpl();
		adminDao = new AdminProfileDaoImpl();
		mapsDao = new MapsDaoImpl();
	}
	
	public static DaoManager getInstance() {
		return INSTANCE;
	}

	public MapsDao getMapsDao() {
		return mapsDao;
	}
	
	public FriendDao getFriendDao() {
		return friendDao;
	}

	public GoodsDao getShopDao() {
		return goodsDao;
	}

	public AccountDao getAccountDao() {
		return accountDao;
	}

	public GameServerDao getGameServerDao() {
		return gameServerDao;
	}

	public IPNetworkDao getiPNetworkDao() {
		return iPNetworkDao;
	}

	public PlayerDao getPlayerDao() {
		return playerDao;
	}

	public LevelUpDao getLevelupDao() {
		return levelupDao;
	}

	public static ChannelDao getChannelDao() {
		return channelDao;
	}

	public ClanDao getClanDao() {
		return clanDao;
	}

	public EquipmentDao getEquipmentDao() {
		return equipmentDao;
	}

	public ItemsDao getItemsDao() {
		return itemsDao;
	}

	public AdminProfileDao getAdminProfileDao() {
		return adminDao;
	}
}
