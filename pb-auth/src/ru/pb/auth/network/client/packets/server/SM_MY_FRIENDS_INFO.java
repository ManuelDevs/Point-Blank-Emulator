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

package ru.pb.auth.network.client.packets.server;

import ru.pb.auth.network.client.packets.ServerPacket;
import ru.pb.global.models.FriendInfo;

import java.util.List;

/**
 * Ответ списка друзей
 *
 * @author sjke, Felixx
 */
public class SM_MY_FRIENDS_INFO extends ServerPacket {

	private List<FriendInfo> friends;

	/**
	 * Пакет показывает друзей плеера.
	 *
	 * @param friends
	 */
	public SM_MY_FRIENDS_INFO(List<FriendInfo> friends) {
		super(0x112);
		this.friends = friends;
	}

	@Override
	public void writeImpl() {
		writeC(friends.size()); // count friends
		for (FriendInfo fi : friends) {
			writeC(fi.getName().length());
			writeS(fi.getName()); // Имя друга
			writeC(47);
			writeC(66);
			writeC(9);
			writeC(0);
			writeC(0);
			writeC(0);
			writeC(0);
			writeC(0);
			writeC(0);
			writeC(0);
			writeC(0);
			writeC(48);
			writeC(fi.getRank()); // Ранк (Звание)
			writeC(84);
			writeC(195);
			writeC(164);
		}
	}
}