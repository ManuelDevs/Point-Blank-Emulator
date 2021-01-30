using PointBlank.Data.Communication;
using PointBlank.Data.Model;

namespace PointBlank.Game.Communication.Packets.ServerPackets.Shop
{
    class PROTOCOL_SHOP_GET_GOODS_ACK : OutgoingPacket
    {
        public PROTOCOL_SHOP_GET_GOODS_ACK() : base(523)
        {

			WriteInt(GameEnvironment.Services.Goods.Goods.Count);
			WriteInt(GameEnvironment.Services.Goods.Goods.Count);
			WriteInt(0);

			foreach(Good Good in GameEnvironment.Services.Goods.Goods)
			{
				WriteInt(Good.Id);
				WriteByte(1);
				WriteByte(1); //Flag1 = Show icon + Buy option | Flag2 = UNK | Flag4 = Show icon + No buy option
				WriteInt(Good.Points);
				WriteInt(Good.Moneys);
				WriteByte((byte) Good.Tag);
			}
			WriteInt(44);
		}
    }
}
