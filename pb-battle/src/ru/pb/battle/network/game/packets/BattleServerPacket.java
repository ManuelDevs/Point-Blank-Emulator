package ru.pb.battle.network.game.packets;

import ru.pb.battle.network.game.BattleConnection;
import ru.pb.global.network.packets.BaseServerPacket;

public abstract class BattleServerPacket extends BaseServerPacket {
	protected BattleConnection con;

	public BattleServerPacket(int opcode) {
		super(opcode);
	}

	public void writeImpl(BattleConnection con) {
		this.con = con;
		writeImpl();
		byte[] data = getBuf().array();
		getBuf().clear();
		getBuf().capacity(data.length + 4);
		writeH(data.length);
		writeH(getOpcode());
		writeB(data);
		con.showPackageHex(getBuf());
		con.getChannel().writeAndFlush(getBuf());
	}

	public abstract void writeImpl();
}
