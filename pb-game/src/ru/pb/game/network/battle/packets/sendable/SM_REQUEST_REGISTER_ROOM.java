package ru.pb.game.network.battle.packets.sendable;

import ru.pb.game.network.battle.packets.BattleServerPacket;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;

public class SM_REQUEST_REGISTER_ROOM extends BattleServerPacket {
	private Room room;
	private Player leader;
	private int serverChannel = 0;

	public SM_REQUEST_REGISTER_ROOM(Room room, int serverChannel) {
		super(0x10);
		this.room = room;
		this.serverChannel = serverChannel;
		this.leader = room.getLeader();
	}

	@Override
	public void writeImpl() {
		writeQ((serverChannel << 8) | room.getId() << 16);
		writeB(leader.getConnection().getIPBytes());
	}
}
