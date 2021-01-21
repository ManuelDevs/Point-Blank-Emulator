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

import ru.pb.game.network.client.packets.ClientPacket;
import ru.pb.game.network.client.packets.server.SM_BATTLE_BOMB_TAB;
import ru.pb.game.network.client.packets.server.SM_BATTLE_END;
import ru.pb.game.network.client.packets.server.SM_BATTLE_FINAL;
import ru.pb.game.network.client.packets.server.SM_BATTLE_ROUND_END;
import ru.pb.global.models.Player;
import ru.pb.global.utils.concurrent.ThreadPoolManager;

/**
 * Неизветный пакет   TODO: Delete???
 *
 * @author sjke
 */
public class CM_ROUND_END extends ClientPacket {

	public CM_ROUND_END(int opcode) {
		super(opcode);
	}

	@Override
	public void readImpl() {
	}

	@Override
	public void runImpl() {
		ThreadPoolManager.getInstance().executeTask(new Runnable() {
			@Override
			public void run() {
				for (Player member : getConnection().getRoom().getPlayers().values()) {
					member.getConnection().sendPacket(new SM_BATTLE_ROUND_END());
				}
			}
		});
	}	
}