package ru.pb.battle.network.game.packets.receivable;

import ru.pb.battle.controllers.RoomController;
import ru.pb.battle.models.Player;
import ru.pb.battle.models.Room;
import ru.pb.battle.network.game.packets.BattleClientPacket;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class CM_REQUEST_READY_BATTLE_ROOM extends BattleClientPacket {

	private long rid;
	private byte[] addr;
	public CM_REQUEST_READY_BATTLE_ROOM(int opcode) {
		super(opcode);
	}

	@Override
	protected void readImpl() {
		 rid = readQ();
		 addr = readB(4);
	}

	@Override
	protected void runImpl() {
		try {
			Room room = RoomController.getInstance().getRoom(rid);
			Player player = RoomController.getInstance().getPlayer(InetAddress.getByAddress(addr));
			if(player != null) {
				player.setActive(true);
			}
			if(room.inetAddress.equals(InetAddress.getByAddress(addr))) {
				room.isActive = true;
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
