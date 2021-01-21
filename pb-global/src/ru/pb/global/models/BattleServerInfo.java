package ru.pb.global.models;


import ru.pb.global.network.packets.Connection;

import java.beans.Transient;

/**
 * Модель боевого сервера
 *
 * @author Felixx
 */
public class BattleServerInfo {

	private int id;
	private String ip;
	private int port;
	private String password;

	private transient Connection connection;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}


	@Transient
	public Connection getConnection() {
		return connection;
	}

	@Transient
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "BattleServerInfo{" +
				"id=" + id +
				", port=" + port +
				//", online=" + online +
				", ip='" + ip + '\'' +
				//", available=" + available +
				", connection=" + connection +
				"}";
	}
}