using PointBlank.Data.Communication;
using PointBlank.Data.Model;

namespace PointBlank.Game.Communication.Packets.ServerPackets.Shop
{
    class PROTOCOL_SHOP_GET_MATCHING_ACK : OutgoingPacket
    {
        public PROTOCOL_SHOP_GET_MATCHING_ACK() : base (527)
        {
			WriteInt(GameEnvironment.Services.Goods.Goods.Count);
			WriteInt(GameEnvironment.Services.Goods.Goods.Count);
			WriteInt(0);

			foreach (Good Good in GameEnvironment.Services.Goods.Goods)
			{
				WriteInt(Good.Id);
				WriteInt(Good.BaseItem.Id);
				WriteInt(Good.Count);
			}
			WriteInt(44);
		}
    }
}
