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

package ru.pb.global.controller;

import ru.pb.global.models.PlayerCreateTemplate;
import ru.pb.global.service.PlayerDaoService;

public class PlayerTemplateController extends BaseController {

	private static final PlayerCreateTemplate template = PlayerDaoService.getInstance().readStartTemplate();

	private PlayerTemplateController() {
		if (template == null) {
			log.error("Error load current player template");
			System.exit(-1);
		}
		log.info("Loaded start player template. [Gp: " + template.getStartGp() + "; Money: " + template.getStartMoney());
		log.info("Loaded " + template.getItems().size() + " starting items.");
	}

	public PlayerCreateTemplate getTemplate() {
		return template;
	}

	private static class Singleton {
		private static final PlayerTemplateController INSTANCE = new PlayerTemplateController();
	}

	public static PlayerTemplateController getInstance() {
		return Singleton.INSTANCE;
	}

}
