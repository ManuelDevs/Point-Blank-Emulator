using PointBlank.Data.Model.Enum;

namespace PointBlank.Data.Model
{
    public class GameServer
    {
        public int Id;
        public GameServerType Type;
        public string Ip;
        public int Port;
        public int UsersNow;
        public int UsersMax;
    }
}
