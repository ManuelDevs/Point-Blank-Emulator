package ru.pb.game.network.client.packets.client;

import ru.pb.game.network.client.packets.ClientPacket;

/**
 * Пакет смены хоста(отсылка хостом)
 *
 * @author DarkSkeleton
 */
public class CM_ROOM_CHANGE_HOST extends ClientPacket {

	public CM_ROOM_CHANGE_HOST(int opcode) {
		super(opcode);
	}

	@Override
	protected void readImpl() {
		int slot = readD();
	}

	@Override
	protected void runImpl() {

	}
}
