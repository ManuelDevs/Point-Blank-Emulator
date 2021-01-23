/*
 * Java Server Emulator Project Blackout / PointBlank
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * Authors: DarkSkeleton, sjke, Felixx
 * Copyright (C) 2013 PBDevâ„¢
 */

package ru.pb.game.network.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.nio.ByteOrder;
import java.util.List;

import ru.pb.game.controller.BattleServerController;
import ru.pb.game.network.battle.packets.sendable.SM_REQUEST_CHANGE_HOST;
import ru.pb.game.network.battle.packets.sendable.SM_REQUEST_REMOVE_PLAYER;
import ru.pb.game.network.client.packets.ClientPacket;
import ru.pb.game.network.client.packets.GamePacketHandler;
import ru.pb.game.network.client.packets.ServerPacket;
import ru.pb.game.network.client.packets.server.PROTOCOL_SCHANNEL_LIST_ACK;
import ru.pb.game.network.client.packets.server.PROTOCOL_ROOM_SLOT_INFO_ACK;
import ru.pb.game.properties.GameServerProperty;
import ru.pb.global.models.Account;
import ru.pb.global.models.BattleServerInfo;
import ru.pb.global.models.Channel;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;
import ru.pb.global.models.RoomSlot;
import ru.pb.global.network.packets.BaseServerPacket;
import ru.pb.global.network.packets.Connection;
import ru.pb.global.network.packets.PacketProcessor;
import ru.pb.global.service.PlayerDaoService;
import ru.pb.global.utils.NetworkUtil;

/**
 * ÐžÐ±Ñ€Ð°Ð±Ð¾Ñ‚Ñ‡Ð¸Ðº Ñ�Ð¾Ð¾Ð±Ñ‰ÐµÐ½Ð¸Ð¹ Ð¼ÐµÐ¶Ð´Ñƒ Ð¸Ð³Ñ€Ð¾Ð²Ñ‹Ð¼ Ñ�ÐµÑ€Ð²ÐµÑ€Ð¾Ð¼ Ð¸ Ñ�ÐµÑ€Ð²ÐµÑ€Ð¾Ð¼ Ð°Ð²Ñ‚Ð¾Ñ€Ð¸Ð·Ð°Ñ†Ð¸Ð¸
 * 
 * @author sjke
 */
public class ClientConnection extends Connection {

	private final PacketProcessor<ClientPacket> processor = new PacketProcessor<ClientPacket>(1, 8);
	private Account account;
	private Player player;
	private Channel serverChannel;
	private Room room;

	private static final int lengthHeader = 4;
	private boolean readHeader = true;
	private boolean doDecrypt = false;
	private int size;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		log.info(this + " connected" + "; address: " + getChannel().remoteAddress());
		sendPacket(new PROTOCOL_SCHANNEL_LIST_ACK());
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		ByteBuf buffer = in.order(ByteOrder.LITTLE_ENDIAN);
		if(readHeader) {
			if(buffer.readableBytes() < lengthHeader) {
				return;
			}
			size = buffer.readUnsignedShort();
			if(size > 0x22CC) {
				size = size & 0x7FFF;
				doDecrypt = true;
			}
			readHeader = false;
		}
		if(buffer.readableBytes() < size + 2) {
			return;
		}
		ByteBuf buf = buffer.readBytes(size + 2);
		if(doDecrypt) {
			decrypt(buf);
		}
		read(buf);
		readHeader = true;
		doDecrypt = false;
	}

	protected void decrypt(ByteBuf buf) {
		byte[] data = buf.array();
		byte lastElement = data[data.length - 1];
		for(int i = data.length - 1; i > 0; i--) {
			data[i] = (byte) (((data[i - 1] & 0xFF) << (8 - getShift())) | ((data[i] & 0xFF) >> getShift()));
		}
		data[0] = (byte) ((lastElement << (8 - getShift())) | ((data[0] & 0xFF) >> getShift()));
		log.debug(NetworkUtil.printData("Decoded [shift: " + getShift() + "]", buf));
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		if(getAccount() == null || getChannel() == null) {
			super.channelUnregistered(ctx);
			return;
		}

		if(player != null) {
			PlayerDaoService.getInstance().update(player);

			Room room = getRoom();
			Channel ch = getServerChannel();

			if(room != null && player != null && ch != null) {
				// TODO: remove from room
			}

			if(ch != null && ch.getPlayers().containsKey(player.getId()))
				ch.removePlayer(player);
		}
		super.channelUnregistered(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.info(this + " will be force disconnected: " + getAccount().getLogin() + "; address: " + getChannel().remoteAddress());
		super.exceptionCaught(ctx, cause);
	}

	private void read(ByteBuf buffer) {
		ClientPacket packet = GamePacketHandler.getInstance().handle(buffer, this);
		showPackageHex(buffer);
		if((packet != null) && packet.read()) {
			packet.setBuf(null);
			processor.executePacket(packet);
		}
	}

	@Override
	public void close(BaseServerPacket packet) {
		log.debug(this + " sending packet about of the disconnected");
		super.close(packet);
	}

	@Override
	public void sendPacket(BaseServerPacket packet) {
		if(packet != null && (packet instanceof ServerPacket)) {
			((ServerPacket) packet).writeImpl(this);
			log.info("Sending packet " + packet);
		}
	}

	public void showPackageHex(ByteBuf data) {
		if(GameServerProperty.getInstance().SHOW_BYTE_BUFFER) {
			log.info(NetworkUtil.printData("Packet data", data.copy()));
		}
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
		player.setConnection(this);
	}

	public Channel getServerChannel() {
		return serverChannel;
	}

	public void setServerChannel(Channel serverChannel) {
		this.serverChannel = serverChannel;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Client [id:").append(getId());
		sb.append(", ip: ").append(getIp()).append(']');
		return sb.toString();
	}
}
