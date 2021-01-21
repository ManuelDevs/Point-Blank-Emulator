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

package ru.pb.game.network.auth.objects.handlers;

import ru.pb.game.controller.GameServerController;
import ru.pb.game.network.auth.AuthConnection;
import ru.pb.global.network.objects.Handler;
import ru.pb.global.network.objects.GameAuth.GameServerList;

/**
 * Обработчик списка серверов
 *
 * @author sjke
 */
public class GameServerListHandler implements Handler<GameServerList, AuthConnection> {
	@Override
	public void call(GameServerList obj, AuthConnection connection) {
		GameServerController.getInstance().writeServerList(obj.getList(), connection);
	}
}
