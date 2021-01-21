package ru.pb.game.network.battle.packets.sendable;

import ru.pb.game.network.battle.packets.BattleServerPacket;
import ru.pb.global.models.Room;

public class SM_REQUEST_REMOVE_PLAYER extends BattleServerPacket {

	private byte[] ip;
	private Room r;
	private int serverChannel = 0;

	public SM_REQUEST_REMOVE_PLAYER(Room r, byte[] ip, int serverChannel) {
		super(0x13);
		this.ip = ip;
		this.r = r;
		this.serverChannel = serverChannel;
	}

	@Override
	public void writeImpl() {
		writeQ((serverChannel << 8) | (r.getId() << 16));
		writeB(ip);
	}
}
