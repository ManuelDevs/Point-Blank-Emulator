// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 3/3/2014 3:46:08 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   SM_FRIEND_FIND.java

package ru.pb.game.network.client.packets.server;

import ru.pb.game.network.client.packets.ServerPacket;

public class SM_FRIEND_FIND extends ServerPacket
{

    public SM_FRIEND_FIND()
    {
        super(298);
    }

    public void writeImpl()
    {
        writeB(new byte[] {
            4, 24, 0, -128
        });
    }
}