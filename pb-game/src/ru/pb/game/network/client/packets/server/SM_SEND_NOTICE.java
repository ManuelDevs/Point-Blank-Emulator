/**
 * Filename     : SM_SEND_NOTICE.java
 * Package name : ru.pb.game.network.client.packets.server
 * Created date : 3 Mar 2014 @ 14.48.15
 */
package ru.pb.game.network.client.packets.server;

import ru.pb.game.network.client.packets.ServerPacket;

/**
 * @author abujafar
 *
 */
public class SM_SEND_NOTICE extends ServerPacket {

	String message;
	public SM_SEND_NOTICE(String message) {
		super(0x807);
		this.message = message;
	}

	@Override
	public void writeImpl() {
		// TODO Auto-generated method stub
		if(this.message.length() > 0) {
			writeD(2);
			writeD(this.message.length());
			writeB(this.message.getBytes());
		}
	}

}
