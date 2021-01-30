using PointBlank.Data.Communication;
using System;

namespace PointBlank.Game.Communication.Packets.ServerPackets.Shop
{
    class PROTOCOL_SHOP_LIST_ACK : OutgoingPacket
    {
        public PROTOCOL_SHOP_LIST_ACK() : base(2822)
        {
            WriteInt(uint.Parse(DateTime.Now.ToString("yyMMddHHmm")));
        }
    }
}
