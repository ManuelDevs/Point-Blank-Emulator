// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 3/3/2014 3:10:23 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   SM_BATTLE_MISSION_COMPLETE.java

package ru.pb.game.network.client.packets.server;

import ru.pb.game.network.client.packets.ServerPacket;

public class SM_BATTLE_MISSION_COMPLETE extends ServerPacket
{

    public SM_BATTLE_MISSION_COMPLETE(int mission)
    {
        super(2600);
        this.mission = mission;
    }

    public void writeImpl()
    {
        if(mission == 1)
        {
            writeC(240);
            writeC(1);
        } else
        if(mission == 2)
        {
            writeC(241);
            writeC(1);
        } else
        if(mission == 3)
        {
            writeC(242);
            writeC(1);
        } else
        if(mission == 4)
        {
            writeC(243);
            writeC(1);
        }
    }

    private int mission;
}
