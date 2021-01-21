/*
 * Java Server Emulator Project Blackout / PointBlank
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * Authors: DarkSkeleton, sjke, Felixx
 * Copyright (C) 2013 PBDevâ„¢
 */

package ru.pb.game;

import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.pb.game.controller.LoaderController;
import ru.pb.game.handler.AdminCommandHandler;
import ru.pb.game.loader.DatabaseLoader;
import ru.pb.game.network.auth.AuthClient;
import ru.pb.game.network.auth.objects.AuthObjectHandler;
import ru.pb.game.network.battle.BattleServer;
import ru.pb.game.network.battle.packets.BattlePacketHandler;
import ru.pb.game.network.client.ClientServer;
import ru.pb.game.network.client.packets.GamePacketHandler;
import ru.pb.game.properties.GameServerProperty;
import ru.pb.global.dao.DaoManager;
import ru.pb.global.dao.jdbc.DatabaseFactory;
import ru.pb.global.info.PrintInfo;
import ru.pb.global.properties.DataBaseProperty;
import ru.pb.global.utils.concurrent.ThreadPoolManager;

public class Application {

	public static void main(String[] args) {
		System.out.println(
				" ██████╗ ██████╗ ██████╗ ███████╗██╗   ██╗  ███████╗██╗   ██╗\r\n" + 
				" ██╔══██╗██╔══██╗██╔══██╗██╔════╝██║   ██║  ██╔════╝██║   ██║\r\n" + 
				" ██████╔╝██████╔╝██║  ██║█████╗  ██║   ██║  █████╗  ██║   ██║\r\n" + 
				" ██╔═══╝ ██╔══██╗██║  ██║██╔══╝  ╚██╗ ██╔╝  ██╔══╝  ██║   ██║\r\n" + 
				" ██║     ██████╔╝██████╔╝███████╗ ╚████╔╝██╗███████╗╚██████╔╝\r\n" + 
				" ╚═╝     ╚═════╝ ╚═════╝ ╚══════╝  ╚═══╝ ╚═╝╚══════╝ ╚═════╝ \r\n" + 
				"     THIS PROJECT IS FOR STUDY PURPOSES - DON'T USE IT\r\n");
		
		GameServerProperty.getInstance();
		DataBaseProperty.getInstance();

		try {
			DatabaseFactory.getInstance();
		} catch(SQLException e) {
			e.printStackTrace();
		}

		DaoManager.getInstance();
		DatabaseLoader.loadTables();
		LoaderController.loadControllers();
		ThreadPoolManager.getInstance();
		GamePacketHandler.getInstance();
		BattlePacketHandler.getInstance();
		AuthObjectHandler.getInstance();
		AdminCommandHandler.getInstance();

		PrintInfo.getInstance().printLoadInfos();

		ExecutorService service = Executors.newFixedThreadPool(3);
		service.execute(new ClientServer());
		service.execute(new BattleServer());
		service.execute(new AuthClient());
		service.shutdown();
	}
}
