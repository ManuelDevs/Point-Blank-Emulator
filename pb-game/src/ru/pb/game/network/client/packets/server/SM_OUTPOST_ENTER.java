package ru.pb.game.network.client.packets.server;

import ru.pb.game.network.client.packets.ServerPacket;

/**
 * Пакет входа в OUTPOST
 *
 * @author: DarkSkeleton
 */
public class SM_OUTPOST_ENTER extends ServerPacket {
	public SM_OUTPOST_ENTER() {
		super(0xB52);
	}

	@Override
	public void writeImpl() {
		writeD(0); //предполагается
	}
}
