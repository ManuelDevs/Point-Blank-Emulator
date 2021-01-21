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
import ru.pb.game.network.battle.packets.sendable.SM_REQUEST_REGISTER_ROOM;
import ru.pb.game.network.client.packets.ClientPacket;
import ru.pb.game.network.client.packets.server.SM_HACKSHIELD;
import ru.pb.game.network.client.packets.server.SM_ROOM_CREATE;
import ru.pb.game.tasks.GRoomTask;
import ru.pb.global.models.BattleServerInfo;
import ru.pb.global.models.Channel;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;

/**
 * Неизветный пакет
 *
 * @author sjke
 */
public class CM_ROOM_CREATE extends ClientPacket {

	private Room room;

	public CM_ROOM_CREATE(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
		room = new Room();
		getConnection().setRoom(null);
		if (getConnection() != null) {
			for (int id = 0; id < Channel.MAX_ROOMS_COUNT; id++) {
				if (!getConnection().getServerChannel().getRooms().containsKey(id)) {
					room.setId(id);
					break;
				}
			}
		}
		readD(); // тут всегда 0
		room.setName(readS(Room.ROOM_NAME_SIZE).trim());
		room.setMapId(readC());
		readC(); // unk
		room.setStage4v4(readC());
		room.setType(readC());
		readC(); // unk
		readC(); // unk
		room.setSlots(readC());
		readC(); // unk
		room.setAllWeapons(readC());
		room.setRandomMap(readC());
		room.setSpecial(readC());
		readS(Player.MAX_NAME_SIZE);
		room.setKillMask(readC());
		readC(); // unk
		readC(); // unk
		readC(); // unk
		room.setLimit(readC());
		room.setSeeConf(readC());
		room.setAutobalans(readH());
		room.setPassword(readS(4));
		room.setLeader(getConnection().getPlayer());
		room.addPlayer(getConnection().getPlayer());
		room.setChannelId(getConnection().getServerChannel().getId());
		if (getBuf().readableBytes() > 0) {
			room.setAiCount(readC());
			room.setAiLevel(readC());
		}
	}

	@Override
	public void runImpl() {
		Player player = getConnection().getPlayer();
		Channel ch = getConnection().getServerChannel();

		ch.removePlayer(player);
		ch.addRoom(room);
		getConnection().setRoom(room);

		BattleServerInfo bsi = BattleServerController.getInstance().getBattleServerInfo(40000);
		if (bsi != null) {
			if (bsi.getConnection() != null) {
				//Создаем комнату на боевом сервере с одним лишь лидером...
				bsi.getConnection().sendPacket(new SM_REQUEST_REGISTER_ROOM(getConnection().getRoom(), getConnection().getServerChannel().getId()));
			}
		}

		room.startTask(new GRoomTask());

		sendPacket(new SM_ROOM_CREATE(room));
		sendPacket(new SM_HACKSHIELD());
	}
}