package ru.pb.game.network.client.packets.client;

import ru.pb.game.network.client.packets.ClientPacket;
import ru.pb.game.network.client.packets.server.PROTOCOL_SCHANNEL_LIST_ACK;

public class PROTOCOL_SCHANNEL_LIST_REQ extends ClientPacket {

	public PROTOCOL_SCHANNEL_LIST_REQ(int opcode) {
		super(opcode);
	}

	@Override
	protected void readImpl() {
		
	}

	@Override
	protected void runImpl() {
		sendPacket(new PROTOCOL_SCHANNEL_LIST_ACK());	
	}

}
