using PointBlank.Data.Communication;
using PointBlank.Data.Model;

namespace PointBlank.Game.Communication.Packets.ServerPackets.Shop
{
    class PROTOCOL_SHOP_GET_ITEMS_ACK : OutgoingPacket
    {
        public PROTOCOL_SHOP_GET_ITEMS_ACK() : base(525)
        {
            WriteInt(GameEnvironment.Services.Items.Items.Count);
            WriteInt(GameEnvironment.Services.Items.Items.Count);
            WriteInt(0);
            foreach(Item Item in GameEnvironment.Services.Items.Items)
            {
                WriteInt(Item.Id);
                WriteByte((byte)Item.ConsumeType);
                WriteByte(1);
                WriteByte((byte) (Item.Type == Data.Model.Enum.Items.ItemType.ITEM ? 2 : 1));
                WriteByte((byte)Item.RequiredTitle);
             }
            WriteInt(44);
        }
    }
}
