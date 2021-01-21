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
 * Copyright (C) 2013 PBDev™
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
import ru.pb.game.network.client.packets.server.SM_BATTLE_CHANGE_NETWORK_INFO;
import ru.pb.game.network.client.packets.server.SM_BATTLE_LEAVE;
import ru.pb.game.network.client.packets.server.SM_ROOM_INFO;
import ru.pb.game.network.client.packets.server.SM_SCHANNEL_LIST;
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
 * Обработчик сообщений между игровым сервером и сервером авторизации
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
		sendPacket(new SM_SCHANNEL_LIST());
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
				RoomSlot sl = room.getRoomSlotByPlayer(player);
				if(sl == null) {
					log.info("SLOT is NULL for player: " + player.getName());
					return;
				}
				int slot = sl.getId();
				// если игрок-лидер вылетает из комнаты, то меняем лидера
				if(player.equals(room.getLeader()))
					room.setNewLeader();

				BattleServerInfo bsi = BattleServerController.getInstance().getBattleServerInfo(40000);
				if(bsi != null) {
					if(bsi.getConnection() != null) {
						// удаляем из боевого сервера отключающегося игрока
						bsi.getConnection().sendPacket(new SM_REQUEST_REMOVE_PLAYER(room, player.getConnection().getIPBytes(), ch.getId()));
						// обновляем инфу о лидере комнаты
						bsi.getConnection().sendPacket(new SM_REQUEST_CHANGE_HOST(room, ch.getId()));
					}
				}

				room.removePlayer(player);

				// фикс удаления игрока из комнаты -- TODO Test
				for(Player member : room.getPlayers().values()) {
					if(member.getConnection() != null)
						member.getConnection().sendPacket(new SM_ROOM_INFO(room));

					if(room.getRoomSlotByPlayer(member).getState().ordinal() > 8)
						if(member.getConnection() != null)
							member.getConnection().sendPacket(new SM_BATTLE_LEAVE(slot));

					if(player.equals(room.getLeader())) {
						member.getConnection().sendPacket(new SM_BATTLE_CHANGE_NETWORK_INFO(room));
					}
				}
				ch.removeRoom(room);
			}

			if(ch != null && ch.getPlayers().containsKey(player.getId()))
				ch.removePlayer(player);
		}
		log.info(this + " disconnected: " + getAccount().getLogin() + "; address: " + getChannel().remoteAddress());
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
			log.debug(this + " reading packet: " + packet);
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
			log.debug(this + " sending packet: " + packet + "; capacity: " + packet.getBuf().capacity() + "; writable: " + packet.getBuf().writableBytes());
			((ServerPacket) packet).writeImpl(this);
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
