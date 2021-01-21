package ru.pb.battle.network.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pb.battle.properties.BattleServerProperty;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * UDP сервер для работы с клиентами
 *
 * @author sjke
 */
public class BattleClientServer implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(BattleClientServer.class);
	private static final String host = BattleServerProperty.getInstance().BATTLE_SERVER_HOST;
	private static final int port = BattleServerProperty.getInstance().BATTLE_SERVER_PORT;
	private static final Bootstrap server = new Bootstrap();

	public BattleClientServer() {
		server.group(new NioEventLoopGroup());
		server.channel(NioDatagramChannel.class);
		server.handler(new BattleClientConnection());

	}

	public static void shutdown() {
		server.group().shutdownGracefully();
		log.info("The Battle Server has been shutdown!");
	}

	@Override
	public void run() {
		try {
			InetSocketAddress ad = host.equals("*") ? new InetSocketAddress(port) : new InetSocketAddress(InetAddress.getByName(host), port);
			log.info("Listening to the UDP connections [" + ad + "]");
			server.bind(ad).sync().channel().closeFuture().await();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}
		log.info("UDP Networking is closed!");
	}
}
