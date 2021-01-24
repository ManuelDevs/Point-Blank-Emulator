using PointBlank.Data.Database.Interfaces;
using PointBlank.Data.Model;
using PointBlank.Data.Model.Enum;
using System;
using System.Collections.Generic;
using System.Data;

namespace PointBlank.Data.Service.Services
{
    public class GameServerService
    {
        public List<GameServer> Servers;

        public GameServerService(Log log)
        {
            Servers = new List<GameServer>();
            
            using(IQueryAdapter db = DataEnvironment.Database.GetQueryReactor())
            {
                db.SetQuery("SELECT * FROM gameservers WHERE id > 0;");
                DataTable table = db.GetTable();

                foreach(DataRow row in table.Rows)
                {
                    if (!Enum.TryParse(Convert.ToString(row["type"]), out GameServerType ServerType))
                        return;

                    Servers.Add(new GameServer()
                    {
                        Id = Convert.ToInt32(row["id"]),
                        Type = ServerType,
                        Ip = Convert.ToString(row["ip"]),
                        Port = Convert.ToInt32(row["port"]),
                        UsersMax = Convert.ToInt32(row["max_players"])
                    });

                    log.Debug($"Loaded new gameservers. [{Servers[Servers.Count - 1].Id}; {Servers[Servers.Count - 1].Ip}:{Servers[Servers.Count - 1].Port}; {Servers[Servers.Count - 1].Type}]");
                }
            }

            log.Debug("GameServerService ready to use.");
        }
    }
}
