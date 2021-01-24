using PointBlank.Data.Communication;
using PointBlank.Data.Model;
using PointBlank.Data.Model.Enum.Items;
using System.Collections.Generic;

namespace PointBlank.Auth.Communication.Packets.ServerPackets
{
    public class PROTOCOL_BASE_USER_INVENTORY_ACK : OutgoingPacket
    {
        public PROTOCOL_BASE_USER_INVENTORY_ACK(Player player) : base(2699)
        {
            WriteItems(player.GetItemsByItemType(ItemType.CHARACTER));
            WriteItems(player.GetItemsByItemType(ItemType.WEAPON));
            WriteItems(player.GetItemsByItemType(ItemType.ITEM));
            WriteInt(0);
        }

        private void WriteItems(List<PlayerItem> items)
        {
            WriteInt(items.Count);
            foreach(PlayerItem item in items)
            {
                WriteLong(item.Id);
                WriteInt(item.BaseItem.Id);
                WriteByte((byte)item.State);
                WriteInt(item.Count);
            }
        }
    }
}
