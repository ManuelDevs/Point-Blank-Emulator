package ru.pb.game.network.client.packets.server;


import ru.pb.global.models.Player;
import ru.pb.global.models.Room;
import ru.pb.game.network.client.packets.ServerPacket;
import ru.pb.game.network.client.ClientConnection;

import static java.sql.DriverManager.getConnection;

/**
 * Created by Kirito on 27.02.14.
 */
public class SM_INVITE_ROOM_RETURN extends ServerPacket {


    public SM_INVITE_ROOM_RETURN() {
        super(0xF2D);

    }
    @Override
    public void writeImpl() {
        writeD(0);
    }
}
