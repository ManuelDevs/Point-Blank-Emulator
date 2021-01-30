using PointBlank.Data.Communication;
using PointBlank.Data.Model;
using System;
namespace PointBlank.Game.Communication.Packets.ServerPackets.Inventory
{
    class PROTOCOL_INVENTORY_EQUIP_ACK : OutgoingPacket
    {
        public PROTOCOL_INVENTORY_EQUIP_ACK(uint Error, PlayerItem Item) : base(535)
        {
            WriteInt(Error);
            if (Error == 1)
            {
                WriteInt(uint.Parse(DateTime.Now.ToString("yyMMddHHmm")));
                WriteLong(Item.Id);
                WriteInt(Item.BaseItem.Id);
                WriteByte((byte)Item.State);
                WriteInt(Item.Count);
            }
        }
    }
}
