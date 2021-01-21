package ru.pb.battle.network.game.packets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pb.battle.network.game.BattleConnection;
import ru.pb.global.network.packets.BaseClientPacket;

/**
 * @author: sjke
 */
public abstract class BattleClientPacket extends BaseClientPacket<BattleClientPacket, BattleConnection> implements Cloneable {
	protected final Logger log = LoggerFactory.getLogger(getClass());

	protected BattleClientPacket(int opcode) {
		super(opcode);
	}

	@Override
	public final void run() {
		try {
			runImpl();
		} catch (Throwable e) {
			log.warn("Error handling gs (" + getConnection().getIp() + "), message " + this, e);
		}
	}

	public BattleClientPacket clonePacket() {
		try {
			return (BattleClientPacket) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
