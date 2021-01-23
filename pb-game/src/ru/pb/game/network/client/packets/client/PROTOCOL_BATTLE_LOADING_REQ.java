package ru.pb.game.network.client.packets.client;

import ru.pb.game.network.client.packets.ClientPacket;
import ru.pb.game.network.client.packets.server.PROTOCOL_BATTLE_ROOM_INFO_ACK;
import ru.pb.global.enums.RoomState;
import ru.pb.global.enums.SlotState;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;
import ru.pb.global.models.RoomSlot;

public class PROTOCOL_BATTLE_LOADING_REQ extends ClientPacket {

	public PROTOCOL_BATTLE_LOADING_REQ(int opcode) {
		super(opcode);
	}

	@Override
	protected void readImpl() {
		readS(readC());
	}

	@Override
	protected void runImpl() {
		Room room = getConnection().getRoom();
		Player player = getConnection().getPlayer();
		
		if(room == null || player == null)
			return;
		
		RoomSlot slot = room.getRoomSlotByPlayer(player);
		if(slot == null)
			return;
		
		room.updateRoomSlotState(slot, SlotState.SLOT_STATE_RENDEZVOUS);
		if(room.getLeader().getId() == player.getId())
		{
			room.setState(RoomState.RENDEZVOUS);
			for(Player member : room.getPlayers().values())
				member.getConnection().sendPacket(new PROTOCOL_BATTLE_ROOM_INFO_ACK(room));
		}
	}

}
