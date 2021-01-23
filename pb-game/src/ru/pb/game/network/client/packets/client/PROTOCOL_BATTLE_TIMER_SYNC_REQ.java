package ru.pb.game.network.client.packets.client;

import ru.pb.game.network.client.packets.ClientPacket;
import ru.pb.game.network.client.packets.server.PROTOCOL_BATTLE_END_ACK;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;

public class PROTOCOL_BATTLE_TIMER_SYNC_REQ extends ClientPacket {

	private int timeLost, unkD, unkC1, unkC2;

	public PROTOCOL_BATTLE_TIMER_SYNC_REQ(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
		timeLost = readD();
		unkD = readD();
		unkC1 = readC();
		unkC2 = readC();
	}

	@Override
	public void runImpl() {
		final Room room = getConnection().getRoom();
		final Player player = getConnection().getPlayer();
		
		if(room == null || player == null)
			return;
		
		if(player.getId() == room.getLeader().getId())
		{
			room.setTimeLost(timeLost);
			if(timeLost < 1)
			{
				room.calculateResults();
				
				for (Player member : getConnection().getRoom().getPlayers().values())
					if (room.getRoomSlotByPlayer(member).getState().ordinal() >= 9) 
						member.getConnection().sendPacket(new PROTOCOL_BATTLE_END_ACK(member, getConnection().getRoom()));
				
				room.resetRoom();
			}
		}
	}
}