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
                case 2565: return new PROTOCOL_BASE_USER_INFO_REQ();
                case 2698: return new PROTOCOL_BASE_USER_INVENTORY_REQ();
                case 2666: return new PROTOCOL_BASE_RANK_AWARDS_REQ();
                case 2678: return new PROTOCOL_UNK_2679_REQ();
                case 2567: return new PROTOCOL_BASE_USER_CONFIGS_REQ();
                case 2577: return new PROTOCOL_BASE_SERVER_CHANGE_REQ();
                case 2654: return new PROTOCOL_BASE_USER_EXIT_REQ();
            }

            return null;
        }
    }
}
