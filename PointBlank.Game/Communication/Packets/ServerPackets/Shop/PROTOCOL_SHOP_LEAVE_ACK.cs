using PointBlank.Data.Communication;

namespace PointBlank.Game.Communication.Packets.ServerPackets.Shop
{
    class PROTOCOL_SHOP_LEAVE_ACK : OutgoingPacket
    {
        public PROTOCOL_SHOP_LEAVE_ACK(int Type) : base(2818)
        {
            WriteInt(Type);
            WriteInt(0);
        }
    }
}
