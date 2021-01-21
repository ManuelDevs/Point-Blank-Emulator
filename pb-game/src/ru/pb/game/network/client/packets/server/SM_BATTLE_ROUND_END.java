package ru.pb.game.network.client.packets.server;

import ru.pb.game.network.client.packets.ServerPacket;

/**
 * Пакет завершения раунда
 *
 * @author DarkSkeleton
 */
public class SM_BATTLE_ROUND_END extends ServerPacket {
	public SM_BATTLE_ROUND_END() {
		super(0xF1D);
	}

	@Override
	public void writeImpl() {
		writeC(0);
		writeD(1); // в дампе летит 1(с итальянского сервера)
		writeC(0);
	}
}
