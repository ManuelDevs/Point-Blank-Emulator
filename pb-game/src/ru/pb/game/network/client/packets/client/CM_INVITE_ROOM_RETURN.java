package ru.pb.game.network.client.packets.client;

import ru.pb.global.enums.SlotState;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;
import ru.pb.global.utils.concurrent.ThreadPoolManager;
import ru.pb.game.network.client.packets.ClientPacket;
import ru.pb.game.network.client.packets.server.SM_INVITE_ROOM_RETURN;
import ru.pb.game.network.client.packets.server.SM_ROOM_INFO;

/**
 * Created by Kirito on 27.02.14.
 */
public class CM_INVITE_ROOM_RETURN extends ClientPacket {

    public CM_INVITE_ROOM_RETURN(int opcode) {
        super(opcode);
    }

    @Override
    public void readImpl() {
    }

    @Override
    public void runImpl() {
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
            sendPacket(new SM_INVITE_ROOM_RETURN());
        }
    }
}
