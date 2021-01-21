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

package ru.pb.global.network.objects.GameAuth;

import ru.pb.global.models.GameServerInfo;
import ru.pb.global.network.objects.Connection;
import ru.pb.global.network.objects.ObjectNetwork;

import java.io.Serializable;
import java.util.List;

/**
 * Список серверов
 *
 * @author sjke
 */
public class GameServerList<T extends Connection> extends ObjectNetwork<T> implements Serializable {

	private static final long serialVersionUID = 4162588879843225125L;

	private List<GameServerInfo> list;

	public List<GameServerInfo> getList() {
		return list;
	}

	public void setList(List<GameServerInfo> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "GameServerList{" +
				"list=" + list +
				'}';
	}
}
