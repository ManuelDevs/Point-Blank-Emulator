package ru.pb.battle.network.game.packets.sendable;

import ru.pb.battle.network.game.packets.BattleServerPacket;
import ru.pb.battle.properties.BattleServerProperty;

public class SM_REQUEST_AUTH_IN_GAMESERVER extends BattleServerPacket {

	public SM_REQUEST_AUTH_IN_GAMESERVER() {
		super(0x00);
	}

	@Override
	public void writeImpl() {
		writeH(BattleServerProperty.getInstance().BATTLE_SERVER_PORT);
	}
}
