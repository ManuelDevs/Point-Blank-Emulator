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

package ru.pb.auth;

import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.pb.auth.controller.LoaderController;
import ru.pb.auth.loader.DatabaseLoader;
import ru.pb.auth.network.client.ClientServer;
import ru.pb.auth.network.client.packets.AuthPacketHandler;
import ru.pb.auth.network.gameserver.GameServer;
import ru.pb.auth.network.gameserver.objects.GameObjectHandler;
import ru.pb.auth.properties.AuthServerProperty;
import ru.pb.global.dao.DaoManager;
import ru.pb.global.dao.jdbc.DatabaseFactory;
import ru.pb.global.info.PrintInfo;
import ru.pb.global.properties.DataBaseProperty;

/**
 * Ð¡ÐµÑ€Ð²ÐµÑ€ Ð°Ð²Ñ‚Ð¾Ñ€Ð¸Ð·Ð°Ñ†Ð¸Ð¸
 * 
 * @author sjke
 */
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
		
		AuthServerProperty.getInstance();
		DataBaseProperty.getInstance();
		
		try {
			DatabaseFactory.getInstance();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		DaoManager.getInstance();
		DatabaseLoader.loadTables();
		LoaderController.loadControllers();
		AuthPacketHandler.getInstance();
		GameObjectHandler.getInstance();
		System.out.println();
		PrintInfo.getInstance().printLoadInfos();
		System.out.println();
		ExecutorService service = Executors.newFixedThreadPool(2);
		service.submit(new GameServer());
		service.submit(new ClientServer());
	}
}
