/**
 * Filename     : SM_BASE_USER_TITLE_USE.java
 * Package name : ru.pb.game.network.client.packets.server
 * Created date : 2 Mar 2014 @ 00.34.46
 */
package ru.pb.game.network.client.packets.server;

import ru.pb.game.network.client.packets.ServerPacket;

/**
 * @author abujafar
 *
 */
public class SM_BASE_USER_TITLE_USE extends ServerPacket {

	public SM_BASE_USER_TITLE_USE() {
		super(0xA3E);
	}

	@Override
	public void writeImpl() {
		writeD(0);	// error number
		
	}

}
