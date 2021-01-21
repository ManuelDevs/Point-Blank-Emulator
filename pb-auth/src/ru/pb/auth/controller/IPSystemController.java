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

package ru.pb.auth.controller;

import ru.pb.global.controller.BaseController;
import ru.pb.global.models.IPNetwork;
import ru.pb.global.service.IPNetworkDaoService;
import ru.pb.global.utils.NetworkUtil;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author: Felixx
 * Контроллер IP Системы.
 */
public class IPSystemController extends BaseController {

	private static CopyOnWriteArrayList<IPNetwork> blockedNetworks = new CopyOnWriteArrayList<IPNetwork>();  // TODO
	// заменить на потокобезопасный!

	private static class Singleton {
		private static final IPSystemController INSTANCE = new IPSystemController();
	}

	public static IPSystemController getInstance() {
		return Singleton.INSTANCE;
	}

	private IPSystemController() {
		blockedNetworks.addAll(IPNetworkDaoService.getInstance().getAll());
		log.info("Loaded " + blockedNetworks.size() + " blocked ip masks.");
	}

	public boolean isInBlockedNetwork(String ip) {
		for (IPNetwork n : blockedNetworks) {
			if (NetworkUtil.isInRange(ip, n)) {
				return true;
			}
		}
		return false;
	}
}
