/**
 * Filename     : CM_BASE_USER_TITLE_USE.java
 * Package name : ru.pb.game.network.client.packets.client
 * Created date : 2 Mar 2014 @ 00.28.28
 */
package ru.pb.game.network.client.packets.client;

import ru.pb.game.network.client.packets.ClientPacket;
import ru.pb.game.network.client.packets.server.SM_BASE_USER_TITLE_USE;

/**
 * @author abujafar
 *
 */
public class CM_BASE_USER_TITLE_USE extends ClientPacket {

	private int slot, titleId;
	
	public CM_BASE_USER_TITLE_USE(int opcode) {
		super(opcode);
	}

	@Override
	protected void readImpl() {
		readH();
		slot = readB(1)[0];
		titleId = readB(1)[0];
	}

	@Override
	protected void runImpl() {
		// TODO:
		// Need real check from player database to make sure the player
		// have needed requirements for each title selected.
		sendPacket(new SM_BASE_USER_TITLE_USE());
	}

}
