package ru.pb.game.network.battle.packets;

import io.netty.buffer.ByteBuf;
import ru.pb.game.network.battle.FromBattleToGameConnection;
import ru.pb.game.network.battle.packets.receivable.CM_REQUEST_AUTH_BATTLE_SERVER;
import ru.pb.global.network.packets.PacketHandler;

public class BattlePacketHandler extends PacketHandler {
	public BattlePacketHandler() {
		addPacketPrototype(new CM_REQUEST_AUTH_BATTLE_SERVER(0x00));
		log.info("Loaded " + packetsPrototypes.size() + " battle packet prototypes");
	}

	private static final BattlePacketHandler INSTANCE = new BattlePacketHandler();

	public static BattlePacketHandler getInstance() {
		return INSTANCE;
	}

	public BattleClientPacket handle(ByteBuf buffer, FromBattleToGameConnection client) {
		int opcode = buffer.readUnsignedShort();
		if (buffer.readableBytes() > 0) {
			return (BattleClientPacket) getPacket(opcode, buffer, client);
		}
		return null;
	}
}
