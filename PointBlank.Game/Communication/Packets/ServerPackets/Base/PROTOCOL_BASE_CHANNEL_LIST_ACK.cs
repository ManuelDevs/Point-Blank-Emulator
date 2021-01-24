using PointBlank.Data.Communication;
using PointBlank.Data.Model;

namespace PointBlank.Game.Communication.Packets.ServerPackets.Base
{
    class PROTOCOL_BASE_CHANNEL_LIST_ACK : OutgoingPacket
    {
        public PROTOCOL_BASE_CHANNEL_LIST_ACK() : base(2572)
        {
           /* int playersPerChannel = GameEnvironment.Services.GameServers.Servers.
                Find(x => x.Id == GameEnvironment.GetConfig().GetInt("game.id")).UsersMax
                / GameEnvironment.Services.Channels.Channels.Count;*/

            WriteInt(GameEnvironment.Services.Channels.Channels.Count);
            WriteInt(GameEnvironment.GetConfig().GetInt("game.channels.maxplayers"));

            foreach(Channel channel in GameEnvironment.Services.Channels.Channels)
            {
                WriteInt(channel.Players.Count);
            }
        }
    }
}
