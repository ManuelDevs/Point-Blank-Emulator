package ru.pb.game.network.battle.packets.sendable;

import ru.pb.game.network.battle.packets.BattleServerPacket;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;

/**
 * Пакет добавления игрока на боевой сервер
 *
 * @author DarkSkeleton
 */
public class SM_REQUEST_ADD_PLAYER extends BattleServerPacket {
	private final Player player;
	private final Room room;
	private int serverChannel = 0;

	public SM_REQUEST_ADD_PLAYER(Room room, Player player, int serverChannel) {
		super(0x12);
		this.room = room;
		this.player = player;
		this.serverChannel = serverChannel;
	}

	@Override
	public void writeImpl() {
		writeQ((serverChannel << 8) | room.getId() << 16);
		writeB(player.getConnection().getIPBytes());
	}
}
