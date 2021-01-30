using PointBlank.Data.Database.Interfaces;
using PointBlank.Data.Model;
using PointBlank.Data.Model.Enum;
using System;
using System.Collections.Generic;
using System.Data;

namespace PointBlank.Data.Service.Services
{
    public class ChannelService
    {
        public List<Channel> Channels = new List<Channel>();

        public ChannelService(Log log, int gameServer)
        {
            using (IQueryAdapter db = DataEnvironment.Database.GetQueryReactor())
            {
                db.SetQuery("SELECT * FROM channels WHERE gameserver = @gs ORDER BY id;");
                db.AddParameter("gs", gameServer);

                DataTable table = db.GetTable();

                foreach (DataRow row in table.Rows)
                {
                    if (!Enum.TryParse(Convert.ToString(row["type"]), out GameServerType ServerType))
                        return;

                    Channels.Add(new Channel()
                    {
                        Id = Convert.ToInt32(row["id"]),
                        Type = ServerType,
                        Announce = Convert.ToString(row["announce"])
                    });

                    log.Debug($"Loaded new channel. [{Channels[Channels.Count - 1].Id}; {Channels[Channels.Count - 1].Type}]");
                }
            }

            log.Debug("ChannelService for gameServer " + gameServer + " ready to use.");
        }
    }
}
