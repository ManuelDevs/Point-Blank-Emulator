using Microsoft.VisualBasic;
using PointBlank.Data.Database.Interfaces;
using PointBlank.Data.Model;
using PointBlank.Data.Model.Enum.Items;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;

namespace PointBlank.Data.Service.Services
{
    public class GoodService
    {
        public List<Good> Goods = null;
        public GoodService(Log log, ItemService Items)
        {
            Goods = new List<Good>();

            using (IQueryAdapter db = DataEnvironment.Database.GetQueryReactor())
            {
                db.SetQuery("SELECT * FROM goods WHERE visible = 1;");
                DataTable table = db.GetTable();

                foreach (DataRow row in table.Rows)
                {
                    int ItemId = Convert.ToInt32(row["item_id"]);
                    string Points = Convert.ToString(row["points"]);
                    string Moneys = Convert.ToString(row["moneys"]);
                    string Counts = Convert.ToString(row["counts"]);
                    string Tag = Convert.ToString(row["tag"]);

                    Item Item = Items.Items.Where(x => x.Id == ItemId).FirstOrDefault();
                    if(Item == null)
                    {
                        log.Error("Good " + ItemId + " have ItemTemplate null");
                        continue;
                    }

                    if (!Counts.EndsWith(","))
                        Counts += ",";

                    if (!Points.EndsWith(","))
                        Points += ",";

                    if (!Moneys.EndsWith(","))
                        Moneys += ",";

                    string[] CountsReals = Counts.Split(',');
                    string[] PricesMoneys = Moneys.Split(',');
                    string[] PricePoints = Points.Split(',');

                    if(!((CountsReals.Length == PricesMoneys.Length) && (CountsReals.Length == PricePoints.Length)))
                    {
                        log.Error("Good with multiple counts have different sizes. Check prices.");
                        continue;
                    }

                    for(int idx = 0; idx < CountsReals.Length - 1; idx++)
                    {
                        Good good = new Good()
                        {
                            BaseItem = Item,
                            Id = int.Parse(ItemId.ToString().Substring(ItemId.ToString().Length - 4, 4) + idx.ToString("00")),
                            Count = int.Parse(CountsReals[idx]),
                            Moneys = int.Parse(PricesMoneys[idx]),
                            Points = int.Parse(PricePoints[idx]),
                            Tag = Tag.Trim().Length == 0 ? 0 : (Tag == "NEW" ? 1 : 2)
                        };

                        Goods.Add(good);
                    }
                }
            }

            log.Debug("GoodService ready to use. Goods => " + Goods.Count);
        }
    }
}
