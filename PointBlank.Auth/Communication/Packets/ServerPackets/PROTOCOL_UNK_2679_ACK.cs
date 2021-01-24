using PointBlank.Data.Communication;

namespace PointBlank.Auth.Communication.Packets.ServerPackets
{
    class PROTOCOL_UNK_2679_ACK : OutgoingPacket
    {
        public PROTOCOL_UNK_2679_ACK() : base(2679)
        {
            WriteInt(8);
            WriteByte(1);
            WriteByte(5);
            WriteShort(1);
            WriteShort(15);
            WriteShort(42);
            WriteShort(0);
            WriteShort(1012);
            WriteShort(12);
            WriteByte(5);
            WriteString("Mar  2 2017", 11);
            WriteInt(0);
            WriteString("11:10:23", 8);
            WriteBytes(new byte[7]);
            WriteString("DIST", 4);
            WriteShort(0);
        }
    }
}
