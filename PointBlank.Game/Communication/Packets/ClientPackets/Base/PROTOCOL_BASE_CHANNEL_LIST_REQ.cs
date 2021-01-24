using PointBlank.Data.Communication;
using PointBlank.Game.Communication.Packets.ServerPackets.Base;

namespace PointBlank.Game.Communication.Packets.ClientPackets.Base
{
    class PROTOCOL_BASE_CHANNEL_LIST_REQ : IncomingPacket
    {
        public override void Execute()
        {
            if (GetConnection().Player == null)
                return;

            GetConnection().SendPacket(new PROTOCOL_BASE_CHANNEL_LIST_ACK());
        }
    }
}
