using PointBlank.Auth.Communication.Packets.ClientPackets;
using PointBlank.Data.Communication;

namespace PointBlank.Auth.Communication
{
    public class ClientPacketHandler
    {
        public IncomingPacket GetPacket(ushort Opcode)
        {
            switch(Opcode)
            {
                case 2561: return new PROTOCOL_BASE_LOGIN_REQ();
            }

            return null;
        }
    }
}
