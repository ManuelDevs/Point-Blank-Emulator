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
import ru.pb.game.network.battle.packets.sendable.SM_REQUEST_ADD_PLAYER;
import ru.pb.game.network.client.packets.ClientPacket;
import ru.pb.game.network.client.packets.server.SM_BATTLE_ERRORMESSAGE;
import ru.pb.game.network.client.packets.server.SM_BATTLE_PREPARATION;
import ru.pb.game.network.client.packets.server.SM_BATTLE_ROOM_INFO;
import ru.pb.global.enums.BattleErrorMessage;
import ru.pb.global.enums.SlotState;
import ru.pb.global.models.BattleServerInfo;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;
import ru.pb.global.models.RoomSlot;
import ru.pb.global.utils.concurrent.ThreadPoolManager;

/**
 * Неизветный пакет
 *
 * @author sjke
 */
public class CM_BATTLE_RPE_START extends ClientPacket {

	public CM_BATTLE_RPE_START(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
	}

	@Override
	public void runImpl() {

		Room room = getConnection().getRoom();

		if (room == null)
			return;

		Player leader = getConnection().getRoom().getLeader();
		Player player = getConnection().getPlayer();

		BattleServerInfo bsi = BattleServerController.getInstance().getBattleServerInfo(40000);
		if (bsi != null) {
			if (bsi.getConnection() != null) {
				if (!room.getLeader().equals(player) && room.getRoomSlotByPlayer(room.getLeader()).getState().ordinal() > 8) {
					bsi.getConnection().sendPacket(new SM_REQUEST_ADD_PLAYER(room, getConnection().getPlayer(), getConnection().getServerChannel().getId()));
				}
			}
		} else
			getConnection().sendPacket(new SM_BATTLE_ERRORMESSAGE(BattleErrorMessage.EVENT_ERROR_FIRST_HOLE));

		room.getRoomSlotByPlayer(player).setState(SlotState.SLOT_STATE_PRESTART);
		getConnection().sendPacket(new SM_BATTLE_PREPARATION(room, player));
		if (player.getId() != leader.getId())
			leader.getConnection().sendPacket(new SM_BATTLE_PREPARATION(room, player));

		ThreadPoolManager.getInstance().executeTask(new Runnable() {
			@Override
			public void run() {
				for (Player member : getConnection().getRoom().getPlayers().values()) {
					RoomSlot slot = getConnection().getRoom().getRoomSlotByPlayer(member);
					member.getConnection().sendPacket(new SM_BATTLE_ROOM_INFO(getConnection().getRoom()));
				}
			}
		});
	}
}