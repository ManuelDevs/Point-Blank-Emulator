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

package ru.pb.game.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pb.game.chat.commands.*;
import ru.pb.game.network.client.ClientConnection;
import ru.pb.game.network.client.packets.server.SM_CHAT_LOBBY_MESSAGE;
import ru.pb.global.models.AdminProfile;
import ru.pb.global.service.AdminProfileDaoService;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Обработчик админ комманд
 *
 * @author sjke
 */
public class AdminCommandHandler {
	ArrayList<AdminProfile> adminList = new ArrayList<AdminProfile>();
	private static final CopyOnWriteArrayList<BaseCommand> commands = new CopyOnWriteArrayList<BaseCommand>();
	private static final AdminCommandHandler INSTANCE = new AdminCommandHandler();
	protected final Logger log = LoggerFactory.getLogger(getClass());

	private AdminCommandHandler() {
		commands.add(new ChangeAnounceAdminCommand());
		commands.add(new ChangePlayerColorAdminCommand());
		commands.add(new ChangePlayerRankAdminCommand());
		commands.add(new ChangePlayerExpAdminCommand());
		commands.add(new ChangePlayerGpAdminCommand());
		commands.add(new AddItemAdminCommand());
		commands.add(new ChangePlayerNameAdminCommand());
		commands.add(new KickPlayerAdminCommand());
		commands.add(new DelRoomAdminCommand());
		if (AdminProfileDaoService.getInstance().getAll() != null) {
			adminList.addAll(AdminProfileDaoService.getInstance().getAll());
		}
		log.info("Load " + commands.size() + " admin commands.");
	}

	public static AdminCommandHandler getInstance() {
		return INSTANCE;
	}

	public void handler(ClientConnection connection, String message) {

		String messageResponce = "This command not found. [" + message + "]";
		for (BaseCommand command : commands) {
			if (message.startsWith(command.getPrefix())) {
				try {
					messageResponce = command.call(connection, message.substring(command.getPrefix().length() + 1));
					break;
				} catch (Exception ex) {
					connection.sendPacket(new SM_CHAT_LOBBY_MESSAGE(connection.getPlayer(), "Wrong command usage! See admin guide!"));
				}
			}
		}
		connection.sendPacket(new SM_CHAT_LOBBY_MESSAGE(connection.getPlayer(), messageResponce));
	}

}
