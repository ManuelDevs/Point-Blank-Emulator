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

/**
 * ÐžÐ±Ñ€Ð°Ð±Ð¾Ñ‚Ñ‡Ð¸Ðº Ð¿Ð°ÐºÐµÑ‚Ð¾Ð² Ð¾Ñ‚ Ð³ÐµÐ¹Ð¼ Ñ�ÐµÑ€Ð²ÐµÑ€Ð°
 *
 * @author sjke
 */
public class AuthPacketHandler extends PacketHandler {

	private AuthPacketHandler() {
		/*addPacketPrototype(new CM_LOGIN(0xA01));
		addPacketPrototype(new CM_ACCOUNT_INFO(0xA05));
		addPacketPrototype(new CM_MY_FRIENDS_INFO(0xA07));
		addPacketPrototype(new CM_UNK_2661(0xA65));
		addPacketPrototype(new CM_FREE_ITEMS_OF_LEVELUP(0xA6C));
		addPacketPrototype(new CM_LEAVE(0xA11));
		addPacketPrototype(new CM_DISCONNECT(0xA5E));
		addPacketPrototype(new CM_UPDATE_GAMESERVERS(0xA0F));*/
		addPacketPrototype(new PROTOCOL_BASE_LOGIN_REQ(2561));
		addPacketPrototype(new PROTOCOL_BASE_DISCONNECT_REQ(2654));
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
