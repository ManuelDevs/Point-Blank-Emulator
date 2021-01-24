using PointBlank.Data.Communication;

namespace PointBlank.Auth.Communication.Packets.ServerPackets
{
    class PROTOCOL_BASE_SERVER_CHANGE_ACK : OutgoingPacket
    {
        public PROTOCOL_BASE_SERVER_CHANGE_ACK() : base(0xA12)
        {
            WriteInt(0);
        }
    }
}
