using Org.BouncyCastle.Crypto.Signers;
using PointBlank.Data;
using PointBlank.Data.Communication;
using PointBlank.Data.Database.Interfaces;
using PointBlank.Data.Model;
using PointBlank.Data.Model.Enum;
using PointBlank.Data.Model.Enum.Items;
using PointBlank.Game.Communication.Packets.ServerPackets.Shop;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Runtime.InteropServices.ComTypes;

namespace PointBlank.Game.Communication.Packets.ClientPackets.Shop
{
    public class PROTOCOL_SHOP_BUY_REQ : IncomingPacket
    {

        public override void Execute()
        {
            int Count = GetByte();
            int GoodId = GetInt();
            BuyType BuyType = (BuyType) GetByte();

            Player Player = GetConnection().Player;
            if(Player == null)
            {
                GetConnection().SendPacket(new PROTOCOL_SHOP_BUY_ACK(2147487767, null, null));
                return;
            }

            if(Player.Inventory.Count >= GameEnvironment.GetConfig().GetInt("game.inventory.limit"))
            {
                GetConnection().SendPacket(new PROTOCOL_SHOP_BUY_ACK(2147487929, null, null));
                return;
            }

            Good Good = GameEnvironment.Services.Goods.Goods.Where(x => x.Id == GoodId).FirstOrDefault();
            if(Good != null && Good.BaseItem != null)
            {
                if ((BuyType == BuyType.MONEYS && Player.Moneys >= Good.Moneys) || (BuyType == BuyType.POINTS && Player.Points >= Good.Points))
                {
                    try 
                    {
                        PlayerItem InventoryItem = Player.GetItemByItemId(Good.BaseItem.Id);
                        if (InventoryItem == null)
                        {
                            InventoryItem = GetConnection().Player.AddItem(Good.BaseItem, ItemState.NORMAL, Good.Count, PlayerItemLocation.INVENTORY);
                            Player.Inventory.Add(InventoryItem);
                        }
                        else
                        {
                            if (InventoryItem.State == ItemState.NORMAL)
                                InventoryItem.Count += Good.Count;
                            else
                            {
                                DateTime Date = DateTime.ParseExact(InventoryItem.Count.ToString(), "yyMMddHHmm", CultureInfo.InvariantCulture);
                                InventoryItem.Count = int.Parse(Date.AddSeconds(Good.Count).ToString("yyMMddHHmm"));
                            }

                            using (IQueryAdapter db = DataEnvironment.Database.GetQueryReactor())
                            {
                                db.SetQuery("UPDATE player_items SET count = @count WHERE id = @id");
                                db.AddParameter("count", InventoryItem.Count);
                                db.AddParameter("id", InventoryItem.Id);
                                db.RunQuery();
                            }
                        }

                        if (BuyType == BuyType.POINTS)
                            Player.Points -= Good.Points;
                        if (BuyType == BuyType.MONEYS)
                            Player.Moneys -= Good.Moneys;

                        List<PlayerItem> NewItems = new List<PlayerItem>() { InventoryItem };
                        GetConnection().SendPacket(new PROTOCOL_SHOP_BUY_ACK(1, NewItems, Player));
                    }
                    catch(Exception e)
                    {
                        GameEnvironment.GetLogger().Error(e.ToString()); 
                        GetConnection().SendPacket(new PROTOCOL_SHOP_BUY_ACK(2147487769, null, null));
                    }
                }
                else GetConnection().SendPacket(new PROTOCOL_SHOP_BUY_ACK(2147487768, null, null));
            }
            else GetConnection().SendPacket(new PROTOCOL_SHOP_BUY_ACK(2147487769, null, null));
        }
    }
    
    public enum BuyType
    {
        POINTS = 1,
        MONEYS = 2
    }
}
