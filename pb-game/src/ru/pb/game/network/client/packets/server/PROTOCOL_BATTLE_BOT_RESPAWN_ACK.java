package ru.pb.game.network.client.packets.server;

import ru.pb.game.network.client.packets.ServerPacket;

public class PROTOCOL_BATTLE_BOT_RESPAWN_ACK extends ServerPacket {

	private int slot;

	public PROTOCOL_BATTLE_BOT_RESPAWN_ACK(int slot) {
		super(3379);
		this.slot = slot;
	}

	@Override
	public void writeImpl() {
		writeD(slot); 
	}
}
