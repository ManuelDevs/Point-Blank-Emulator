package ru.pb.game.network.client.packets.server;

import ru.pb.game.network.client.packets.ServerPacket;

/**
 * Пакет выхода из OUTPOST
 *
 * @author: DarkSkeleton
 */
public class SM_OUTPOST_LEAVE extends ServerPacket {
	public SM_OUTPOST_LEAVE() {
		super(0xB54);
	}

	@Override
	public void writeImpl() {
		writeD(0);
	}
}
