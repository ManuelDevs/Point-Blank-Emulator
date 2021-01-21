package ru.pb.battle.controllers;

import ru.pb.battle.models.Player;
import ru.pb.battle.models.Room;
import ru.pb.global.controller.BaseController;

import java.net.InetAddress;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RoomController extends BaseController {

	private Map<Long, Room> rooms = new HashMap<Long, Room>();

	public static RoomController getInstance() {
		return Singleton.INSTANCE;
	}

	private static class Singleton {
		private static final RoomController INSTANCE = new RoomController();
	}

	public void addRoom(long id, Room room) {
		long mask = 0x000000FF;
		log.debug("Create Battle Room battleId: " + id  + " Channel: " + (mask & id >>> 8) + " Room: " + (mask & id >>> 16) + "");
		rooms.put(id, room);
	}

	public Room getRoom(long id) {
		//log.info("GETTING ROOM: " + id);
		return rooms.get(id);
	}

	public Player getPlayer(InetAddress ip)
	{
		for (Room room : rooms.values())
		{
			for(Player player : room.getPlayers().values())
			{
				if(player.playerIP.equals(ip))
					return player;
			}
		}
		return null;
	}

	public Room getRoom(InetAddress ip)
	{
		for (Room room : rooms.values())
		{
			for(Player player : room.getPlayers().values())
			{
				if(player.playerIP.equals(ip))
					return room;
			}
			if(room.inetAddress.equals(ip))
			{
				return room;
			}
		}
		return null;
	}

	public ConcurrentHashMap<Long, Player> getPlayers(InetAddress ip)
	{
		for (Room room : rooms.values())
		{
			for(Player player : room.getPlayers().values())
			{
				if(player.playerIP.equals(ip))
					return room.getPlayers();
			}
		}
		return null;
	}

	public void removeRoom(long id) {
		rooms.remove(id);
		log.info("REMOVING ROOM: " + id);
	}
}
