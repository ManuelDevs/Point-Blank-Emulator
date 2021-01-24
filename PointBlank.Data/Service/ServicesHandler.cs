using PointBlank.Data.Model;
using PointBlank.Data.Service.Services;

namespace PointBlank.Data.Service
{
    public class ServicesHandler
    {
        public PlayerService Players;
        public GameServerService GameServers;
        public ChannelService Channels;
        public ItemService Items;

        public ServicesHandler(Log log, int gameServer = -1)
        {
            Players = new PlayerService(log);
            GameServers = new GameServerService(log);
            Items = new ItemService(log);

            if(gameServer > -1)
            {
                Channels = new ChannelService(log, gameServer);
            }

            log.Info("Services ready to use.");
        }
    }
}
