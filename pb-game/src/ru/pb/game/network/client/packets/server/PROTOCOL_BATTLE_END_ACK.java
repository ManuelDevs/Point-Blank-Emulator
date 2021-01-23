package ru.pb.game.network.client.packets.server;

import ru.pb.game.network.client.packets.ServerPacket;
import ru.pb.global.models.LevelUpInfo;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;
import ru.pb.global.models.RoomSlot;
import ru.pb.global.service.LevelUpDaoService;

public class PROTOCOL_BATTLE_END_ACK extends ServerPacket {

	private final Player player;
	private final Room room;

	public PROTOCOL_BATTLE_END_ACK(Player player, Room room) {
		super(0xD08);
		this.player = player;
		this.room = room;
	}

	@Override
	public void writeImpl() {
		if(room.getType() == 4 || room.getType() == 2) // bomb
			writeC(2);
		else
			writeC(room.getRedKills() > room.getBlueKills() ? 0 : (room.getRedKills() < room.getBlueKills() ? 1 : 2));

		writeH(room.getSlotFlag(false, false));
		writeH(room.getSlotFlag(false, true));
		
		for (int i = 0; i < 16; i++) {
			RoomSlot slot = room.getRoomSlot(i);
			writeH(slot.getAllExp());
		}
		
		for (int i = 0; i < 16; i++) {
			RoomSlot slot = room.getRoomSlot(i);
			writeH(slot.getAllGp());
		}
		
		for (int i = 0; i < 16; i++) {
			RoomSlot slot = room.getRoomSlot(i);
			writeH(0); // exp
		}
		
		for (int i = 0; i < 16; i++) {
			RoomSlot slot = room.getRoomSlot(i);
			writeH(0); // points
		}
		
		for (int i = 0; i < 16; i++) {
			RoomSlot slot = room.getRoomSlot(i);
			writeC(0); // tag
		}
		
		writeS(player.getName(), Player.MAX_NAME_SIZE); 
		writeD(player.getExp());
		writeD(player.getRank());
		writeD(player.getRank());
		writeD(player.getGp());
		writeD(con.getAccount().getMoney());

		writeD(0); // clan id
		writeD(0);  // clan access
		writeD(0);
		writeD(0);
		writeC(0);
		writeC(0);
		writeC(player.getColor());
		
		writeS("", 17);
		writeC(0);
		writeC(0);
		writeC(255);
		writeC(255);
		writeC(255);
		writeC(255);
		writeC(0);
		

		writeD(0);
        writeC(0);
        writeD(0);
        writeD(0); // last rankup

		writeD(player.getStats().getFights());
		writeD(player.getStats().getWins());
		writeD(player.getStats().getLosts());
		writeD(player.getStats().getDraws());
		writeD(player.getStats().getKills());
		writeD(player.getStats().getSeriaWins());
		writeD(player.getStats().getDeaths());
		writeD(0); // unk
		writeD(player.getStats().getKpd());
		writeD(player.getStats().getEscapes());
		writeD(player.getStats().getSeasonFights());
		writeD(player.getStats().getSeasonWins());
		writeD(player.getStats().getSeasonLosts());
		writeD(player.getStats().getSeasonDraws());
		writeD(player.getStats().getSeasonKills());
		writeD(player.getStats().getSeasonSeriaWins());
		writeD(player.getStats().getSeasonDeaths());
		writeD(0); // unk
		writeD(player.getStats().getSeasonKpd());
		writeD(player.getStats().getSeasonEscapes());

		
		if(room.isBot())
		{
			for (int i = 0; i < 16; i++) {
				RoomSlot slot = room.getRoomSlot(i);
				writeH(slot.getBotScore());
			}
		}
		
		writeC(0);
		writeD(0);
		writeB(new byte[16]);
		// TODO: Code other results screens
	}
}