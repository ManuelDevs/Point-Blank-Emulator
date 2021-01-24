using PointBlank.Data.Communication;

namespace PointBlank.Auth.Communication.Packets.ServerPackets
{
    class PROTOCOL_BASE_RANK_AWARDS_ACK : OutgoingPacket
    {
        public PROTOCOL_BASE_RANK_AWARDS_ACK() : base(2667)
        {
            for (int i = 0; i < 50; i++)
            {
                WriteByte((byte)(i + 1));
                WriteInt(0);
                WriteInt(0);
                WriteInt(0);
                WriteInt(0);
            }
        }
    }
}
