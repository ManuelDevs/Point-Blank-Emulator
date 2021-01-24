using PointBlank.Data.Communication;

namespace PointBlank.Game.Communication.Packets.ServerPackets.Lobby
{
    public class PROTOCOL_LOBBY_CREATE_NICKNAME_ACK : OutgoingPacket
    {
        public PROTOCOL_LOBBY_CREATE_NICKNAME_ACK(uint error) : base(3102)
        {
            WriteInt(error);
        }
    }
}
