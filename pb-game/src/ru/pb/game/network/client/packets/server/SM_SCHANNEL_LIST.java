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

package ru.pb.game.network.client.packets.server;

import ru.pb.game.controller.ChannelController;
import ru.pb.game.controller.GameServerController;
import ru.pb.game.network.client.packets.ServerPacket;
import ru.pb.global.models.Channel;
import ru.pb.global.models.GameServerInfo;
import ru.pb.global.utils.NetworkUtil;

/**
 * Пакет со списком каналов
 */
public class SM_SCHANNEL_LIST extends ServerPacket {

	public SM_SCHANNEL_LIST() {
		super(0x801);
	}

	@Override
	public void writeImpl() {
		writeD(con.getId());
		writeB(con.getIPBytes());
		writeH(con.getSecurityKey());
		writeH(con.getId());
		for(byte i = 0; i < 10; i++){
			writeC(ChannelController.getInstance().getChannel(i).getType().getValue());
		}
		writeC(0); // unk Видимо разделитель
		writeD(GameServerController.getInstance().getServers().size() + 1);
		// Пишем фейковый сервер (Постоянно скрыт)
		writeBool(false); // Доступен для входа или нет
		writeB(con.getIPBytes()); // IP сервера
		writeH(0); // Порт сервера
		writeC(5); // Тип сервера
		writeH(0); // Максимально персов на сервере
		writeD(0); // Сколько персов на сервере
		// Пишем реальные сервера
		for (GameServerInfo info : GameServerController.getInstance().getServers()) {
			writeBool(info.getAvailable()); // Доступен для входа или нет
			writeB(NetworkUtil.parseIpToBytes(info.getIp())); // IP сервера
			writeH(info.getPort()); // Порт сервера
			writeC(info.getType().getValue()); // Тип сервера
			writeH(info.getMaxPlayer()); // Максимально персов на сервере
			writeD(info.getOnline().get()); // Сколько персов на сервере
		}
	}
}
