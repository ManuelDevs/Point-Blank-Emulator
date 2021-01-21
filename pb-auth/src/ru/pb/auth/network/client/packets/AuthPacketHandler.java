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
 * Copyright (C) 2013 PBDevâ„¢
 */

package ru.pb.auth.network.client.packets;

import io.netty.buffer.ByteBuf;
import ru.pb.auth.network.client.ClientConnection;
import ru.pb.auth.network.client.packets.client.*;
import ru.pb.global.network.packets.PacketHandler;

public class AuthPacketHandler extends PacketHandler {

	@SuppressWarnings("unchecked")
	private AuthPacketHandler() {
		addPacketPrototype(new PROTOCOL_BASE_LOGIN_REQ(2561));
		addPacketPrototype(new PROTOCOL_BASE_DISCONNECT_REQ(2654));
		addPacketPrototype(new PROTOCOL_BASE_USER_INFO_REQ(2565));
		addPacketPrototype(new PROTOCOL_BASE_USER_INVENTORY_REQ(2698));
		addPacketPrototype(new PROTOCOL_BASE_USER_CONFIGS_REQ(2567));
		addPacketPrototype(new PROTOCOL_BASE_RANK_AWARDS_REQ(2666));
		addPacketPrototype(new PROTOCOL_BASE_SERVER_CHANGE_REQ(2577));
		addPacketPrototype(new PROTOCOL_UPDATE_GAMESERVERS_REQ(2575));
		addPacketPrototype(new PROTOCOL_UNK_2678_REQ(2678));
		log.info("Loaded " + packetsPrototypes.size() + " client packet prototypes");
	}

	private static final AuthPacketHandler INSTANCE = new AuthPacketHandler();

	public static AuthPacketHandler getInstance() {
		return INSTANCE;
	}

	public synchronized ClientPacket handle(ByteBuf buffer, ClientConnection client) {
		int opcode = buffer.readUnsignedShort();
		buffer.readUnsignedShort();
		return (ClientPacket) getPacket(opcode, buffer, client);
	}

}
