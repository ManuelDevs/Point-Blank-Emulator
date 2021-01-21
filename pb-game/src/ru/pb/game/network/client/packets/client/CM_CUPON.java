// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 3/3/2014 3:45:36 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CM_CUPON.java

package ru.pb.game.network.client.packets.client;

import ru.pb.game.network.client.packets.ClientPacket;
import ru.pb.game.network.client.packets.server.SM_CUPON;

public class CM_CUPON extends ClientPacket
{

    public CM_CUPON(int opcode)
    {
        super(opcode);
    }

    protected void readImpl()
    {
    }

    protected void runImpl()
    {
        sendPacket(new SM_CUPON());
    }
}