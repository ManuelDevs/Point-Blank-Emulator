package ru.pb.game.network.client.packets.server;

import ru.pb.game.network.client.packets.ServerPacket;

public class SM_1452 extends ServerPacket {

	private int size = 0;

	public SM_1452(int size) {
		super(0x5AC);
		this.size = size;
	}

	@Override
	public void writeImpl() {
		writeD(size);
	}
}
