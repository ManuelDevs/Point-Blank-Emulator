using PointBlank.Data.Communication;

namespace PointBlank.Auth.Communication.Packets.ServerPackets
{
    public class PROTOCOL_BASE_LOGIN_ACK : OutgoingPacket
    {
        public PROTOCOL_BASE_LOGIN_ACK(uint Result, long Id, string Login) : base(2564)
        {
            WriteInt(Result);
            WriteByte(0);
            WriteLong(Id);
            WriteByte((byte) Login.Length);
            WriteString(Login, Login.Length);
            WriteBytes(new byte[2]);
        }
    }
}
