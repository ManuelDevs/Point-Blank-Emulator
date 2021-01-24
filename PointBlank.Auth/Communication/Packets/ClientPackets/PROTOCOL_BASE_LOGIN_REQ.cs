using PointBlank.Data.Communication;


namespace PointBlank.Auth.Communication.Packets.ClientPackets
{
    class PROTOCOL_BASE_LOGIN_REQ : IncomingPacket
    {
        public override void Execute()
        {
            string gameVersion = GetByte() + "." + GetShort() + "." + GetShort();
            GetByte(); // Game Locale
            int loginSize = GetByte();
            int passwordSize = GetByte();
            string login = GetString(loginSize);
            string password = GetString(passwordSize);
            GetBytes(6); // Mac
            GetBytes(2);

            bool isLocalIp = GetByte() == 1;
            string localIp = GetByte() + "." + GetByte() + "." + GetByte() + "." + GetByte();
            ulong key = GetULong();
            GetString(32); // userfilelist md5
            GetInt();
            GetString(32); // DirectX MD5
            GetByte();
            string publicIp = GetConnection().GetIPAddress();

            AuthEnvironment.GetLogger().Debug("Logging in with: " + login + ";" + password);
        }
    }
}
