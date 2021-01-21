/**
 * Filename     : SM_BASE_USER_TITLE_GET.java
 * Package name : ru.pb.game.network.client.packets.server
 * Created date : 2 Mar 2014 @ 00.33.59
 */
package ru.pb.game.network.client.packets.server;

import ru.pb.game.network.client.packets.ServerPacket;

/**
 * @author abujafar
 *
 */
public class SM_BASE_USER_TITLE_GET extends ServerPacket {
	private int errNo, openedSlot;
	
	public SM_BASE_USER_TITLE_GET(int errNo, int openedSlot) {
		super(0xA3C);
		this.errNo = errNo;
		this.openedSlot = openedSlot;
	}

	@Override
	public void writeImpl() {
		writeD(this.errNo);	// error number
		writeD(this.openedSlot);	// slot will opened
	}

}
