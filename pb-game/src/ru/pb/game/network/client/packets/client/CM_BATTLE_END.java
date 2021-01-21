/*
 * Java Server Emulator Project Blackout / PointBlank
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Authors: DarkSkeleton, sjke, Felixx
 * Copyright (C) 2013 PBDev™
 */

package ru.pb.game.network.client.packets.client;

import ru.pb.game.controller.BattleServerController;
import ru.pb.game.network.battle.packets.sendable.SM_REQUEST_CHANGE_HOST;
import ru.pb.game.network.client.packets.ClientPacket;
import ru.pb.game.network.client.packets.server.SM_BATTLE_CHANGE_NETWORK_INFO;
import ru.pb.game.network.client.packets.server.SM_BATTLE_END;
import ru.pb.game.network.client.packets.server.SM_BATTLE_LEAVE;
import ru.pb.game.network.client.packets.server.SM_ROOM_INFO;
import ru.pb.global.enums.SlotState;
import ru.pb.global.models.BattleServerInfo;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;
import ru.pb.global.models.RoomSlot;
import ru.pb.global.utils.concurrent.ThreadPoolManager;

/**
 * @author: Felixx
 * Date: 03.10.13
 * Time: 7:09
 */
public class CM_BATTLE_END extends ClientPacket {
	public CM_BATTLE_END(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
		if (getBuf().readableBytes() > 0)
			readD(); // id предмета - свободный выход из боя, если он есть
	}

	@Override
	public void runImpl() {
		final Room room = getConnection().getRoom();
		final Player player = getConnection().getPlayer();
		log.info("Player end battle " + player.getName());

		room.getRoomSlotByPlayer(player).setState(SlotState.SLOT_STATE_NORMAL);
		player.getConnection().sendPacket(new SM_BATTLE_END(player, room));

		//если выходит лидер из боя, то меняем лидера
		if (player.equals(room.getLeader()))
			room.setNewLeader();

		BattleServerInfo bsi = BattleServerController.getInstance().getBattleServerInfo(40000);
		if (bsi != null) {
			if (bsi.getConnection() != null) {
				//отправляем на боевой сервер инфу о смене лидера комнаты
				bsi.getConnection().sendPacket(new SM_REQUEST_CHANGE_HOST(room, getConnection().getServerChannel().getId()));
			}
		}

		ThreadPoolManager.getInstance().executeTask(new Runnable() {
			@Override
			public void run() {
				for (Player member : getConnection().getRoom().getPlayers().values()) {
					member.getConnection().sendPacket(new SM_ROOM_INFO(room));
					if (room.getRoomSlotByPlayer(member).getState().ordinal() > 8)
						member.getConnection().sendPacket(new SM_BATTLE_LEAVE(room.getRoomSlotByPlayer(player).getId()));

					if (player.equals(room.getLeader())) {
						member.getConnection().sendPacket(new SM_BATTLE_CHANGE_NETWORK_INFO(room));
					}
				}
			}
		});

		if (room.getLeader().equals(player)) {
			for (int i = 0; i < 16; i++) {
				RoomSlot slot = room.getRoomSlot(i);
				slot.setKillMessage(0);
				slot.setLastKillMessage(0);
				slot.setOneTimeKills(0);
				slot.setAllKills(0);
				slot.setAllDeahts(0);
				slot.setBotScore(0);
			}
			room.setRedKills(0);
			room.setRedDeaths(0);
			room.setBlueKills(0);
			room.setBlueDeaths(0);
			room.setFigth(false);
		}
	}
}
