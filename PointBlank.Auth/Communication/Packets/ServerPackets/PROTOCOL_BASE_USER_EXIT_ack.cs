using PointBlank.Data.Communication;

namespace PointBlank.Auth.Communication.Packets.ServerPackets
{
    class PROTOCOL_BASE_USER_EXIT_ACK : OutgoingPacket
    {
        public PROTOCOL_BASE_USER_EXIT_ACK() : base(2655)
        {
        }
    }
}
