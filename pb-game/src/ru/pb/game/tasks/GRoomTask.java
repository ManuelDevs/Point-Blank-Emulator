package ru.pb.game.tasks;

import ru.pb.game.controller.BattleServerController;
import ru.pb.game.network.battle.packets.sendable.SM_REQUEST_READY_BATTLE_ROOM;
import ru.pb.game.network.client.packets.server.*;
import ru.pb.global.enums.SlotState;
import ru.pb.global.models.BattleServerInfo;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;
import ru.pb.global.models.RoomSlot;
import ru.pb.global.tasks.IRoomTask;

public class GRoomTask implements IRoomTask {

	@Override
	public void run(int channel, Room room) {
		int countPlayerPrestart = 0;
		
		int redTeamReady = 0; 
		int blueTeamReady = 0;
		
		for(Player player : room.getPlayers().values()) {
			RoomSlot slot = room.getRoomSlotByPlayer(player);
			if (slot.getState().ordinal() > 8 && slot.getState().ordinal() < 12) {
				countPlayerPrestart++;
			}
			else {
				//Актуально для всех режимов кроме тренировки и туториала
				if(room.getSpecial() != 6) { //TODO: айди туториала
					if(slot.getId() % 2 == 0)
						redTeamReady+=1;
					else
						blueTeamReady+=1;
				}
			}
		}

		RoomSlot leader = room.getRoomSlotByPlayer(room.getLeader());

		if(leader.getState().ordinal() < 13) {
			if (redTeamReady > 0 && blueTeamReady > 0 || countPlayerPrestart == 0) {
				//если лидер еще не в бою - выполняем этот сценарий для запуска игроков в бой
				if(leader.getState().ordinal() < 13 ) {
					for (Player member : room.getPlayers().values()) {
						RoomSlot playerSlot = room.getRoomSlotByPlayer(member);
						if (playerSlot.getState() == SlotState.SLOT_STATE_BATTLE_READY) {
							playerSlot.setState(SlotState.SLOT_STATE_BATTLE);
							member.getConnection().sendPacket(new SM_BATTLE_PLAYER_INFO(room, member));
							for (Player toSend : room.getPlayers().values()) {
								if (!toSend.equals(member)) {
									RoomSlot toSendplayerSlot = room.getRoomSlotByPlayer(toSend);
									if (toSendplayerSlot.getState().ordinal() > 11)
										member.getConnection().sendPacket(new SM_BATTLE_PLAYER_INFO(room, toSend));
								}
							}
							member.getConnection().sendPacket(new SM_UNK_3865());
							member.getConnection().sendPacket(new SM_BATTLE_ROUND_START(room));
							BattleServerInfo bsi = BattleServerController.getInstance().getBattleServerInfo(40000);
							if (bsi != null) {
								if (bsi.getConnection() != null) {
									if (room.getRoomSlotByPlayer(member).getState().ordinal() > 8) {
										bsi.getConnection().sendPacket(new SM_REQUEST_READY_BATTLE_ROOM(room, member, channel));
									}
								}
							}
						}
					}
				}
			}
		}
		else {
			for(Player player : room.getPlayers().values()) {
				RoomSlot playerSlot = room.getRoomSlotByPlayer(player);
				if(playerSlot.getState().ordinal() == 12) {
					player.getConnection().sendPacket(new SM_BATTLE_BOT_INFO(room));
					playerSlot.setState(SlotState.SLOT_STATE_BATTLE);
					player.getConnection().sendPacket(new SM_BATTLE_PLAYER_INFO(room, player));
					for(Player player1 : room.getPlayers().values()) {
						RoomSlot why = room.getRoomSlotByPlayer(player1);
						if(why.getState().ordinal() == 13 && !player.equals(player1)) {
							player1.getConnection().sendPacket(new SM_BATTLE_PLAYER_INFO(room, player));
							player.getConnection().sendPacket(new SM_BATTLE_PLAYER_INFO(room, player1));
						}
					}
					player.getConnection().sendPacket(new SM_UNK_3865());
					player.getConnection().sendPacket(new SM_BATTLE_ROUND_START(room));
					BattleServerInfo bsi = BattleServerController.getInstance().getBattleServerInfo(40000);
					if (bsi != null) {
						if (bsi.getConnection() != null) {
							if (room.getRoomSlotByPlayer(player).getState().ordinal() > 8) {
								bsi.getConnection().sendPacket(new SM_REQUEST_READY_BATTLE_ROOM(room, player, channel));
							}
						}
					}
				}
			}
		}

		for(Player player : room.getPlayers().values()) {
			player.getConnection().sendPacket(new SM_ROOM_INFO(room));
		}
	}
}

