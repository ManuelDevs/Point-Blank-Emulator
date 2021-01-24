using PointBlank.Auth.Communication;
using PointBlank.Data;
using System;
using System.Threading;

namespace PointBlank.Auth
{
    public static class AuthEnvironment
    {
        private static Log log;
        private static Config config;
        private static ClientListener listener;
        private static ClientPacketHandler packetHandler;

        public static void Load()
        {
            log = new Log("Auth");

            try
            {
                config = new Config("data\\configuration.txt");
                packetHandler = new ClientPacketHandler();
                listener = new ClientListener();
            }
            catch(Exception e)
            {
                log.SaveException(e);
                log.Error("Impossible to start auth server. Check 'logs\\auth\\exceptions.txt'");
                Thread.Sleep(2000);
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
