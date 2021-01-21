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

import ru.pb.global.controller.PlayerTemplateController;
import ru.pb.global.dao.DaoManager;
import ru.pb.global.dao.PlayerDao;
import ru.pb.global.models.Player;
import ru.pb.global.models.PlayerCreateTemplate;
import ru.pb.global.models.PlayerStats;

/**
 * Сервис для работы с игроками
 *
 * @author sjke
 */
public class PlayerDaoService {
	private static final PlayerDao playerDao = DaoManager.getInstance().getPlayerDao();

	private PlayerDaoService() {
	}

	public static PlayerDaoService getInstance() {
		return Singleton.INSTANCE;
	}

	public Player createBasePlayer(String playerName, Long id) {
		Player player = new Player();
		player.setName(playerName);
		player.setAccountId(id);
		player.setColor((byte) 0); // TODO template
		player.setRank((short) 0); // TODO template
		player.setGp(0); // TODO template
		player.setExp(0);
		player.setPcCafe((short) 0); // TODO template
		player.setOnline(true);
		player.setEqipment(PlayerTemplateController.getInstance().getTemplate().getEqipment());
		PlayerStats stats = new PlayerStats();
		stats.setSeasonFights(0);
		stats.setSeasonWins(0);
		stats.setSeasonLosts(0);
		stats.setSeasonKills(0);
		stats.setSeasonDeaths(0);
		stats.setSeasonEscapes(0);
		stats.setFights(0);
		stats.setWins(0);
		stats.setLosts(0);
		stats.setKills(0);
		stats.setDeaths(0);
		stats.setEscapes(0);
		stats.setSeriaWins(0);
		stats.setKpd(0);
		stats.setSeasonSeriaWins(0);
		stats.setSeasonKpd(0);
		player.setStats(stats);
		create(player);
		return player;
	}

	private static class Singleton {
		private static final PlayerDaoService INSTANCE = new PlayerDaoService();
	}

	public boolean playerExist(String name) {
		return playerDao.playerExist(name);
	}

	public void create(Player entity) {
		playerDao.create(entity);
	}

	public void update(Player entity) {
		playerDao.update(entity);
		EquipmentDaoService.getInstance().writeEquipped(entity);
		EquipmentDaoService.getInstance().writeBag(entity);
	}

	public Player read(Long accountID) {
		return playerDao.read(accountID);
	}

	public PlayerCreateTemplate readStartTemplate() {
		return playerDao.readStartTemplate();
	}
}
