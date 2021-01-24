using Org.BouncyCastle.Utilities.Net;
using PointBlank.Data.Communication;
using PointBlank.Data.Database.Interfaces;
using PointBlank.Data.Model.Enum;
using PointBlank.Data.Model.Enum.Items;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;

namespace PointBlank.Data.Model
{
    public class Player
    {
        public long Id;
        public string Nickname;
        public int Rank, Experience, Points, Moneys;

        public List<PlayerItem> Inventory = new List<PlayerItem>();

        public PlayerStats Stats;
        public PlayerStats StatsSeason;

        public byte[] LocalIP;
        public Connection Connection;

        public Channel Channel = null;

        public List<PlayerItem> GetItemsByItemType(ItemType type)
        {
            return Inventory.Where(x => x != null && x.BaseItem != null && x.BaseItem.Type.Equals(type)).ToList();
        }

        public int GetEquippedItemByItemSpecificType(ItemTypeSpecific type)
        {
            int item = 0;
            
            PlayerItem playerItem = Inventory.Where(x => x != null && x.Location == Enum.PlayerItemLocation.EQUIPPED && x.BaseItem != null && x.BaseItem.SpecificType.Equals(type)).FirstOrDefault();
            if (playerItem != null)
                item = playerItem.BaseItem.Id;

            if(item == 0)
            {
                switch(type)
                {
                    case ItemTypeSpecific.PISTOL: item = 601002003; break;
                    case ItemTypeSpecific.KNIFE: item = 702001001; break;
                    case ItemTypeSpecific.THROW: item = 803007001; break;
                    case ItemTypeSpecific.SPECIAL: item = 904007002; break;

                    case ItemTypeSpecific.RED: item = 1001001005; break;
                    case ItemTypeSpecific.BLUE: item = 1001002006; break;
                    case ItemTypeSpecific.HEAD: item = 1102003001; break;
                }
            }

            return item;
        }

        public PlayerItem GetEquippedItemsByType(ItemTypeSpecific type)
        {
            return Inventory.Where(x => x.BaseItem.SpecificType == type && x.Location == PlayerItemLocation.EQUIPPED).FirstOrDefault();
        }

        public PlayerItem GetItemByItemId(int Id)
        {
            return Inventory.Where(x => x.BaseItem.Id == Id).FirstOrDefault();
        }

        public Player(long Id)
        {
            using (IQueryAdapter db = DataEnvironment.Database.GetQueryReactor())
            {
                db.SetQuery("SELECT * FROM players WHERE id = @id LIMIT 1;");
                db.AddParameter("id", Id);
                DataRow row = db.GetRow();

                if (row != null)
                {
                    this.Id = Id;

                    Nickname = Convert.ToString(row["nickname"]);
                    Rank = Convert.ToInt32(row["rank"]);
                    Experience = Convert.ToInt32(row["experience"]);
                    Points = Convert.ToInt32(row["points"]);
                    Moneys = Convert.ToInt32(row["moneys"]);
                }
            }

            using (IQueryAdapter db = DataEnvironment.Database.GetQueryReactor())
            {
                LoadStats:
                db.SetQuery("SELECT * FROM player_stats WHERE id = @id LIMIT 1;");
                db.AddParameter("id", Id);
                DataRow row = db.GetRow();

                if (row != null)
                {
                    Stats = new PlayerStats()
                    {
                        Fights = Convert.ToInt32(row["fights"]),
                        Wins = Convert.ToInt32(row["wins"]),
                        Loses = Convert.ToInt32(row["loses"]),
                        Escapes = Convert.ToInt32(row["escapes"]),
                        Kills = Convert.ToInt32(row["kills"]),
                        Deaths = Convert.ToInt32(row["deaths"]),
                        Headshots = Convert.ToInt32(row["headshots"]),
                        TotalFights = Convert.ToInt32(row["total_fights"]),
                        TotalKills = Convert.ToInt32(row["total_kills"])
                    };
                }
                else
                {
                    CreateBaseData("player_stats");
                    goto LoadStats;
                }
            }

            using (IQueryAdapter db = DataEnvironment.Database.GetQueryReactor())
            {
                LoadSeasonStats:
                db.SetQuery("SELECT * FROM player_stats_season WHERE id = @id LIMIT 1;");
                db.AddParameter("id", Id);
                DataRow row = db.GetRow();

                if (row != null)
                {
                    StatsSeason = new PlayerStats()
                    {
                        Fights = Convert.ToInt32(row["fights"]),
                        Wins = Convert.ToInt32(row["wins"]),
                        Loses = Convert.ToInt32(row["loses"]),
                        Escapes = Convert.ToInt32(row["escapes"]),
                        Kills = Convert.ToInt32(row["kills"]),
                        Deaths = Convert.ToInt32(row["deaths"]),
                        Headshots = Convert.ToInt32(row["headshots"]),
                        TotalFights = Convert.ToInt32(row["total_fights"]),
                        TotalKills = Convert.ToInt32(row["total_kills"])
                    };
                }
                else
                {
                    CreateBaseData("player_stats_season");
                    goto LoadSeasonStats;
                }
            }

            using (IQueryAdapter db = DataEnvironment.Database.GetQueryReactor())
            {
                db.SetQuery("SELECT * FROM player_items WHERE owner_id = @id;");
                db.AddParameter("id", Id);
                DataTable table = db.GetTable();

                foreach(DataRow row in table.Rows)
                {
                    if (!System.Enum.TryParse(Convert.ToString(row["state"]), out ItemState itemState))
                        continue;

                    if (!System.Enum.TryParse(Convert.ToString(row["location"]), out PlayerItemLocation itemLocation))
                        continue;

                    PlayerItem item = new PlayerItem()
                    {
                        Id = Convert.ToInt64(row["id"]),
                        State = itemState,
                        Location = itemLocation,
                        Count = Convert.ToInt32(row["count"])
                    };

                    Item template = DataEnvironment.Services.Items.Items.Where(x => x != null && x.Id == Convert.ToInt32(row["item_id"])).FirstOrDefault();
                    if(template == null)
                    {
                        DataEnvironment.Log.Error("Loaded player item with null template. " + Convert.ToInt32(row["item_id"]));
                        continue;
                    }

                    item.BaseItem = template;
                    Inventory.Add(item);
                }

                DataEnvironment.Log.Debug("Loaded user items => " + Inventory.Count);
            }
        }

