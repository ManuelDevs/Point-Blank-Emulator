using PointBlank.Auth.Communication.Packets.ServerPackets;
using PointBlank.Data.Communication;
using PointBlank.Data.Model;
using PointBlank.Data.Model.Enum;

namespace PointBlank.Auth.Communication.Packets.ClientPackets
{
    class PROTOCOL_BASE_LOGIN_REQ : IncomingPacket
    {
        public override void Execute()
        {
            string gameVersion = GetByte() + "." + GetShort() + "." + GetShort();
            int gameLocale = GetByte();
            int loginSize = GetByte();
            int passwordSize = GetByte();
            string login = GetString(loginSize);
            string password = GetString(passwordSize);
            byte[] mac = GetBytes(6);
            GetBytes(2);

            bool isLocalIp = GetByte() == 1;
            string localIp = GetByte() + "." + GetByte() + "." + GetByte() + "." + GetByte();
            ulong key = GetULong();
            string userFileListMD5 = GetString(32);
            GetInt();
            string directXVersion = GetString(32);
            GetByte();
            string publicIp = GetConnection().GetIPAddress();

            Player Player = AuthEnvironment.Services.Players.Load(login, password);

            if(Player != null)
            {
                GetConnection().SendPacket(new PROTOCOL_BASE_LOGIN_ACK(1, Player.Id, login));
                GetConnection().Player = Player;
                AuthEnvironment.GetLogger().Debug("Player logged in.");
            }
            else
            {
                GetConnection().SendPacket(new PROTOCOL_BASE_LOGIN_ACK((uint)LoginResult.USER_PASS_FAIL, 0, ""));
                AuthEnvironment.GetLogger().Debug("Player logged in with wrong infos.");
            }
        }
    }
}
