package ru.pb.game.network.client.packets.client;

import ru.pb.game.network.client.packets.ClientPacket;

public class CM_UNK_3342 extends ClientPacket {
	public CM_UNK_3342(int opcode) {
		super(opcode);
	}

	@Override
	protected void readImpl() {
		readB(4);
	}

	@Override
	protected void runImpl() {

	}
}
