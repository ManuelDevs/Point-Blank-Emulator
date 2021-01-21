package ru.pb.game.network.client.packets.server;

import ru.pb.game.network.client.packets.ServerPacket;
import ru.pb.global.enums.BattleErrorMessage;

/**
 * Пакет сообщения об ошибке в бою
 *
 * @author DarkSkeleton
 */
public class SM_BATTLE_ERRORMESSAGE extends ServerPacket {
	private BattleErrorMessage error;

	public SM_BATTLE_ERRORMESSAGE(BattleErrorMessage error) {
		super(0x804);
		this.error = error;
	}

	@Override
	public void writeImpl() {
		writeD(error.get());
	}
}
