package ru.pb.battle.network.game.packets.receivable;

import ru.pb.battle.controllers.RoomController;
import ru.pb.battle.models.Room;
import ru.pb.battle.network.game.packets.BattleClientPacket;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class CM_REQUEST_CHANGE_HOST extends BattleClientPacket {

	private long rid;
	private InetAddress host;

	public CM_REQUEST_CHANGE_HOST(int opcode) {
		super(opcode);
	}

	@Override
	protected void readImpl() {
		try {
			rid = readQ();
			host = InetAddress.getByAddress(readB(4));
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void runImpl()
	{
		Room room = RoomController.getInstance().getRoom(rid);
		if(room != null) {
			if(host != null) {
				room.inetAddress = host;
				long mask = 0x000000FF;
				log.debug("Change host battleId: " + rid  + " Channel: " + (mask & rid >>> 8) + " Room: " + (mask & rid >>> 16) + "");
			}
		}
	}
}
