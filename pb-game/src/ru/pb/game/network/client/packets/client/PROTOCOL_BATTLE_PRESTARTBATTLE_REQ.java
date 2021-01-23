package ru.pb.game.network.client.packets.client;

import ru.pb.game.controller.BattleServerController;
import ru.pb.game.network.battle.packets.sendable.SM_REQUEST_ADD_PLAYER;
import ru.pb.game.network.client.packets.ClientPacket;
import ru.pb.game.network.client.packets.server.PROTOCOL_BATTLE_PRESTARTBATTLE_ACK;
import ru.pb.game.network.client.packets.server.PROTOCOL_BATTLE_ROOM_INFO_ACK;
import ru.pb.game.network.client.packets.server.PROTOCOL_LOBBY_ENTER_ACK;
import ru.pb.game.network.client.packets.server.PROTOCOL_SERVER_MESSAGE_KICK_BATTLE_PLAYER_ACK;
import ru.pb.global.enums.RoomErrorMessage;
import ru.pb.global.enums.RoomState;
import ru.pb.global.enums.SlotState;
import ru.pb.global.models.BattleServerInfo;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;
import ru.pb.global.models.RoomSlot;

public class PROTOCOL_BATTLE_PRESTARTBATTLE_REQ extends ClientPacket {

	private int mapId;
	private int stage4v4;
	private int roomType;
	
	public PROTOCOL_BATTLE_PRESTARTBATTLE_REQ(int opcode) {
		super(opcode);
	}

	@Override
	protected void readImpl() {
		mapId = readH();
		stage4v4 = readC();
		roomType = readC();
		
	}

	@Override
	protected void runImpl() {
		Room room = getConnection().getRoom();
		Player player = getConnection().getPlayer();
		
		if(room != null && room.getMapId() == mapId && room.getStage4v4() == stage4v4 && room.getType() == roomType)
		{
			BattleServerInfo bsi = BattleServerController.getInstance().getBattleServerInfo(40009); // TODO: Make a neat code 
			if (bsi != null) {
				if (bsi.getConnection() != null) {
					if (!room.getLeader().equals(player) && room.getRoomSlotByPlayer(room.getLeader()).getState().ordinal() > 8) {
						bsi.getConnection().sendPacket(new SM_REQUEST_ADD_PLAYER(room, getConnection().getPlayer(), getConnection().getServerChannel().getId()));
					}
				}
			}
			
			RoomSlot slot = room.getRoomSlotByPlayer(player);
			if(room.isPreparing() && slot.getState().ordinal() >= 9)
			{
				// TODO: Check if miss local ip / public ip check
				if(player.getId() == room.getLeader().getId())
				{
					room.setState(RoomState.PRE_BATTLE);
					for(Player member : room.getPlayers().values())
						member.getConnection().sendPacket(new PROTOCOL_BATTLE_ROOM_INFO_ACK(room));
				}
				room.updateRoomSlotState(slot, SlotState.SLOT_STATE_PRESTART);
				sendPacket(new PROTOCOL_BATTLE_PRESTARTBATTLE_ACK(room, player, true, true));
				if(player.getId() != room.getLeader().getId())
					room.getLeader().getConnection().sendPacket(new PROTOCOL_BATTLE_PRESTARTBATTLE_ACK(room, player, true, false));

				log.info("Room prestarting correctly.");
			}
			else
			{
				log.info("Room prestarting in a incorrect way.");
			}
			
		}
		else
		{
			sendPacket(new PROTOCOL_SERVER_MESSAGE_KICK_BATTLE_PLAYER_ACK(RoomErrorMessage.BATTLE_FIRST_HOLE_MAIN.get()));
			sendPacket(new PROTOCOL_BATTLE_PRESTARTBATTLE_ACK(null, null, false, false));
			
			if(room != null)
			{
				room.updateRoomSlotState(room.getRoomSlotByPlayer(player), SlotState.SLOT_STATE_NORMAL);
				log.info("Room prestart was starting with wrong data. Cancelled.");
			}
			else 
			{
				sendPacket(new PROTOCOL_LOBBY_ENTER_ACK());
				log.info("Room prestart was starting with null room. Cancelled.");
			}
		}
	}

}
