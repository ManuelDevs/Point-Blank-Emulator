package ru.pb.game.network.client.packets.server;

import ru.pb.game.network.client.packets.ServerPacket;
import ru.pb.global.models.Clan;
import ru.pb.global.models.Room;
import ru.pb.global.models.RoomSlot;

public class PROTOCOL_ROOM_SLOT_INFO_ACK extends ServerPacket {

	private final Room room;

	public PROTOCOL_ROOM_SLOT_INFO_ACK(Room room) {
		super(3861);
		this.room = room;
	}

	@Override
	public void writeImpl() {

		if (room.getRoomSlotByPlayer(room.getLeader()) == null)
			room.setNewLeader();

		writeD(room.getRoomSlotByPlayer(room.getLeader()).getId());
		for (int i = 0; i < 16; i++) {
			RoomSlot slot = room.getRoomSlot(i);
			writeC(slot.getState().ordinal());
			writeC(slot.getPlayer() == null ? 0 : slot.getPlayer().getRank());
			writeD(0); // clan id
			writeD(0); // clan access
			writeC(0); // clan rank
			writeC(255);
			writeC(255);
			writeC(255);
			writeC(255);
			writeC(0);
			writeC(0); // tourney level
			writeD(0); // effects
			writeS("", Clan.CLAN_NAME_SIZE);
			writeD(0);
			writeC(31);
		}
	}
}