package ru.pb.battle.models;

import java.net.InetAddress;
import java.util.concurrent.ConcurrentHashMap;

public class Room {
	public long id;
	public InetAddress inetAddress; //host
	public int port;
	public boolean isActive;
	public boolean isConnected;

	private ConcurrentHashMap<Long, Player> players = new ConcurrentHashMap<Long, Player>();

	public ConcurrentHashMap<Long, Player> getPlayers()
	{
		return  players;
	}

	public void setPlayers(ConcurrentHashMap<Long, Player> players)
	{
		this.players = players;
	}

	public void RemovePlayer(long rid, InetAddress ip)
	{
		for(int i = 0; i < this.getPlayers().size(); i++)
		{
			if(this.getPlayers().get(i) != null)
			{
				if(this.getPlayers().get(i).playerIP.equals(ip))
				{
					this.getPlayers().remove(this.getPlayers().get(i));
				}
			}
		}
	}
}
