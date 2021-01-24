using PointBlank.Data.Communication;
using PointBlank.Data.Model;
using PointBlank.Game.Communication.Packets.ServerPackets.Base;

namespace PointBlank.Game.Communication.Packets.ClientPackets.Base
{
    class PROTOCOL_BASE_USER_ENTER_REQ : IncomingPacket
    {
        public override void Execute()
        {
            string login = GetString(GetByte());
            long playerId = GetLong();
            GetByte();
            byte[] localIp = GetBytes(4);

            Player player = new Player(playerId);

            if(player == null)
            {
                GetConnection().SendPacket(new PROTOCOL_BASE_USER_ENTER_ACK(0x80000000));
                GetConnection().Close(500, false);
                return;
            }

            GetConnection().LocalIP = localIp;
            GetConnection().Player = player;
            player.Connection = GetConnection();

            GetConnection().SendPacket(new PROTOCOL_BASE_USER_ENTER_ACK(0));


        }
    }
}
