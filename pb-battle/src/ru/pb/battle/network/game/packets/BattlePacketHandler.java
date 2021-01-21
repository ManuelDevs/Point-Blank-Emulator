package ru.pb.battle.network.game.packets;

import io.netty.buffer.ByteBuf;
import ru.pb.battle.network.game.BattleConnection;
import ru.pb.battle.network.game.packets.receivable.*;
import ru.pb.global.network.packets.PacketHandler;

/**
 * Обработчик пакетов от гейм сервера
 *
 * @author Felixx
 */
public class BattlePacketHandler extends PacketHandler {

	public BattlePacketHandler() {
		addPacketPrototype(new CM_REQUEST_AUTH_IN_GAMESERVER(0x01));
		addPacketPrototype(new CM_REQUEST_REGISTER_ROOM(0x10));
		addPacketPrototype(new CM_REQUEST_UNREGISTER_ROOM(0x11));
		addPacketPrototype(new CM_REQUEST_ADD_PLAYER(0x12));
		addPacketPrototype(new CM_REQUEST_REMOVE_PLAYER(0x13));
		addPacketPrototype(new CM_REQUEST_CHANGE_HOST(0x14));
		addPacketPrototype(new CM_REQUEST_READY_BATTLE_ROOM(0x15));
		log.info("Loaded " + packetsPrototypes.size() + " battle packet prototypes");
	}

	private static final BattlePacketHandler INSTANCE = new BattlePacketHandler();

	public static BattlePacketHandler getInstance() {
		return INSTANCE;
	}

	public BattleClientPacket handle(ByteBuf buffer, BattleConnection client) {
		int opcode = buffer.readUnsignedShort();
		if (buffer.readableBytes() > 0) {
			return (BattleClientPacket) getPacket(opcode, buffer, client);
		}
		return null;
	}
}
