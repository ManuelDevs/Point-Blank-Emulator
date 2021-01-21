package ru.pb.battle.network.game.packets.receivable;

import ru.pb.battle.network.game.packets.BattleClientPacket;
import ru.pb.global.enums.BattleServerAuthResponse;

public class CM_REQUEST_AUTH_IN_GAMESERVER extends BattleClientPacket {
	private BattleServerAuthResponse resp;

	public CM_REQUEST_AUTH_IN_GAMESERVER(int opcode) {
		super(opcode);
	}

	@Override
	protected void readImpl() {
		resp = BattleServerAuthResponse.valueOf(readC());
	}

	@Override
	protected void runImpl() {
		switch (resp) {
			case AUTHED: {
				log.info("BattleServer successfully registred, response: " + resp);
				break;
			}
			case NOT_FOUND: {
				log.info("BattleServer not found in gameserver, response: " + resp);
				break;
			}
			case INCORRECT: {
				log.info("BattleServer incorrect password, response: " + resp);
				break;
			}
			case ALREADY_REGISTERED: {
				log.info("BattleServer already registred, response: " + resp);
				break;
			}
		}
	}
}
