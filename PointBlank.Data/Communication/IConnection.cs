using System.Net;

namespace PointBlank.Data.Communication
{
    public interface IConnection
    {
        public void SendPacket(OutgoingPacket packet);
        public void Close(int time, bool destroyConnection);
        public IPAddress GetAddress();
        public string GetIPAddress();
    }
}
