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

package ru.pb.game.controller;

import ru.pb.game.properties.GameServerProperty;
import ru.pb.global.controller.BaseController;
import ru.pb.global.models.Channel;
import ru.pb.global.service.ChannelDaoService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Контроллер каналов
 *
 * @author sjke
 */
public class ChannelController extends BaseController {

	private static ConcurrentMap<Byte, Channel> channels = new ConcurrentSkipListMap<Byte, Channel>();

	private ChannelController() {
		List<Channel> list = ChannelDaoService.getInstance().getAllChannelByServer(GameServerProperty.getInstance().GAME_SERVER_ID);
		for (Channel channel : list) {
			channels.put((byte) channel.getId(), channel);
		}
		log.info("Loaded " + channels.size() + " channels");
	}

	public static ChannelController getInstance() {
		return Singleton.INSTANCE;
	}

	public Collection<Channel> getChannels() {
		return Collections.unmodifiableCollection(channels.values());
	}

	public Channel getChannel(byte id) {
		return channels.get(id);
	}

	private static class Singleton {
		private static final ChannelController INSTANCE = new ChannelController();
	}
}
