using PointBlank.Auth.Communication.Packets.ServerPackets;
using PointBlank.Data.Communication;

namespace PointBlank.Auth.Communication.Packets.ClientPackets
{
    class PROTOCOL_BASE_USER_EXIT_REQ : IncomingPacket
    {
        public override void Execute()
        {
            GetConnection().SendPacket(new PROTOCOL_BASE_USER_EXIT_ACK());
            GetConnection().Close(5000, true);
        }
    }
}
