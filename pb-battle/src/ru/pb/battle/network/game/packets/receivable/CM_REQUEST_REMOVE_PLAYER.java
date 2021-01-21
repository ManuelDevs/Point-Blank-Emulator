package ru.pb.battle.network.game.packets.receivable;

import ru.pb.battle.controllers.RoomController;
import ru.pb.battle.models.Room;
import ru.pb.battle.network.game.packets.BattleClientPacket;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class CM_REQUEST_REMOVE_PLAYER extends BattleClientPacket
{
	private long rid;
	private InetAddress pid;
	public CM_REQUEST_REMOVE_PLAYER(int opcode) {
		super(opcode);
	}

	@Override
	protected void readImpl()
	{
		try {
			rid = readQ();
			pid = InetAddress.getByAddress(readB(4));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void runImpl() {
		Room room = RoomController.getInstance().getRoom(rid);
		if(room != null) {
			long mask = 0x000000FF;
			room.RemovePlayer(rid, pid);
			log.info("Remove player battleId: " + rid  + " Channel: " + (mask & rid >>> 8) + " Room: " + (mask & rid >>> 16) + "");
		}
	}
}
