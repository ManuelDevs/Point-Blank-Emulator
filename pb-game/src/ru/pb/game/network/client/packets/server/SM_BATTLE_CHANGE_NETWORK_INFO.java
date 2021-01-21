package ru.pb.game.network.client.packets.server;

import ru.pb.game.network.client.packets.ServerPacket;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;

/**
 * Пакет обновления сетевой инфы.
 *
 * @author DarkSkeleton
 */
public class SM_BATTLE_CHANGE_NETWORK_INFO extends ServerPacket {
	Room room;

	public SM_BATTLE_CHANGE_NETWORK_INFO(Room room) {
		super(0xD13);
		this.room = room;
	}

	@Override
	public void writeImpl() {
		if (room != null) {
			writeD(room.getRoomSlotByPlayer(room.getLeader()).getId());
			for (int i = 0; i < 16; i++) {
				Player p = room.getRoomSlot(i).getPlayer();
				if (p != null) {
					writeB(p.getConnection().getIPBytes());
					writeH(29890);
					writeB(p.getConnection().getIPBytes());
					writeH(29890);
					writeC(0); //NAT?
				} else {
					writeB(new byte[13]);
				}
			}
		}
	}
}
