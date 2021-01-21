package ru.pb.battle.network.game;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Инициализатор каналов
 *
 * @author Felixx
 */
public class BattleChannelInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	public void initChannel(SocketChannel channel) throws Exception {
		channel.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG), new BattleConnection());
	}
}
