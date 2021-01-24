using PointBlank.Data;
using PointBlank.Data.Communication;
using PointBlank.Data.Model;

namespace PointBlank.Auth.Communication.Packets.ServerPackets
{
    class PROTOCOL_BASE_SERVER_LIST : OutgoingPacket
    {
        int port = 39190;

        public PROTOCOL_BASE_SERVER_LIST(Client client) : base(2049)
        {
            WriteInt(client.SessionId);
            WriteIP(client.GetAddress());
            WriteShort(29890); 
            WriteShort(0);
            for (int idx = 0; idx < 10; idx++)
                WriteByte(1);
            WriteByte(1);

            WriteInt(AuthEnvironment.Services.GameServers.Servers.Count + 1);

            WriteInt(true);
            WriteIP("127.0.0.1");
            WriteShort((short) port);
            WriteByte(1);
            WriteShort(100); 
            WriteInt(50);

            foreach(GameServer Server in AuthEnvironment.Services.GameServers.Servers)
            {
                WriteInt(true);
                WriteIP(Server.Ip);
                WriteShort((short) Server.Port);
                WriteByte((byte) Server.Type);
                WriteShort((short) Server.UsersMax);
                WriteInt(0); // TODO: Communicate with GameServer to know how much players there are
            }   
            
            WriteShort(1);
        }
    }
}
