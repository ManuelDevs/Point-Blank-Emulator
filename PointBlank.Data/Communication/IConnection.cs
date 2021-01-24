using PointBlank.Data.Model;
using System.Net;

namespace PointBlank.Data.Communication
{
    public abstract class Connection
    {
        public abstract void SendPacket(OutgoingPacket packet);
        public abstract void Close(int time, bool destroyConnection);
        public abstract IPAddress GetAddress();
        public abstract string GetIPAddress();

        public int SessionId;
        public Player Player;
        public byte[] LocalIP;
    }
}
