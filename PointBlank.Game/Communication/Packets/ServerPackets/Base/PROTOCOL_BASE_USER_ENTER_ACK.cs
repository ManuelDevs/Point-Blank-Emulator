using PointBlank.Data.Communication;

namespace PointBlank.Game.Communication.Packets.ServerPackets.Base
{
    public class PROTOCOL_BASE_USER_ENTER_ACK : OutgoingPacket
    {
        public PROTOCOL_BASE_USER_ENTER_ACK(uint error) : base(2580)
        {
            WriteInt(error);
        }
    }
}
