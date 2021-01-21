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

package ru.pb.auth.network.client;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pb.auth.properties.AuthServerProperty;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import static io.netty.channel.ChannelOption.TCP_NODELAY;

/**
 * Сервер для связи с игроками
 *
 * @author sjke
 */
public class ClientServer implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(ClientServer.class);
	private static final ServerBootstrap server = new ServerBootstrap();

	private static final String host = AuthServerProperty.getInstance().AUTH_CLIENT_HOST;
	private static final int port = AuthServerProperty.getInstance().AUTH_CLIENT_PORT;
	private static final int boss = AuthServerProperty.getInstance().AUTH_CLIENT_COUNT_BOSS_THREADS;
	private static final int work = AuthServerProperty.getInstance().AUTH_CLIENT_COUNT_WORK_THREADS;

	public ClientServer() {
		server.option(TCP_NODELAY, false);
		server.childOption(TCP_NODELAY, false);

		server.group(new NioEventLoopGroup(boss), new NioEventLoopGroup(work));
		server.channel(NioServerSocketChannel.class);
		server.childHandler(new ClientChannelInitializer());
	}

	public static void shutdown() {
		server.group().shutdownGracefully();
		server.childGroup().shutdownGracefully();
		log.info("The server has been shutdown!");
	}

	@Override
	public void run() {
		try {
			InetSocketAddress ad = host.equals("*") ? new InetSocketAddress(port) : new InetSocketAddress(InetAddress.getByName(host), port);
			log.info("Listening to the client connections [" + ad + "]");
			server.bind(ad).sync().channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}
		log.info("Networking is closed!");
	}
}
