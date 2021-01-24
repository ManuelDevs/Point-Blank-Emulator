using PointBlank.Auth.Communication.Packets.ServerPackets;
using PointBlank.Data.Communication;

namespace PointBlank.Auth.Communication.Packets.ClientPackets
{
    class PROTOCOL_BASE_RANK_AWARDS_REQ : IncomingPacket
    {
        public override void Execute()
        {
            GetConnection().SendPacket(new PROTOCOL_BASE_RANK_AWARDS_ACK());
        }
    }
}
