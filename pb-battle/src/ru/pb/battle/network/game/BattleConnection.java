package ru.pb.battle.network.game;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import ru.pb.battle.network.game.packets.BattleClientPacket;
import ru.pb.battle.network.game.packets.BattlePacketHandler;
import ru.pb.battle.network.game.packets.BattleServerPacket;
import ru.pb.battle.network.game.packets.sendable.SM_REQUEST_AUTH_IN_GAMESERVER;
import ru.pb.battle.properties.BattleServerProperty;
import ru.pb.global.network.packets.BaseServerPacket;
import ru.pb.global.network.packets.Connection;
import ru.pb.global.network.packets.PacketProcessor;
import ru.pb.global.utils.NetworkUtil;

import java.nio.ByteOrder;
import java.util.List;

/**
 * Обработчик сообщений между игровым сервером и боевым
 *
 * @author sjke
 */
public class BattleConnection extends Connection {

	private final PacketProcessor<BattleClientPacket> processor = new PacketProcessor<BattleClientPacket>(1, 8);

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		log.info(this + " connected");
		sendPacket(new SM_REQUEST_AUTH_IN_GAMESERVER());
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

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		super.channelUnregistered(ctx);
		log.info(this + " disconnected");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		log.info(this + " disconnecting");
	}

	public boolean read(ByteBuf data) {
		BattleClientPacket packet = BattlePacketHandler.getInstance().handle(data, this);
		if ((packet != null) && packet.read()) {
			log.info(this + " reading packet: " + packet);
			processor.executePacket(packet);
		}
		return true;
	}

	@Override
	public void close(BaseServerPacket packet) {
		log.info(this + " sending disconnecting packet");
		super.close(packet);
	}

	@Override
	public void sendPacket(BaseServerPacket packet) {
		if (packet != null && (packet instanceof BattleServerPacket)) {
			log.info(this + " sending packet: " + packet);
			((BattleServerPacket) packet).writeImpl(this);
		}
	}

	public void showPackageHex(ByteBuf data) {
		if (BattleServerProperty.getInstance().SHOW_BYTE_BUFFER) {
			log.info(NetworkUtil.printData("Packet data", data.copy()));
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("BattleServer [id:").append(getId());
		sb.append(", ip: ").append(getIp()).append(']');
		return sb.toString();
	}
}

