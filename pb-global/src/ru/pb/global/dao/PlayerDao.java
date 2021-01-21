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
 * Copyright (C) 2013 PBDevâ„¢
 */

package ru.pb.global.dao;

import ru.pb.global.models.Player;
import ru.pb.global.models.PlayerCreateTemplate;

/**
 * Ð”Ð°Ð¾ Ð´Ð»Ñ� Ñ€Ð°Ð±Ð¾Ñ‚Ñ‹ Ñ� Ð¸Ð³Ñ€Ð¾ÐºÐ°Ð¼Ð¸
 *
 * @author sjke
 */
public interface PlayerDao {
	boolean playerExist(String name);

	PlayerCreateTemplate readStartTemplate();

	void update(Player entity);

	void create(Player entity);

	public Player read(Long accountID);

	void delete(Long key);
}
