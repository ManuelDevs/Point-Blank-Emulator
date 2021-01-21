/**
 * Filename     : SM_BASE_USER_TITLE_DETACH.java
 * Package name : ru.pb.game.network.client.packets.server
 * Created date : 2 Mar 2014 @ 00.35.34
 */
package ru.pb.game.network.client.packets.server;

import ru.pb.game.network.client.packets.ServerPacket;

/**
 * @author abujafar
 *
 */
public class SM_BASE_USER_TITLE_DETACH extends ServerPacket {

	public SM_BASE_USER_TITLE_DETACH() {
		super(0xA40);
	}

	@Override
	public void writeImpl() {
		writeD(0);	// error number
	}

}
