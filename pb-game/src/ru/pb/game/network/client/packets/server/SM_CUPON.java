// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 3/3/2014 3:10:55 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   SM_CUPON.java

package ru.pb.game.network.client.packets.server;

import ru.pb.game.network.client.packets.ServerPacket;
import ru.pb.global.utils.NetworkUtil;

public class SM_CUPON extends ServerPacket
{

    public SM_CUPON()
    {
        super(535);
    }

    public void writeImpl()
    {
        String hex = "01 00 00 00 61 0D 92 53 4C C2 14 00 00 00 00 00 00 00 00 00 01 00 00 00 00";
        writeB(NetworkUtil.hexStringToByteArray(hex));
    }
}
