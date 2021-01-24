using PointBlank.Data.Communication;

namespace PointBlank.Game.Communication.Packets.ServerPackets.Inventory
{
    class PROTOCOL_INVENTORY_LEAVE_ACK : OutgoingPacket
    {
        public PROTOCOL_INVENTORY_LEAVE_ACK(int Type) : base(3590)
        {
            WriteInt(Type);
            WriteInt(0);
        }
    }
}
