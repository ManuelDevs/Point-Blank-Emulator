package ru.pb.game.network.client.packets.server;

import ru.pb.game.network.client.packets.ServerPacket;

public class PROTOCOL_BASE_ENTER_ACK extends ServerPacket {
	public PROTOCOL_BASE_ENTER_ACK() {
		super(0xA14);
	}

	@Override
	public void writeImpl() {
		writeD(0);
	}
}