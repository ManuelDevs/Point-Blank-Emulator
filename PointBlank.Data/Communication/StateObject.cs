using System.Net.Sockets;

namespace PointBlank.Data.Communication
{
    public class StateObject
    {
        public StateObject(Socket socket)
        {
            workSocket = socket;
        }

        public Socket workSocket = null;
        public const int BufferSize = 8096;
        public byte[] buffer = new byte[BufferSize];
    }
}
