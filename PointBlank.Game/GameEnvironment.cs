using PointBlank.Data;
using PointBlank.Data.Database;
using PointBlank.Data.Service;
using PointBlank.Game.Communication;
using System;
using System.Threading;

namespace PointBlank.Game
{
    public static class GameEnvironment
    {
        private static Log log;
        private static Config config;
        private static ClientListener listener;
        private static ClientPacketHandler packetHandler;

        public static ServicesHandler Services;

        public static void Load()
        {
            log = new Log("Game");
            DataEnvironment.Log = log;
            try
            {
                Console.WriteLine();

                config = new Config("data\\configuration.txt");
                log.ShowDebug = config.GetBool("console.debug");

                DataEnvironment.Database = new DatabaseManager(DatabaseManager.GenerateConnectionString(
                    config.GetString("mysql.host"),
                    config.GetString("mysql.user"),
                    config.GetString("mysql.password"),
                    config.GetString("mysql.database"),
                    config.GetInt("mysql.port"),
                    config.GetInt("mysql.pool.min"),
                    config.GetInt("mysql.pool.max")));

                if (DataEnvironment.Database.IsConnected())
                    log.Info("Database connected correctly.");
                else
                    throw new Exception("Check you database connection. Impossible to connect.");

                DataEnvironment.Services = new ServicesHandler(log, config.GetInt("game.id")); ;
                Services = DataEnvironment.Services;

                packetHandler = new ClientPacketHandler();
                listener = new ClientListener();
            }
            catch (Exception e)
            {
                log.SaveException(e);
                log.Error("Impossible to start game server. Check 'logs\\game\\exceptions.txt'");
                Thread.Sleep(6000);
                Environment.Exit(0);
            }
        }

        public static Log GetLogger() => log;
        public static Config GetConfig() => config;
        public static ClientListener GetCommunication() => listener;
        public static ClientPacketHandler GetPacketHandler() => packetHandler;

        private static int sessionId = 0;
        public static int NextSessionId()
        {
            if (sessionId + 1 >= int.MaxValue) sessionId = 0;
            return sessionId++;
        }
    }
}
