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

package ru.pb.game.network.auth;

import static io.netty.channel.ChannelOption.TCP_NODELAY;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.pb.game.network.client.ClientServer;
import ru.pb.game.properties.GameServerProperty;

/**
 * Клиент для связи с сервером авторизации
 *
 * @author sjke
 */
public class AuthClient implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(AuthClient.class);
	private static final String host = GameServerProperty.getInstance().AUTH_SERVER_HOST;
	private static final int port = GameServerProperty.getInstance().AUTH_SERVER_PORT;
	private static InetSocketAddress ad = null;
	private static final Bootstrap client = new Bootstrap();

	public static boolean authFailed = false;

	public AuthClient() {
		client.option(TCP_NODELAY, false);

		client.group(new NioEventLoopGroup());
		client.channel(NioSocketChannel.class);
		client.handler(new AuthChannelInitializer());
	}

	public static void shutdown() {
		client.group().shutdownGracefully();
		log.info("The Client has been shutdown!");
	}

	@Override
	public void run() {
		while( !authFailed) {
			try {
				ad = host.equals("*") ? new InetSocketAddress(port) : new InetSocketAddress(InetAddress.getByName(host), port);
				log.info("Connecting to " + ad + "...");
				client.connect(ad).sync().channel().closeFuture().sync();
				// connect = true;
			} catch(Throwable e) {
				log.info("Server [" + ad + "] not available...");
			}
			try {
				Thread.sleep(10000);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			if(authFailed) {
				shutdown();
				ClientServer.shutdown();
			}
		}
	}
}
