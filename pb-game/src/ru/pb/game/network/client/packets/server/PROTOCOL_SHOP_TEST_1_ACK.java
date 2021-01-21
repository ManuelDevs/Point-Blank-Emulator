package ru.pb.game.network.client.packets.server;

import ru.pb.game.network.client.packets.ServerPacket;
import ru.pb.global.models.Good;
import ru.pb.global.utils.NetworkUtil;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: eas
 * Date: 22.11.13
 * Time: 10:44
 * To change this template use File | Settings | File Templates.
 */
public class PROTOCOL_SHOP_TEST_1_ACK extends ServerPacket {
	public PROTOCOL_SHOP_TEST_1_ACK() {
		super(0x22F);
	}

	@Override
	public void writeImpl() {
		writeD(0);
		writeD(0);
		writeD(0);
		writeD(NetworkUtil.UNBUFFERED_STATE);
	}
}
