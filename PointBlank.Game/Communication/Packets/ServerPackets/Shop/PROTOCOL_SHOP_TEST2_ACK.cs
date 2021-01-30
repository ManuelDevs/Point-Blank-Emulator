using PointBlank.Data.Communication;

namespace PointBlank.Game.Communication.Packets.ServerPackets.Shop
{
    class PROTOCOL_SHOP_TEST2_ACK : OutgoingPacket
    {
        public PROTOCOL_SHOP_TEST2_ACK() : base(567)
        {
            WriteInt(0);
            WriteInt(0);
            WriteInt(0);
            WriteInt(44); //356
            
        }
    }
}
