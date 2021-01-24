using PointBlank.Data.Communication;
using System;

namespace PointBlank.Game.Communication.Packets.ServerPackets.Shop
{
    class PROTOCOL_SHOP_ENTER_ACK : OutgoingPacket
    {
        public PROTOCOL_SHOP_ENTER_ACK() : base(2820)
        {
            WriteInt(uint.Parse(DateTime.Now.ToString("yyMMddHHmm")));
        }
    }
}
