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

package ru.pb.game.network.battle;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import ru.pb.game.controller.BattleServerController;
import ru.pb.game.network.battle.packets.BattleClientPacket;
import ru.pb.game.network.battle.packets.BattlePacketHandler;
import ru.pb.game.network.battle.packets.BattleServerPacket;
import ru.pb.game.properties.GameServerProperty;
import ru.pb.global.models.BattleServerInfo;
import ru.pb.global.network.packets.BaseServerPacket;
import ru.pb.global.network.packets.Connection;
import ru.pb.global.network.packets.PacketProcessor;
import ru.pb.global.utils.NetworkUtil;

import java.nio.ByteOrder;
import java.util.List;

/**
 * Обработчик сообщений между игровым сервером и боевым сервером
 *
 * @author sjke
 */
public class FromBattleToGameConnection extends Connection {
	private BattleServerInfo battleServerInfo = null;
	private final PacketProcessor<BattleClientPacket> processor = new PacketProcessor<BattleClientPacket>(1, 8);

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		log.info(this + " connected");
	}

	private static final int lengthHeader = 2;
	private boolean readHeader = true;
	private int size;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		ByteBuf buffer = in.order(ByteOrder.LITTLE_ENDIAN);
		if (readHeader) {
			if (buffer.readableBytes() < lengthHeader) {
				return;
			}
			size = buffer.readUnsignedShort();
			readHeader = false;
		}
		if (buffer.readableBytes() < size + 2) {
			return;
		}
		ByteBuf buf = buffer.readBytes(size + 2);
		read(buf);
		readHeader = true;
	}

	private void read(ByteBuf buffer) {
		BattleClientPacket packet = BattlePacketHandler.getInstance().handle(buffer, this);
		showPackageHex(buffer);
		if ((packet != null) && packet.read()) {
			log.info(this + " reading packet: " + packet);
			packet.setBuf(null);
			processor.executePacket(packet);
		}
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		log.info(this + " disconnected");
		BattleServerController.getInstance().unregisterBattleServer(this, battleServerInfo.getId());
		super.channelUnregistered(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.info(this + " will be disconnected");
		super.exceptionCaught(ctx, cause);
	}


	@Override
	public void close(BaseServerPacket packet) {
		log.debug(this + " sending packet about of the disconnected");
		super.close(packet);
	}

	@Override
	public void sendPacket(BaseServerPacket packet) {
		if (packet != null && (packet instanceof BattleServerPacket)) {
			log.info(this + " sending packet: " + packet + "; capacity: " + packet.getBuf().capacity() + "; writable: " +packet.getBuf().writableBytes());
			((BattleServerPacket) packet).writeImpl(this);
		}
	}

	public void showPackageHex(ByteBuf data) {
		if (GameServerProperty.getInstance().SHOW_BYTE_BUFFER) {
			log.info(NetworkUtil.printData("Packet data", data.copy()));
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("BattleClient [id:").append(getId());
		sb.append(", ip: ").append(getIp()).append(']');
		return sb.toString();
	}

	public BattleServerInfo getBattleServerInfo() {
		return battleServerInfo;
	}

	public void setBattleServerInfo(BattleServerInfo gameServerInfo) {
		this.battleServerInfo = gameServerInfo;
	}
}
