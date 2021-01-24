using PointBlank.Data.Communication;
using PointBlank.Game.Communication.Packets.ServerPackets.Inventory;

namespace PointBlank.Game.Communication.Packets.ClientPackets.Inventory
{
    class PROTOCOL_INVENTORY_ENTER_REQ : IncomingPacket
    {
        public override void Execute()
        {
            GetConnection().SendPacket(new PROTOCOL_INVENTORY_ENTER_ACK());
        }
    }
}
