package ru.pb.battle.network.game.packets.receivable;

import ru.pb.battle.controllers.RoomController;
import ru.pb.battle.models.Player;
import ru.pb.battle.models.Room;
import ru.pb.battle.network.game.packets.BattleClientPacket;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Пакет добавления игрока на боевой сервер
 *
 * @author DarkSkeleton
 */
public class CM_REQUEST_ADD_PLAYER extends BattleClientPacket {
	private InetAddress player;
	private long rid;

    public CM_REQUEST_ADD_PLAYER(int opcode) {
        super(opcode);
    }

	@Override
	protected void readImpl() {

		try {
			rid = readQ();
			player = InetAddress.getByAddress(readB(4));
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void runImpl() {
		Room room1 = RoomController.getInstance().getRoom(rid);
		if(room1 != null) {
			if(!room1.getPlayers().equals(new Player(player,0,false))) {
				room1.getPlayers().put((long)room1.getPlayers().size(), new Player(player,0,false));
				long mask = 0x000000FF;
				log.info("Add Player battleId: " + player.toString() + " Channel: " + (mask & rid >>> 8) + " Room: " + (mask & rid >>> 16) + "");
			}
		}
	}
}
