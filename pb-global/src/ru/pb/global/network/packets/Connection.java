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

package ru.pb.global.network.packets;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.pb.global.utils.IDFactory;
import ru.pb.global.utils.NetworkUtil;

import java.io.IOException;

/**
 * Экземпляр подключения
 *
 * @author sjke, Felixx
 */
public abstract class Connection extends ByteToMessageDecoder {
	// http://stackoverflow.com/questions/14352450/buffer-returned-from-lengthfieldbasedframedecoder-too-small
	protected final Logger log = LoggerFactory.getLogger(getClass());
	private final int id = IDFactory.getInstance().nextId();
	private final int SECURITY_KEY = 29890;
	private final int shift = ((id + SECURITY_KEY) % 7) + 1;
	private Channel channel;
	private String ip;
	private String mac;

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		this.channel = ctx.channel();
		if (channel.remoteAddress() != null) {
			this.ip = NetworkUtil.parseIp(channel.remoteAddress().toString());
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (cause instanceof IOException) {
			ctx.close();
		} else {
			log.error("", cause);
		}
	}

	public Channel getChannel() {
		return channel;
	}

	public String getIp() {
		return ip;
	}

	public byte[] getIPBytes() {
		try {
			return NetworkUtil.parseIpToBytes(ip);
		} catch (Exception e) {
			return new byte[]{1, 0, 0, 127};
		}
	}

	public void close(BaseServerPacket packet) {
		if (packet != null) {
			sendPacket(packet);
		}
		channel.close();
	}

	public int getId() {
		return id;
	}

	public int getSecurityKey() {
		return SECURITY_KEY;
	}

	public int getShift() {
		return shift;
	}

	abstract public void sendPacket(BaseServerPacket packet);

}
