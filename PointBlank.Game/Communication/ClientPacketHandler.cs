using PointBlank.Data.Communication;
using PointBlank.Game.Communication.Packets.ClientPackets.Base;
using PointBlank.Game.Communication.Packets.ClientPackets.Inventory;
using PointBlank.Game.Communication.Packets.ClientPackets.Lobby;
using PointBlank.Game.Communication.Packets.ClientPackets.LuckyWheel;
using PointBlank.Game.Communication.Packets.ClientPackets.Shop;

namespace PointBlank.Game.Communication
{
    public class ClientPacketHandler
    {
        public IncomingPacket GetPacket(ushort Opcode)
        {
            switch (Opcode)
            {
                // Base Packets
                case 2579: return new PROTOCOL_BASE_USER_ENTER_REQ();
                case 2654: return new PROTOCOL_BASE_USER_LEAVE_REQ();
                case 2571: return new PROTOCOL_BASE_CHANNEL_LIST_REQ();
                case 2573: return new PROTOCOL_BASE_CHANNEL_ENTER_REQ();

                // Lobby Packets
                case 3079: return new PROTOCOL_LOBBY_ENTER_REQ();
                case 3101: return new PROTOCOL_LOBBY_CREATE_NICKNAME_REQ();
                case 3073: return new PROTOCOL_LOBBY_GET_ROOMLIST_REQ();

                // Outpost Packets
                case 2901: return new PROTOCOL_OUTPOST_INFO_REQ();

                // Inventory Packets
                case 3585: return new PROTOCOL_INVENTORY_ENTER_REQ();
                case 3589: return new PROTOCOL_INVENTORY_LEAVE_REQ();
                case 534: return new PROTOCOL_INVENTORY_EQUIP_REQ();

                // Shop Packets
                case 2819: return new PROTOCOL_SHOP_ENTER_REQ();
                case 2817: return new PROTOCOL_SHOP_LEAVE_REQ();
                case 2821: return new PROTOCOL_SHOP_LIST_REQ();
                case 530: return new PROTOCOL_SHOP_BUY_REQ();
            }

            return null;
        }
    }
}
