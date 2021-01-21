package ru.pb.battle.network.game.packets.receivable;

import ru.pb.battle.controllers.RoomController;
import ru.pb.battle.network.game.packets.BattleClientPacket;

public class CM_REQUEST_UNREGISTER_ROOM extends BattleClientPacket {
	private long roomId;

	public CM_REQUEST_UNREGISTER_ROOM(int opcode) {
		super(opcode);
	}

	@Override
	protected void readImpl() {
		roomId = readQ();
	}

	@Override
	protected void runImpl() {
		RoomController.getInstance().removeRoom(roomId);
	}
}
