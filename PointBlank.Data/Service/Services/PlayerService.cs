using PointBlank.Data.Database.Interfaces;
using PointBlank.Data.Model;
using System;
using System.Data;

namespace PointBlank.Data.Service.Services
{
    public class PlayerService
    {
        private Log log;
        public PlayerService(Log log)
        {
            this.log = log;
            log.Debug("PlayerServer ready to use.");
        }
        
        public bool ExistName(string Nickname)
        {
            try
            {
                using (IQueryAdapter db = DataEnvironment.Database.GetQueryReactor())
                {
                    db.SetQuery("SELECT id FROM players WHERE nickname = @nick LIMIT 1;");
                    db.AddParameter("nick", Nickname);
                    DataRow row = db.GetRow();

                    if (row != null)
                    {
                        return true;
                    }
                }
            }
            catch
            {
                log.Error("Error loading a nickname.");
            }

            return false;
        }

        public void UpdateNickname(long Id, string Nickname)
        {
            try
            {
                using (IQueryAdapter db = DataEnvironment.Database.GetQueryReactor())
                {
                    db.SetQuery("UPDATE players SET nickname = @nick WHERE id = @id;");
                    db.AddParameter("nick", Nickname);
                    db.AddParameter("id", Id);
                    db.RunQuery();
                }
            }
            catch
            {
                log.Error("Error updating a nickname.");
            }
        }

        public Player Load(string Login, string Password)
        {
            try
            {
                long id = -1;

                using (IQueryAdapter db = DataEnvironment.Database.GetQueryReactor())
                {
                    db.SetQuery("SELECT id FROM players WHERE login = @login AND password = @password LIMIT 1;");
                    db.AddParameter("login", Login);
                    db.AddParameter("password", Password);
                    DataRow row = db.GetRow();

                    if(row != null)
                    {
                        id = Convert.ToInt64(row["id"]);

                        return new Player(id);
                    }
                }
                
                log.Debug("Loaded account from login '" + Login + "' and password '" + Password + "' [Id: " + id + "]");
            }
            catch
            {
                log.Error("Error loading a player.");
            }
            return null;
        }
    }
}
