package ru.pb.game.network.client.packets.server;

import ru.pb.game.network.client.packets.ServerPacket;

/**
 * Пакет выхода из боя игрока(рассылается всем игрокам кроме выходящего из боя)
 *
 * @author DarkSkeleton
 */
public class SM_BATTLE_LEAVE extends ServerPacket {

	private int slot;

	public SM_BATTLE_LEAVE(int slot) {
		super(0xD39); //0xF0A
		this.slot = slot;
	}

	@Override
	public void writeImpl() {
		writeD(slot);
		writeB(new byte[20]);
	}
}
