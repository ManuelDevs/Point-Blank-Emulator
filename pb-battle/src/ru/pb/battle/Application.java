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

package ru.pb.battle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.pb.battle.controllers.RoomController;
import ru.pb.battle.network.client.BattleClientServer;
import ru.pb.battle.network.game.GameServerClient;
import ru.pb.battle.network.game.packets.BattlePacketHandler;
import ru.pb.battle.properties.BattleServerProperty;
import ru.pb.global.info.PrintInfo;
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
		
		BattleServerProperty.getInstance();
		ThreadPoolManager.getInstance();
		RoomController.getInstance();
		BattlePacketHandler.getInstance();
		PrintInfo.getInstance().printLoadInfos();

		ExecutorService service = Executors.newFixedThreadPool(2);
		service.execute(new BattleClientServer());
		service.execute(new GameServerClient());
		service.shutdown();
	}
}
