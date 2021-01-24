using PointBlank.Auth.Communication.Packets.ServerPackets;
using PointBlank.Data.Communication;

namespace PointBlank.Auth.Communication.Packets.ClientPackets
{
    public class PROTOCOL_UNK_2679_REQ : IncomingPacket
    {
        public override void Execute()
        {
            GetConnection().SendPacket(new PROTOCOL_UNK_2679_ACK());
        }
    }
}
