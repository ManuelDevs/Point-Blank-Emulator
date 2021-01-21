package ru.pb.global.network.objects.UdpGame;

import ru.pb.global.enums.BattleServerAuthResponse;
import ru.pb.global.network.objects.Connection;
import ru.pb.global.network.objects.ObjectNetwork;

import java.io.Serializable;

/**
 * Объект для авторизации Баттла-а на ГС-е
 *
 * @author sjke
 */
public class AuthBattleOnGame<T extends Connection> extends ObjectNetwork<T> implements Serializable {

	private static final long serialVersionUID = 8751239736680660524L;
	private int id;
	private int port;
	private String password;
	private BattleServerAuthResponse response;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public BattleServerAuthResponse getResponse() {
		return response;
	}

	public void setResponse(BattleServerAuthResponse response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "AuthBattleOnGame{" +
				"id=" + id +
				", port=" + port +
				", password='" + password + '\'' +
				", response=" + response +
				'}';
	}
}
