package ru.pb.game.network.battle.packets.sendable;

import ru.pb.game.network.battle.packets.BattleServerPacket;
import ru.pb.global.enums.BattleServerAuthResponse;

public class SM_REQUEST_AUTH_BATTLE_SERVER extends BattleServerPacket {

	private BattleServerAuthResponse resp;

	public SM_REQUEST_AUTH_BATTLE_SERVER(int opcode, BattleServerAuthResponse resp) {
		super(opcode);
		this.resp = resp;
	}

	@Override
	public void writeImpl() {
		writeC(resp.getResponseId());
	}
}
