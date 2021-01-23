package ru.pb.game.network.client.packets.client;

import ru.pb.game.network.client.packets.ClientPacket;
import ru.pb.game.network.client.packets.server.PROTOCOL_BATTLE_BOT_RESPAWN_ACK;
import ru.pb.global.models.Player;

public class PROTOCOL_BATTLE_BOT_RESPAWN_REQ extends ClientPacket {

	private int slot;

	public PROTOCOL_BATTLE_BOT_RESPAWN_REQ(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
		slot = readD();
	}

	@Override
	public void runImpl() {
		getConnection().getRoom().increaseSpawns();
		
		for (Player member : getConnection().getRoom().getPlayers().values()) {
			member.getConnection().sendPacket(new PROTOCOL_BATTLE_BOT_RESPAWN_ACK(slot));
		}
	}
}