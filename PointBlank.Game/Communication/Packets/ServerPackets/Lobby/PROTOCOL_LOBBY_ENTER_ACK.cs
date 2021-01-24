using PointBlank.Data.Communication;

namespace PointBlank.Game.Communication.Packets.ServerPackets.Lobby
{
    public class PROTOCOL_LOBBY_ENTER_ACK : OutgoingPacket
    {
        public PROTOCOL_LOBBY_ENTER_ACK() : base(3080)
        {
            WriteInt(0);
        }
    }
}
