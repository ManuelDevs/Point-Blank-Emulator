package ru.pb.global.network.objects.UdpGame;

import org.slf4j.LoggerFactory;

import ru.pb.global.models.Room;
import ru.pb.global.network.objects.Connection;
import ru.pb.global.network.objects.ObjectNetwork;

import java.io.Serializable;

public class RegisterRoom<T extends Connection> extends ObjectNetwork<T> implements Serializable {
	private static final long serialVersionUID = -8227238338712335067L;
	private Room room;

	public RegisterRoom(Room room) {
		LoggerFactory.getLogger(RegisterRoom.class).info("new RegisterRoom");
		this.room = room;
	}

	public Room getRoom() {
		return room;
	}
}
