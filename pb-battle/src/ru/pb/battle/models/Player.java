package ru.pb.battle.models;

import java.net.InetAddress;

public class Player
{
	public InetAddress playerIP;
	public int playerPort;
	public boolean isActive = false;
	public boolean isConnected = false;

	public Player(InetAddress playerIP, int playerPort, boolean isConnected)
	{
		this.isConnected = isConnected;
		this.playerIP = playerIP;
		this.playerPort = playerPort;
	}

	public void setActive(boolean active)
	{
		this.isActive = active;
	}

	public boolean isActive()
	{
		return isActive;
	}
}
