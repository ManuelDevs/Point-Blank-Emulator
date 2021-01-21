/**
 * Filename     : CM_BASE_USER_TITLE_GET.java
 * Package name : ru.pb.game.network.client.packets.client
 * Created date : 2 Mar 2014 @ 00.27.13
 */
package ru.pb.game.network.client.packets.client;

import ru.pb.game.network.client.packets.ClientPacket;
import ru.pb.game.network.client.packets.server.SM_BASE_USER_TITLE_GET;
import ru.pb.game.network.client.packets.server.SM_SEND_NOTICE;
import ru.pb.global.models.Player;

/**
 * @author abujafar
 *
 */
public class CM_BASE_USER_TITLE_GET extends ClientPacket {

	private int titleId;
	
	public CM_BASE_USER_TITLE_GET(int opcode) {
		super(opcode);
	}

	@Override
	protected void readImpl() {
		titleId = readB(1)[0];
	}

	@Override
	protected void runImpl() {
		// TODO:
		// Need real check from player database to make sure the player
		// have needed requirements for each title selected.
		
		int errNo = 0x80000000;
		int openedSlot = 1;
		
		Player player = getConnection().getPlayer();
				
		if(player != null) {
			int rank = player.getRank().intValue();
			
			switch(this.titleId) {
			case 1:
			case 2:
			case 3:
			case 4:
				if(rank >= 1) {
					openedSlot = 1;
					errNo = 0;
				}
				break;
				
			case 5:
			case 6:
			case 7:
				if(rank >= 5) {
					openedSlot = 2;
					errNo = 0;
				}
				break;
				
			case 8:
			case 26:
			case 14:
			case 30:
			case 20:
			case 40:
			case 35:
				if(rank >= 8) {
					openedSlot = 2;
					errNo = 0;
				}
				break;
			
			case 9:
			case 27:
			case 15:
			case 31:
			case 21:
			case 41:
			case 36:
				if(rank >= 12) {
					openedSlot = 2;
					errNo = 0;
				}
				break;
				
			case 10:
			case 28:
			case 16:
			case 32:
			case 22:
			case 42:
			case 37:
				if(rank >= 17) {
					openedSlot = 2;
					errNo = 0;
				}
				break;
				
			case 11:
			case 17:
			case 23:
			case 43:
				if(rank >= 21) {
					openedSlot = 3;
					errNo = 0;
				}
				break;
				
			case 12:
			case 18:
			case 33:
			case 24:
			case 38:
				if(rank >= 26) {
					openedSlot = 3;
					errNo = 0;
				}
				break;
				
			case 13:
			case 29:
			case 19:
			case 34:
			case 25:
			case 44:
			case 39:
				if(rank >= 31) {
					openedSlot = 3;
					errNo = 0;
				}
				break;
			}
		}
		
		sendPacket(new SM_BASE_USER_TITLE_GET(errNo, openedSlot));
		
		if(errNo != 0) {
			sendPacket(new SM_SEND_NOTICE("Title aquisiton failed!\nMake sure you have met the title aquisiton requirements."));
		}

	}

}
