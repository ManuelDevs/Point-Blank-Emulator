package ru.pb.game.network.battle.packets.sendable;

import ru.pb.game.network.battle.packets.BattleServerPacket;
import ru.pb.global.models.Room;

public class SM_REQUEST_UNREGISTER_ROOM extends BattleServerPacket {
	private Room room;
	private int serverChannel = 0;

	public SM_REQUEST_UNREGISTER_ROOM(Room room, int serverChannel) {
		super(0x11);
		this.room = room;
		this.serverChannel = serverChannel;
	}

	@Override
	public void writeImpl() {
		writeQ((serverChannel << 8) | room.getId() << 16);
	}
}
