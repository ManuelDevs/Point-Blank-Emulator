package ru.pb.battle.network.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pb.battle.controllers.RoomController;
import ru.pb.battle.models.Player;
import ru.pb.battle.models.Room;
import ru.pb.battle.utils.ByteBuffer;

import java.net.InetSocketAddress;

/**
 * @author DarkSkeleton
 */
public class BattleClientConnection extends SimpleChannelInboundHandler<DatagramPacket>
{

private static final Logger log = LoggerFactory.getLogger(BattleClientConnection.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
		ByteBuf bb = msg.content();
		byte opcode = bb.readByte();
		switch (opcode)
		{
			case 65:
			{
				log.info("Try connect player " + msg.sender().getAddress() + ":" + msg.sender().getPort());
				Room room = RoomController.getInstance().getRoom(msg.sender().getAddress());
				if(room != null)
				{
					if(room.inetAddress.equals(msg.sender().getAddress()))
					{
						ByteBuffer _buffer = new ByteBuffer();
						_buffer.writeC((byte)66);
						_buffer.writeC((byte) 0);
						_buffer.writeB(new byte[5]);
						_buffer.writeC((byte) 0x0b);
						_buffer.writeB(new byte[3]);
						room.isConnected = true;
						room.port = msg.sender().getPort();
						log.info("Send accept data! " + msg.sender().getAddress().toString() + ":" + msg.sender().getPort());
						ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(_buffer.getData(), 0, _buffer.getData().length), msg.sender()));
					}
					else
					{
						Player player = RoomController.getInstance().getPlayer(msg.sender().getAddress());
						if (player != null)
						{
							ByteBuffer _buffer = new ByteBuffer();
							_buffer.writeC((byte)66);
							_buffer.writeC((byte) 0);
							_buffer.writeB(new byte[5]);
							_buffer.writeC((byte) 0x0b);
							_buffer.writeB(new byte[3]);
							player.isConnected = true;
							player.playerPort = msg.sender().getPort();
							log.info("Send accept data! " + msg.sender().getAddress().toString() + ":" + msg.sender().getPort());
							ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(_buffer.getData(), 0, _buffer.getData().length), msg.sender()));
						}
					}
				}
				else log.info("Room is null");
			break;
			}
			case (byte)131:
			case (byte)132:
			case 84:
			case 97:
			case 4:
			case 3:
			{
				//04:00:a1:18:1c:41:00:5b

				//ByteBuffer _buffer = new ByteBuffer();
				//_buffer.writeB(NetworkUtil.hexStringToByteArray("04 ff cd cc 78 41 01 40 00 00 00 00 00 02 e0 00 c0 00 00 1e 03 e5 b9 94 89 2c 69 c2 e8 d0 88 61 a0 01 21 60 00 c0 00 01 21 80 00 c0 00 01 21 a0 00 c0 00 01 21 c0 00 c0 00 01 21 e0 00 c0 00 00"));

				//_buffer.writeB(NetworkUtil.hexStringToByteArray("" +
				//		"04 00 " +
				//		"99 c4 a0 3f 00 " +
				//		"2d 00 " +
				//		"00 2c 00 00 02 20 04 f0 " +
				//		"00 00 00 00 8a 00 0f 01 fc 00 " +
				//		"" +
				//		" f2 e5"  + //ÐºÐ¾Ð¾Ñ€Ð´Ð¸Ð½Ð°Ñ‚Ñ‹ Ñ�Ð¿Ð°Ð²Ð½Ð° Ñ‡Ñ‚Ð¾ Ð»Ð¸
				//		" 01 00" + //z
				//		" 00 00 00 00 00 00 00 00 00 00 00 00 00 00"));
				//_buffer.writeC((byte)4);
				//_buffer.writeC((byte) 0x00);
				//_buffer.writeC((byte) 0xa1);
				//_buffer.writeC((byte) 0x18);
				//_buffer.writeC((byte) 0x1c);
				//_buffer.writeC((byte) 0x41);
				//_buffer.writeC((byte) 0x00);
				//_buffer.writeC((byte) 17);  //Ð´Ð»Ð¸Ð½Ð° Ð¿Ð°ÐºÐµÑ‚Ð°
				//_buffer.writeC((byte) 0x00);  //Ð´Ð»Ð¸Ð½Ð° Ð¿Ð°ÐºÐµÑ‚Ð°
				//_buffer.writeC((byte) 0x00);
				//_buffer.writeC((byte) 0x00);
				//_buffer.writeC((byte) 0x00);
				//_buffer.writeC((byte) 0x00);
				//_buffer.writeC((byte) 0x00);
				//_buffer.writeC((byte) 0x70);
				//_buffer.writeC((byte) 0x00);
				//_buffer.writeC((byte) 0x00);
				//ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(_buffer.getData(), 0, _buffer.getData().length), msg.sender()));

				Room room = RoomController.getInstance().getRoom(msg.sender().getAddress());
				if(room != null)
				{
					if(room.inetAddress.equals(msg.sender().getAddress()))
					{
						for (Player player : room.getPlayers().values())
						{
							if(player.isActive)
							{
								if(player.playerPort > 0)
								{
									ctx.writeAndFlush(new PBUDPServerPacket(opcode, Unpooled.copiedBuffer(msg.content()), new InetSocketAddress(player.playerIP, player.playerPort)).getPacket());
									//ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(byteBuffer.getData()), new InetSocketAddress(player.playerIP, player.playerPort)));
								}
								//else
									//log.info("[Warning]: " + "Port == 0");
							}
						}
					}
					else
					{
						if(room.isActive)
						{
							if(room.port > 0)
							{
								ctx.writeAndFlush(new PBUDPServerPacket(opcode, Unpooled.copiedBuffer(msg.content()), new InetSocketAddress(room.inetAddress, room.port)).getPacket());
							}
							//else
							//{
								//log.info("[Warning]: " + "Host Port == 0");
							//}
						}
					}
				}
				break;
			}
			case 67:
			{
				Player player = RoomController.getInstance().getPlayer(msg.sender().getAddress());
				if(player != null)
				{
					player.isActive = false;
					player.isConnected = false;
				}
				break;
			}

		}
		//ReferenceCountUtil.release(msg);
	}

	@Override
	public void exceptionCaught(
			ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		// We don't close the channel because we can keep serving requests.
	}
}
