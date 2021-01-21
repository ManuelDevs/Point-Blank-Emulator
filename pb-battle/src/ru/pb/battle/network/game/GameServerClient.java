package ru.pb.battle.network.game;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pb.battle.network.client.BattleClientServer;
import ru.pb.battle.properties.BattleServerProperty;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import static io.netty.channel.ChannelOption.SO_BROADCAST;
import static io.netty.channel.ChannelOption.TCP_NODELAY;

/**
 * Клиент для связи с игровым сервером
 *
 * @author Felixx
 */
public class GameServerClient implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(GameServerClient.class);
	private static final String host = BattleServerProperty.getInstance().GAME_SERVER_HOST;
	private static final int port = BattleServerProperty.getInstance().GAME_SERVER_PORT;
	private static InetSocketAddress ad = null;
	private static final Bootstrap client = new Bootstrap();

	public static boolean authFailed = false;

	public GameServerClient() {
		client.option(TCP_NODELAY, false);

		client.group(new NioEventLoopGroup());
		client.channel(NioSocketChannel.class);
		client.handler(new BattleChannelInitializer());
	}

	public static void shutdown() {
		client.group().shutdownGracefully();
		log.info("The GameServerClient has been shutdown!");
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
				BattleClientServer.shutdown();
			}
		}
	}
}