        private void CreateBaseData(string Table)
        {
            using (IQueryAdapter db = DataEnvironment.Database.GetQueryReactor())
            {
                db.SetQuery("INSERT INTO " + Table + " (id) VALUES (@id);");
                db.AddParameter("id", Id);
                db.RunQuery();

                DataEnvironment.Log.Debug("Created base data for " + Table);
            }
        }

        public PlayerItem AddItem(Item item, ItemState state, int count, PlayerItemLocation location = PlayerItemLocation.INVENTORY)
        {
            PlayerItem newItem = null;

            using (IQueryAdapter db = DataEnvironment.Database.GetQueryReactor())
            {
                db.SetQuery("INSERT INTO player_items(owner_id, item_id, state, count, location) VALUES(@owner, @item, @state, @count, @location);");
                db.AddParameter("owner", Id);
                db.AddParameter("item", item.Id);
                db.AddParameter("state", state.ToString());
                db.AddParameter("count", count);
                db.AddParameter("location", location.ToString());
                db.RunQuery();

                db.SetQuery("SELECT id FROM player_items WHERE owner_id = @owner AND item_id = @item;"); 
                db.AddParameter("owner", Id);
                db.AddParameter("item", item.Id);
                DataRow row = db.GetRow();

                if(row != null)
                {
                    newItem = new PlayerItem()
                    {
                        Id = Convert.ToInt64(row["id"]),
                        BaseItem = item,
                        State = state,
                        Count = count,
                        Location = location
                    };
                }
            }

            if (newItem != null)
            {
                Inventory.Add(newItem);
                DataEnvironment.Log.Debug("Created new item for user " + Id + " [" + newItem.Id + "; " + newItem.BaseItem.Id + "]");
            }
            else DataEnvironment.Log.Error("Not created item.");

            return newItem;
        }

        public void OnCloseUpdate()
        {
            using (IQueryAdapter db = DataEnvironment.Database.GetQueryReactor())
            {
                db.SetQuery("UPDATE players SET rank = @rank, experience = @exp, points = @gp, moneys = @moneys WHERE id = @id;");
                db.AddParameter("rank", Rank);
                db.AddParameter("experience", Experience);
                db.AddParameter("gp", Points);
                db.AddParameter("moneys", Moneys);
                db.AddParameter("id", Id);
                db.RunQuery();
            }
        }
    }
}
