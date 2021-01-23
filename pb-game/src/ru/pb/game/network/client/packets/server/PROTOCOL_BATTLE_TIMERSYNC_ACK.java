package ru.pb.game.network.client.packets.server;

import ru.pb.game.network.client.packets.ServerPacket;
import ru.pb.global.models.Room;

public class PROTOCOL_BATTLE_TIMERSYNC_ACK extends ServerPacket
{
	private Room room;

    public PROTOCOL_BATTLE_TIMERSYNC_ACK(Room room)
    {
        super(3371);
        this.room = room;
    }

    private boolean testTime = true;
    
    public void writeImpl()
    {
       writeC(2); // rodada!?
       writeD(testTime ? 30 : room.getTimeLost());
       writeH(room.getSlotFlag(true, false));
    }
}
