using PointBlank.Data.Communication;
using PointBlank.Data.Model;
using PointBlank.Game.Communication.Packets.ServerPackets.Base;
using System.Linq;

namespace PointBlank.Game.Communication.Packets.ClientPackets.Base
{
    public class PROTOCOL_BASE_CHANNEL_ENTER_REQ : IncomingPacket
    {
        public override void Execute()
        {
            int channelId = GetInt();

            Channel channel = GameEnvironment.Services.Channels.Channels.Where(x => x.Id == channelId).FirstOrDefault();
            if (channel != null)
            {
                if (channel.Players.Count < GameEnvironment.GetConfig().GetInt("game.channels.maxplayers"))
                {
                    GetConnection().Player.Channel = channel;
                    channel.AddPlayer(GetConnection().Player);
                    GetConnection().SendPacket(new PROTOCOL_BASE_CHANNEL_ENTER_ACK(0, channel));
                }
                else GetConnection().SendPacket(new PROTOCOL_BASE_CHANNEL_ENTER_ACK(0x80000201, null));
            }
            else GetConnection().SendPacket(new PROTOCOL_BASE_CHANNEL_ENTER_ACK(0x80000000, null));
        }
    }
}
