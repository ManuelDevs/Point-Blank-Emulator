using PointBlank.Data.Communication;
using PointBlank.Data.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace PointBlank.Game.Communication.Packets.ServerPackets.Shop
{
    class PROTOCOL_SHOP_BUY_ACK : OutgoingPacket
    {
        public PROTOCOL_SHOP_BUY_ACK(uint Error, List<PlayerItem> Items, Player Player) : base(531)
        {
            WriteInt(Error);
            if(Error == 1)
            {
                WriteInt(uint.Parse(DateTime.Now.ToString("yyMMddHHmm")));
                WriteInt(Items.Where(x => x.BaseItem.Type == Data.Model.Enum.Items.ItemType.CHARACTER).ToList().Count);
                WriteInt(Items.Where(x => x.BaseItem.Type == Data.Model.Enum.Items.ItemType.WEAPON).ToList().Count);
                WriteInt(Items.Where(x => x.BaseItem.Type == Data.Model.Enum.Items.ItemType.ITEM).ToList().Count);
                WriteItems(Items.Where(x => x.BaseItem.Type == Data.Model.Enum.Items.ItemType.CHARACTER).ToList());
                WriteItems(Items.Where(x => x.BaseItem.Type == Data.Model.Enum.Items.ItemType.WEAPON).ToList());
                WriteItems(Items.Where(x => x.BaseItem.Type == Data.Model.Enum.Items.ItemType.ITEM).ToList());
                WriteInt(Player.Points);
                WriteInt(Player.Moneys);
            }
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
