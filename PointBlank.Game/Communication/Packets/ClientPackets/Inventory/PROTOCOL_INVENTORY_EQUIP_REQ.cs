using PointBlank.Data;
using PointBlank.Data.Communication;
using PointBlank.Data.Database.Interfaces;
using PointBlank.Data.Model;
using PointBlank.Data.Model.Enum.Items;
using PointBlank.Game.Communication.Packets.ServerPackets.Inventory;
using System;

namespace PointBlank.Game.Communication.Packets.ClientPackets.Inventory
{
    class PROTOCOL_INVENTORY_EQUIP_REQ : IncomingPacket
    {
        public override void Execute()
        {
            long StockId = GetLong();

            Player Player = GetConnection().Player;

            if(Player == null || Player.GetItemById(StockId) == null || Player.GetItemById(StockId).State == ItemState.ACTIVATED)
            {
                GetConnection().SendPacket(new PROTOCOL_INVENTORY_EQUIP_ACK(0x80000000, null));
                return;
            }

            PlayerItem Item = Player.GetItemById(StockId);
            Item.Count = int.Parse(DateTime.Now.AddSeconds(Item.Count).ToString("yyMMddHHmm"));
            Item.State = ItemState.ACTIVATED;

            using (IQueryAdapter db = DataEnvironment.Database.GetQueryReactor())
            {
                db.SetQuery("UPDATE player_items SET count = @count, state = @state WHERE id = @id");
                db.AddParameter("count", Item.Count);
                db.AddParameter("id", Item.Id);
                db.AddParameter("state", ItemState.ACTIVATED.ToString());
                db.RunQuery();
            }

            GetConnection().SendPacket(new PROTOCOL_INVENTORY_EQUIP_ACK(1, Item));
        }
    }
}
