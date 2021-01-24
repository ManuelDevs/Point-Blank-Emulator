using PointBlank.Data.Communication;
using PointBlank.Game.Communication.Packets.ServerPackets.Shop;

namespace PointBlank.Game.Communication.Packets.ClientPackets.Shop
{
    class PROTOCOL_SHOP_ENTER_REQ : IncomingPacket
    {
        public override void Execute()
        {
            GetConnection().SendPacket(new PROTOCOL_SHOP_ENTER_ACK());
        }
    }
}
