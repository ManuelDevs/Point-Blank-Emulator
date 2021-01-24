using PointBlank.Data.Communication;
using System;

namespace PointBlank.Game.Communication.Packets.ServerPackets.Inventory
{
    class PROTOCOL_INVENTORY_ENTER_ACK : OutgoingPacket
    {
        public PROTOCOL_INVENTORY_ENTER_ACK() : base(3586)
        {
            WriteInt(uint.Parse(DateTime.Now.ToString("yyMMddHHmm")));
        }
    }
}
