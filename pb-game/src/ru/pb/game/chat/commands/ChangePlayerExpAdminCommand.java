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

package ru.pb.game.chat.commands;

import ru.pb.game.network.client.ClientConnection;

/**
 * Команда на изменение анонса
 *
 * @author sjke
 */
public class ChangePlayerExpAdminCommand implements BaseCommand {

	@Override
	public String call(ClientConnection connection, String message) {
		try {
			connection.getPlayer().setExp(connection.getPlayer().getExp() + Integer.valueOf(message));
			//connection.sendPacket(new SM_ACCOUNT_INFO(connection.getPlayer(), connection.getAccount().getMoney()));
			return "Add exp for player [" + connection.getPlayer().getName() + "]";
		} catch (Exception e) {
			return "Command syntax : /adm player exp [INTEGER]";
		}
	}

	@Override
	public String getPrefix() {
		return "player exp";
	}

}
