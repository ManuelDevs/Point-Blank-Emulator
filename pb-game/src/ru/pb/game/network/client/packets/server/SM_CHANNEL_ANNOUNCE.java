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

import ru.pb.game.network.client.packets.ServerPacket;
import ru.pb.global.models.Channel;

/**
 * Пакет со списком каналов
 */
public class SM_CHANNEL_ANNOUNCE extends ServerPacket {

	private Channel channel;

	public SM_CHANNEL_ANNOUNCE(Channel channel) {
		super(0xA0E);
		this.channel = channel;
	}

	@Override
	public void writeImpl() {
		writeBool(channel.getAnnounce() != null);
		writeH(channel.getAnnounce().length());
		writeS(channel.getAnnounce(), channel.getAnnounce().length());
	}
}
