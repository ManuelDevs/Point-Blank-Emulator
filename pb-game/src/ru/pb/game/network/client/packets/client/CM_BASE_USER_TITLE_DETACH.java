/**
 * Filename     : CM_BASE_USER_TITLE_DETACH.java
 * Package name : ru.pb.game.network.client.packets.client
 * Created date : 2 Mar 2014 @ 00.29.08
 */
package ru.pb.game.network.client.packets.client;

import ru.pb.game.network.client.packets.ClientPacket;
import ru.pb.game.network.client.packets.server.SM_BASE_USER_TITLE_DETACH;

/**
 * @author abujafar
 *
 */
public class CM_BASE_USER_TITLE_DETACH extends ClientPacket {
	private int titleId;
	
	public CM_BASE_USER_TITLE_DETACH(int opcode) {
		super(opcode);
	}

	@Override
	protected void readImpl() {
		readH();
		titleId = readB(1)[0];
	}

	@Override
	protected void runImpl() {
		// TODO:
		// Need real check from player database to make sure the player
		// have needed requirements for each title selected.
		sendPacket(new SM_BASE_USER_TITLE_DETACH());
	}

}
