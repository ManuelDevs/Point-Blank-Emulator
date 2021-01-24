using PointBlank.Data.Communication;
using PointBlank.Data.Model;

namespace PointBlank.Game.Communication.Packets.ServerPackets.Base
{
    class PROTOCOL_BASE_CHANNEL_ENTER_ACK : OutgoingPacket
    {
        public PROTOCOL_BASE_CHANNEL_ENTER_ACK(uint error, Channel channel) : base(2574)
        {
            if (error == 0)
            {
                WriteInt(channel.Id);
                WriteShort((short) channel.Announce.Length);
                WriteString(channel.Announce);
            }
            else WriteInt(error);
        }
    }
}
