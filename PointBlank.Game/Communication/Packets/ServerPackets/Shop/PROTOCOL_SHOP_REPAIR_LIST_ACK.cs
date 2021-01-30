using PointBlank.Data.Communication;

namespace PointBlank.Game.Communication.Packets.ServerPackets.Shop
{
    class PROTOCOL_SHOP_REPAIR_LIST_ACK : OutgoingPacket
    {
        public PROTOCOL_SHOP_REPAIR_LIST_ACK() : base(559)
        {
            WriteInt(0);
            WriteInt(0);
            WriteInt(0);

            // TODO: Implement repair weapons

            //writeD(100003188); //ItemId?
            //writeD(59); //GoldRepairPrice?
            //writeD(0); //CashRepairPrice?
            //writeD(100); //Max durability?
            /*
             * ID DO ITEM - WRITED
             * UNK?? - WRITED (Geralmente varia) Ou é esse que tem valor
             * UNK?? - WRITED (Geralmente varia) Repair Type (0 = ? | 1= GOLD | 2=CASH)
             * COUNT - WRITED (100)
             * 
             * 
             *///writeD(100003188); //ItemId?
            //writeD(59); //GoldRepairPrice?
            //writeD(0); //CashRepairPrice?
            //writeD(100); //Max durability?
            /*
             * ID DO ITEM - WRITED
             * UNK?? - WRITED (Geralmente varia) Ou é esse que tem valor
             * UNK?? - WRITED (Geralmente varia) Repair Type (0 = ? | 1= GOLD | 2=CASH)
             * COUNT - WRITED (100)
             * 
             * 
             */

            WriteInt(44); //356
            
        }
    }
}
