using PointBlank.Data.Database.Interfaces;
using PointBlank.Data.Model;
using PointBlank.Data.Model.Enum.Items;
using System;
using System.Collections.Generic;
using System.Data;

namespace PointBlank.Data.Service.Services
{
    public class ItemService
    {
        public List<Item> Items = null;

        public ItemService(Log log)
        {
            Items = new List<Item>();

            using (IQueryAdapter db = DataEnvironment.Database.GetQueryReactor())
            {
                db.SetQuery("SELECT * FROM items WHERE id > 0;");

                DataTable table = db.GetTable();
                foreach (DataRow row in table.Rows)
                {
                    if (!Enum.TryParse(Convert.ToString(row["consume"]), out ItemConsumeType consumeType))
                        continue;

                    if (!Enum.TryParse(Convert.ToString(row["type"]), out ItemType itemType))
                        continue;

                    if (!Enum.TryParse(Convert.ToString(row["specific_type"]), out ItemTypeSpecific specificType))
                        continue;

                    Item item = new Item()
                    {
                        Id = Convert.ToInt32(row["id"]),
                        SpecificType = specificType,
                        Type = itemType,
                        ConsumeType = consumeType
                    };

                    Items.Add(item);
                }
            }

            log.Debug("ItemService ready to use.");
        }
    }
}
