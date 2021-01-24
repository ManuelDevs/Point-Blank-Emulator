using PointBlank.Data.Communication;
using PointBlank.Game.Communication.Packets.ServerPackets.Lobby;

namespace PointBlank.Game.Communication.Packets.ClientPackets.Lobby
{
    class PROTOCOL_LOBBY_ENTER_REQ : IncomingPacket
    {
        public override void Execute()
        {
            GetConnection().SendPacket(new PROTOCOL_LOBBY_ENTER_ACK());
            // TODO: Remove player from room if is in room
        }
    }
}
