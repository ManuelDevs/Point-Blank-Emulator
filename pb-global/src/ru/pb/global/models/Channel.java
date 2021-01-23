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
 * Copyright (C) 2013 PBDev™
 */

package ru.pb.global.models;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.pb.global.enums.ChannelType;

public class Channel {
	protected final Logger log = LoggerFactory.getLogger(Channel.class);
	private int id;
	public static final int MAX_ROOMS_COUNT = 50;
	public static final int MAX_PLAYERS_COUNT = 150;
	private ChannelType type;
	private String announce;
	private ConcurrentHashMap<Long, Player> players = new ConcurrentHashMap<Long, Player>();
	private ConcurrentHashMap<Integer, Room> rooms = new ConcurrentHashMap<Integer, Room>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ChannelType getType() {
		return type;
	}

	public void setType(ChannelType type) {
		this.type = type;
	}

	public String getAnnounce() {
		return announce;
	}

	public void setAnnounce(String announce) {
		this.announce = announce;
	}

	public ConcurrentHashMap<Long, Player> getPlayers() {
		return players;
	}

	public void addPlayer(Player player) {
		if(players.size() < MAX_PLAYERS_COUNT) {
			if( !players.containsKey(player.getId())) {
				players.put(player.getId(), player);
			}
		}
	}

	public void removePlayer(Player player) {
		players.remove(player.getId());
	}

	public ConcurrentHashMap<Integer, Room> getRooms() {
		return rooms;
	}

	public void removeRoom(Room room) {
		if(room.getPlayers().isEmpty()) {
			rooms.remove(room.getId());
		}
	}

	public Room getRoom(int id) {
		return rooms.get(id);
	}

	public void addRoom(Room room) {
		if(rooms.size() < MAX_ROOMS_COUNT) {
			if( !rooms.containsValue(room)) {
				rooms.put(room.getId(), room);
			}
		}
	}
}
