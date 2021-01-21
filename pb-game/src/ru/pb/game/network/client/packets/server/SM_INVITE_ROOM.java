package ru.pb.game.network.client.packets.server;

import ru.pb.global.models.Channel;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;
import ru.pb.game.network.client.packets.ServerPacket;

/**
 * Created by Kirito on 26.02.14.
 */
public class SM_INVITE_ROOM extends ServerPacket {

    private final Channel channel;

    public SM_INVITE_ROOM(Channel channel) {
    //public SM_INVITE_ROOM() {
        super(0xF0F);
        this.channel = channel;

    }
    @Override
    public void writeImpl() {
      /*  //writeD(0);
        writeD(channel.getPlayers().size());
        //writeD(0);
        writeD(channel.getPlayers().size());*/
        //writeD(channel.getPlayers().size());
        //writeD(player.getRank().size(53));

        //int i = 0;
        //i = 0;*/
        for (Player player : channel.getPlayers().values()) {
            if (player != null) {
           /*     //i++;
                //writeD(i);
                writeS(player.getName());
                writeD(0);
                writeC(player.getRank());
            }*/
        //writeB(new byte[] {0x01, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00, 0x02, 0x00, 0x00, 0x00, 0x07, 0x52, 0x65, 0x65, 0x6b, 0x6f, 0x6e, 0x00});
        writeD(1);
        //writeD(8);
        writeD(player.getRank());
        //writeC(player.getRank());
                writeS(player.getName());
        }
    }
}
}
