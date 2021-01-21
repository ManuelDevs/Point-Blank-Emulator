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

package ru.pb.game.network.client.packets.server;

import ru.pb.game.controller.ChannelController;
import ru.pb.game.controller.GameServerController;
import ru.pb.game.network.client.packets.ServerPacket;
import ru.pb.global.models.Channel;

import java.util.Collection;

/**
 * Неизместный пакет, отправляется вместе с пекетов авторизации
 *
 * @author sjke
 */
public class SM_CHANNEL_LIST extends ServerPacket {

	public SM_CHANNEL_LIST() {
		super(0xA0C);
	}

	@Override
	public void writeImpl() {
		Collection<Channel> channels = ChannelController.getInstance().getChannels();
		int channelMaxPlayer = GameServerController.getInstance().getCurrentGameServerInfo().getMaxPlayer() / channels.size();
		writeD(channels.size());
		writeD(channelMaxPlayer);
		for (Channel channel : channels) {
			writeD(channel.getPlayers().size());
		}
	}

}