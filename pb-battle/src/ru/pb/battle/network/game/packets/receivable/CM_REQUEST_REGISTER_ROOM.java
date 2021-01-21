package ru.pb.battle.network.game.packets.receivable;

import ru.pb.battle.controllers.RoomController;
import ru.pb.battle.models.Player;
import ru.pb.battle.models.Room;
import ru.pb.battle.network.game.packets.BattleClientPacket;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;

public class CM_REQUEST_REGISTER_ROOM extends BattleClientPacket {

	private long roomId;
	private ConcurrentHashMap<Long, Player> players = new ConcurrentHashMap<Long, Player>();
	private InetAddress host;

	public CM_REQUEST_REGISTER_ROOM(int opcode) {
		super(opcode);
	}

	@Override
	protected void readImpl() {
		try {
			roomId = readQ();
			host = InetAddress.getByAddress(readB(4));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void runImpl() {
		Room room = new Room();
		room.inetAddress = host;
		room.isActive = false;
		room.id = roomId;
		room.port = 0;
		RoomController.getInstance().addRoom(roomId, room);
	}
}
