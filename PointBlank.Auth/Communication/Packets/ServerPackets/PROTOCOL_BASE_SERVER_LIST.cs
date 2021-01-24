using PointBlank.Data.Communication;

namespace PointBlank.Auth.Communication.Packets.ServerPackets
{
    class PROTOCOL_BASE_SERVER_LIST : OutgoingPacket
    {
        int port = 39190;
        public PROTOCOL_BASE_SERVER_LIST(Client client) : base(2049)
        {
            WriteInt(client.sessionId);
            WriteIP(client.GetAddress());
            WriteShort(29890); // TODO: make it configurable
            WriteShort(0);
            for (int idx = 0; idx < 10; idx++)
                WriteByte(1);
            WriteByte(1);

            WriteInt(1); // Servers count
            WriteInt(true);
            WriteIP("127.0.0.1");
            WriteShort((short) port);
            WriteByte(1); // server typew
            WriteShort(100); // max pl
            WriteInt(50); // current pl

            WriteShort(1);
        }
    }
}
