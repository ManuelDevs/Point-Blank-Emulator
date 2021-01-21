package ru.pb.game.network.battle.packets.receivable;

import ru.pb.game.controller.BattleServerController;
import ru.pb.game.network.battle.packets.BattleClientPacket;
import ru.pb.game.network.battle.packets.sendable.SM_REQUEST_AUTH_BATTLE_SERVER;
import ru.pb.global.enums.BattleServerAuthResponse;

public class CM_REQUEST_AUTH_BATTLE_SERVER extends BattleClientPacket {

	private int udpServerPort;
	private BattleServerAuthResponse resp;

	public CM_REQUEST_AUTH_BATTLE_SERVER(int opcode) {
		super(opcode);
	}

	@Override
	protected void readImpl() {
		udpServerPort = readH();
	}

	@Override
	protected void runImpl() {
		resp = BattleServerController.getInstance().registerBattleServer(getConnection(), udpServerPort, "test", udpServerPort);
		sendPacket(new SM_REQUEST_AUTH_BATTLE_SERVER(0x01, resp));
	}
}
