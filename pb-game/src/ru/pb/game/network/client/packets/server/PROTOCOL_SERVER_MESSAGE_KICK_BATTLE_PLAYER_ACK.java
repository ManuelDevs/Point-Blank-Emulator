package ru.pb.game.network.client.packets.server;

import ru.pb.game.network.client.packets.ServerPacket;

public class PROTOCOL_SERVER_MESSAGE_KICK_BATTLE_PLAYER_ACK extends ServerPacket {

	private final int error;
	
	public PROTOCOL_SERVER_MESSAGE_KICK_BATTLE_PLAYER_ACK(int error) {
		super(2052);
		
		this.error = error;
	}

	@Override
	public void writeImpl() {
		writeD(error);
	}

}
