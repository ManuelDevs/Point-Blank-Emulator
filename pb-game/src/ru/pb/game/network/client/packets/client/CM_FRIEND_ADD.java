package ru.pb.game.network.client.packets.client;

import ru.pb.game.network.client.packets.ClientPacket;

public class CM_FRIEND_ADD extends ClientPacket {
	private String friend;

	public CM_FRIEND_ADD(int opcode) {
		super(opcode);
	}

	@Override
	protected void readImpl() {
		friend = readS(33);
	}

	@Override
	protected void runImpl() {

	}
}
