package ru.pb.game.network.client.packets.server;

import ru.pb.game.controller.ChannelController;
import ru.pb.game.controller.GameServerController;
import ru.pb.game.network.client.packets.ServerPacket;
import ru.pb.global.models.GameServerInfo;
import ru.pb.global.utils.NetworkUtil;

public class PROTOCOL_SCHANNEL_LIST_ACK extends ServerPacket {

	public PROTOCOL_SCHANNEL_LIST_ACK() {
		super(0x801);
	}

	@Override
	public void writeImpl() {
		writeD(con.getId());
		writeB(con.getIPBytes());
		writeH(con.getSecurityKey());
		writeH(con.getId());
		for(byte i = 0; i < 10; i++)
			writeC(ChannelController.getInstance().getChannel(i).getType().getValue());
		writeC(0);
		writeD(GameServerController.getInstance().getServers().size() + 1);
		writeBool(false);
		writeB(con.getIPBytes());
		writeH(0);
		writeC(5);
		writeH(0);
		writeD(0);
		for (GameServerInfo info : GameServerController.getInstance().getServers()) {
			writeBool(info.getAvailable());
			writeB(NetworkUtil.parseIpToBytes(info.getIp()));
			writeH(info.getPort());
			writeC(info.getType().getValue());
			writeH(info.getMaxPlayer());
			writeD(info.getOnline().get());
		}
	}
}
