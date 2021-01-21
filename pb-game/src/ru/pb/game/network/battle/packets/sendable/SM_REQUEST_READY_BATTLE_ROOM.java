package ru.pb.game.network.battle.packets.sendable;

import ru.pb.game.network.battle.packets.BattleServerPacket;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;

public class SM_REQUEST_READY_BATTLE_ROOM extends BattleServerPacket
{
	private Room room;
	private Player player;
	private int channelId;
	public SM_REQUEST_READY_BATTLE_ROOM(Room room, Player player, int serverChannel) {
		super(0x15);
		this.room = room;
		this.player = player;
		this.channelId = serverChannel;
	}

	@Override
	public void writeImpl() {
		writeQ((channelId << 8) | (room.getId() << 16));
		writeB(player.getConnection().getIPBytes());
	}
}
