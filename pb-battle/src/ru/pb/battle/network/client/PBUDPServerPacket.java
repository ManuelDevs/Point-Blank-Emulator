package ru.pb.battle.network.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.DatagramPacket;

import java.net.InetSocketAddress;
import java.nio.ByteOrder;

public class PBUDPServerPacket{
	private ByteBuf buff;
	private InetSocketAddress addr;
	public PBUDPServerPacket(int opcode, ByteBuf data, InetSocketAddress recipient) {
		buff = Unpooled.buffer().order(ByteOrder.LITTLE_ENDIAN);
		buff.writeByte(opcode);
		buff.writeBytes(data);
		addr = recipient;
	}
	public DatagramPacket getPacket(){
		return new DatagramPacket(buff, addr);
	}
}
