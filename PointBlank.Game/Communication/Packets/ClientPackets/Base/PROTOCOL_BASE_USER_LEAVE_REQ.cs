using PointBlank.Data.Communication;
using PointBlank.Game.Communication.Packets.ServerPackets.Base;

namespace PointBlank.Game.Communication.Packets.ClientPackets.Base
{
    class PROTOCOL_BASE_USER_LEAVE_REQ : IncomingPacket
    {
        public override void Execute()
        {
            GetConnection().SendPacket(new PROTOCOL_BASE_USER_LEAVE_ACK());
            GetConnection().Close(1000, false);
        }
    }
}
