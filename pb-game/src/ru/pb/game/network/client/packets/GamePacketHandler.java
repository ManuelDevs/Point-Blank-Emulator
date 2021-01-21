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

package ru.pb.game.network.client.packets;

import io.netty.buffer.ByteBuf;
import ru.pb.game.network.client.ClientConnection;
import ru.pb.game.network.client.packets.client.*;
import ru.pb.global.network.packets.PacketHandler;

public class GamePacketHandler extends PacketHandler {

	@SuppressWarnings("unchecked")
	private GamePacketHandler() {
		addPacketPrototype(new PROTOCOL_BASE_ENTER_REQ(0xA13));
		addPacketPrototype(new PROTOCOL_BASE_LEAVE_REQ(0xA11));

		addPacketPrototype(new PROTOCOL_CHANNEL_LIST_REQ(0xA0B));
		addPacketPrototype(new CM_SCHANNEL_LIST(0xA0F));
		addPacketPrototype(new PROTOCOL_CHANNEL_ANNOUNCE_REQ(0xA0D));
		
		addPacketPrototype(new PROTOCOL_LOBBY_ENTER_REQ(0xC07));
		addPacketPrototype(new PROTOCOL_LOBBY_LEAVE_REQ(0xC0B));
		
		addPacketPrototype(new PROTOCOL_LOBBY_ROOM_LIST_REQ(0xC01));
		addPacketPrototype(new PROTOCOL_LOBBY_CREATE_PLAYER_REQ(0xC1D));
		addPacketPrototype(new PROTOCOL_DISCONNECT_REQ(0xA5E));
		
		addPacketPrototype(new PROTOCOL_SHOP_LIST_REQ(0xB05));
		addPacketPrototype(new PROTOCOL_SHOP_ENTER_REQ(0xB03));
		addPacketPrototype(new PROTOCOL_SHOP_LEAVE_REQ(0xB01));
		addPacketPrototype(new PROTOCOL_SHOP_BUY_ITEM_REQ(0x212));

		addPacketPrototype(new PROTOCOL_INVENTORY_ENTER_REQ(0xE01));
		addPacketPrototype(new PROTOCOL_INVENTORY_LEAVE_REQ(0xE05));
		
		/*
		addPacketPrototype(new CM_CHAT_MESSAGE(0xA43));
		addPacketPrototype(new CM_CHAT_CLAN_MESSAGE(0x54E));
		addPacketPrototype(new CM_CHAT_WHISPER_MESSAGE(0x122));
		
		addPacketPrototype(new CM_SHOP_LIST(0xB05));
		addPacketPrototype(new CM_SHOP_ENTER(0xB03));
		addPacketPrototype(new CM_SHOP_LEAVE(0xB01));
		addPacketPrototype(new CM_SHOP_BUY_ITEM(0x212));
		
        addPacketPrototype(new CM_CUPON(0x216));
		addPacketPrototype(new CM_EMPTY_UNK(0xD55));
		addPacketPrototype(new CM_EMPTY_UNK(0xA64));
		
		addPacketPrototype(new CM_CLAN_ENTER(0x5A1));
		addPacketPrototype(new CM_CLAN_LEAVE(0x5A3));
		addPacketPrototype(new CM_CLAN_LIST(0x5A5));
		
		addPacketPrototype(new CM_1451(0x5AB));
		addPacketPrototype(new CM_DISCONNECT(0xA5E));
		
		addPacketPrototype(new CM_LOBBY_ENTER(0xC07));
		addPacketPrototype(new CM_LOBBY_LEAVE(0xC0B));
		addPacketPrototype(new CM_LOBBY_ROOM_LIST(0xC01));
		addPacketPrototype(new CM_LOBBY_CREATE_PLAYER(0xC1D));
		
		addPacketPrototype(new CM_PROFILE_ENTER(0xF16));
		addPacketPrototype(new CM_PROFILE_LEAVE(0xF18));
		addPacketPrototype(new CM_ROOM_CREATE(0xC11));
		addPacketPrototype(new CM_ROOM_INFO(0xC0F));
		addPacketPrototype(new CM_ROOM_CHANGE_TEAM(0xF05));
		addPacketPrototype(new CM_ROOM_CHANGE_HOST(0xF1E));
		addPacketPrototype(new CM_ROOM_HOST_CHANGE_TEAM(0xF22));
		addPacketPrototype(new CM_ROOM_ENTER(0xC09));
		addPacketPrototype(new CM_ROOM_CLOSE_SLOT(0xF09));
		addPacketPrototype(new CM_BATTLE_STARTING(0xF40));
		addPacketPrototype(new CM_BATTLE_READY(0xD03));
		addPacketPrototype(new CM_BATTLE_RPE_START(0xD14));
		addPacketPrototype(new CM_BATTLE_START(0xD05));
		addPacketPrototype(new CM_BATTLE_SLOT_INFO(0xD10));
		addPacketPrototype(new CM_BATTLE_USER_RESPAWN(0xD09));
		addPacketPrototype(new CM_BATTLE_TIMER_SYNC(0xD2C));
		addPacketPrototype(new CM_BATTLE_BOMB_TAB(0xF1E));
		addPacketPrototype(new CM_BATTLE_BOMB_UNTAB(0xF20));
		addPacketPrototype(new CM_ROUND_END(0xF1C));
		addPacketPrototype(new CM_ROUND_START(0xD33));
		addPacketPrototype(new CM_BATTLE_BOT_RESPAWN(0xD32));
		addPacketPrototype(new CM_BATTLE_FRAG_INFO(0xD1A));
		addPacketPrototype(new CM_BATTLE_BOT_CHANGELEVEL(0xD30));
		addPacketPrototype(new CM_BATTLE_FINAL(0xF42));
		addPacketPrototype(new CM_BATTLE_END(0xD38));
		addPacketPrototype(new CM_ROOM_CHANGE_INFO(0xF2E));
		addPacketPrototype(new CM_ROOM_CHANGE_PASSWORD(0xF42));
		addPacketPrototype(new CM_ROOM_CHANGE_SETTINGS(0xF12));
		addPacketPrototype(new CM_UNK_2901(0xB55));
		addPacketPrototype(new CM_ROOM_CHANGE_SETTINGS_PART1(0xF07));
		addPacketPrototype(new CM_CREATE_CLAN_REQ(0x588));
		addPacketPrototype(new CM_CLAN_CREATE_NICK_NAME(0x5A7));
		addPacketPrototype(new CM_CLAN_CREATE(0x51E));
        addPacketPrototype(new CM_BASE_USER_TITLE_GET(0xA3B));
        addPacketPrototype(new CM_BASE_USER_TITLE_USE(0xA3D));
        addPacketPrototype(new CM_BASE_USER_TITLE_DETACH(0xA3F));
		addPacketPrototype(new CM_OUTPOST_ENTER(0xB51));
		addPacketPrototype(new CM_OUTPOST_LEAVE(0xB53));
		addPacketPrototype(new CM_BATTLE_NETWORK_PROBLEM(0xD0F));
		addPacketPrototype(new CM_FRIEND_ADD(0x11A));
		addPacketPrototype(new CM_FRIEND_FIND(0x129));
		addPacketPrototype(new CM_UNK_3342(0xD0E));
		addPacketPrototype(new CM_INVITE_ROOM(0xF0E));
        addPacketPrototype(new CM_INVITE_ROOM_RETURN(0xF2C));*/
		log.info("Loaded " + packetsPrototypes.size() + " client packet prototypes");
	}

	private static final GamePacketHandler INSTANCE = new GamePacketHandler();

	public static GamePacketHandler getInstance() {
		return INSTANCE;
	}

	public ClientPacket handle(ByteBuf buffer, ClientConnection client) {
		int opcode = buffer.readUnsignedShort();
		if (buffer.readableBytes() > 0) {
			buffer.readUnsignedShort();
			return (ClientPacket) getPacket(opcode, buffer, client);
		}
		return null;
	}
}
