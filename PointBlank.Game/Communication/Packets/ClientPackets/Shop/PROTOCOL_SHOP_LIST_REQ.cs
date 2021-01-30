using PointBlank.Data.Communication;
using PointBlank.Game.Communication.Packets.ServerPackets.Shop;

namespace PointBlank.Game.Communication.Packets.ClientPackets.Shop
{
    class PROTOCOL_SHOP_LIST_REQ : IncomingPacket
    {
        public override void Execute()
        {
            GetConnection().SendPacket(new PROTOCOL_SHOP_GET_ITEMS_ACK());
            GetConnection().SendPacket(new PROTOCOL_SHOP_GET_GOODS_ACK());
            GetConnection().SendPacket(new PROTOCOL_SHOP_REPAIR_LIST_ACK());
            GetConnection().SendPacket(new PROTOCOL_SHOP_TEST2_ACK());
            GetConnection().SendPacket(new PROTOCOL_SHOP_GET_MATCHING_ACK());
            GetConnection().SendPacket(new PROTOCOL_SHOP_LIST_ACK());
        }
    }
}
