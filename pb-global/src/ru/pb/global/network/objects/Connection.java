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

package ru.pb.global.network.objects;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.pb.global.utils.NetworkUtil;

import java.io.IOException;

/**
 * Коннектор для серверов
 *
 * @author sjke
 */
public abstract class Connection extends ChannelInboundHandlerAdapter {

	protected ObjectProcessor processor = new ObjectProcessor(this, 1, 8);

	public Connection(ObjectHandler handler) {
		processor.setHandler(handler);
	}

	protected final Logger log = LoggerFactory.getLogger(getClass());
	private Channel channel;
	private String ip;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		this.channel = ctx.channel();
		if (channel.remoteAddress() != null) {
			this.ip = NetworkUtil.parseIp(channel.remoteAddress().toString());
		}
		log.info(this + " connected");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
		try {
			readObject(obj);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ReferenceCountUtil.release(obj);
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
		log.info(this + " disconnecting");
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		super.channelUnregistered(ctx);
		log.info(this + " disconnected");
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

	public boolean readObject(Object obj) {
		processor.executeObject(obj);
		return true;
	}

	public void sendObject(ObjectNetwork object) {
		if (object == null) {
			return;
		}
		log.debug(this + " sending object: " + (log.isDebugEnabled() ? object : object.getClass().getSimpleName()) + " to addres: " + getChannel().remoteAddress());
		channel.writeAndFlush(object);
	}

	@Override
	public String toString() {
		return getClass().toString();
	}

}
