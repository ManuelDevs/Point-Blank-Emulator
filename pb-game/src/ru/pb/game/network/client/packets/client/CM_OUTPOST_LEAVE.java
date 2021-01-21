package ru.pb.game.network.client.packets.client;

import ru.pb.game.network.client.packets.ClientPacket;
import ru.pb.game.network.client.packets.server.SM_OUTPOST_LEAVE;
import ru.pb.game.network.client.packets.server.SM_ROOM_INFO;
import ru.pb.global.enums.SlotState;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;
import ru.pb.global.utils.concurrent.ThreadPoolManager;

/**
 * Пакет выхода из OUTPOST
 *
 * @author: DarkSkeleton
 */
public class CM_OUTPOST_LEAVE extends ClientPacket {
	public CM_OUTPOST_LEAVE(int opcode) {
		super(opcode);
	}

	@Override
	protected void readImpl() {

	}

	@Override
	protected void runImpl() {
		if (getConnection().getPlayer() != null) {

			final Room room = getConnection().getRoom();

			if (room != null) {
				room.getRoomSlotByPlayer(getConnection().getPlayer()).setState(SlotState.SLOT_STATE_NORMAL);
				ThreadPoolManager.getInstance().executeTask(new Runnable() {
					@Override
					public void run() {
						for (Player member : getConnection().getRoom().getPlayers().values()) {
							member.getConnection().sendPacket(new SM_ROOM_INFO(room));
						}
					}
				});
			}

			sendPacket(new SM_OUTPOST_LEAVE());
		}
	}
}
