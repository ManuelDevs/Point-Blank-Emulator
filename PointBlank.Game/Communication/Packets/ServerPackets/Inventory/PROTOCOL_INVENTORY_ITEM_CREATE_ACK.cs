using PointBlank.Data.Communication;
using PointBlank.Data.Model;
using PointBlank.Data.Model.Enum.Items;
using System.Collections.Generic;
using System.Linq;

namespace PointBlank.Game.Communication.Packets.ServerPackets.Inventory
{
    public class PROTOCOL_INVENTORY_ITEM_CREATE_ACK : OutgoingPacket
    {
        public PROTOCOL_INVENTORY_ITEM_CREATE_ACK(List<PlayerItem> Items, int Type) : base(3588)
        {
            WriteByte((byte) Type);
            WriteInt(Items.Where(x => x.BaseItem.Type.Equals(ItemType.CHARACTER)).ToList().Count);
            WriteInt(Items.Where(x => x.BaseItem.Type.Equals(ItemType.WEAPON)).ToList().Count);
            WriteInt(Items.Where(x => x.BaseItem.Type.Equals(ItemType.ITEM)).ToList().Count);

            WriteItems(Items.Where(x => x.BaseItem.Type.Equals(ItemType.CHARACTER)).ToList());
            WriteItems(Items.Where(x => x.BaseItem.Type.Equals(ItemType.WEAPON)).ToList());
            WriteItems(Items.Where(x => x.BaseItem.Type.Equals(ItemType.ITEM)).ToList());
        }

        private void WriteItems(List<PlayerItem> Items)
        {
            foreach(PlayerItem Item in Items)
            {
                WriteLong(Item.Id);
                WriteInt(Item.BaseItem.Id);
                WriteByte((byte)Item.State);
                WriteInt(Item.Count);
            }
        }
    }
}
