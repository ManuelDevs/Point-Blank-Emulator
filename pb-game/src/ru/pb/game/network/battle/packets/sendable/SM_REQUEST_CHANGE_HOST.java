package ru.pb.game.network.battle.packets.sendable;

import ru.pb.game.network.battle.packets.BattleServerPacket;
import ru.pb.global.models.Room;

public class SM_REQUEST_CHANGE_HOST extends BattleServerPacket {

	private Room room;
	private int serverChannel;

	public SM_REQUEST_CHANGE_HOST(Room room, int serverChannel) {
		super(0x14);
		this.room = room;
		this.serverChannel = serverChannel;
	}

	@Override
	public void writeImpl() {
		writeQ((serverChannel << 8) | room.getId() << 16);
		writeB(room.getLeader().getConnection().getIPBytes());
	}
}
