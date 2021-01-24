using PointBlank.Auth.Communication.Packets.ServerPackets;
using PointBlank.Data.Communication;
using System;
using System.Net;
using System.Net.Sockets;
using System.Threading;

namespace PointBlank.Auth.Communication
{
    public class Client : IConnection
    {
        private Socket _socket;
        private bool _closed = false;
        private int _shift;
        public int sessionId { get; }

        public string GetIPAddress()
        {
            if (_socket != null && _socket.RemoteEndPoint != null)
                return ((IPEndPoint)_socket.RemoteEndPoint).Address.ToString();
            return "";
        }

        public IPAddress GetAddress()
        {
            if (_socket != null && _socket.RemoteEndPoint != null)
                return ((IPEndPoint)_socket.RemoteEndPoint).Address;
            return null;
        }

        public Client(Socket socket)
        {
            _socket = socket;
            _socket.NoDelay = true;
            sessionId = AuthEnvironment.NextSessionId();
            _shift = (int) (sessionId % 7 + 1);

            AuthEnvironment.GetLogger().Debug("Client connected. [Session: " + sessionId + "; Shift: " + _shift + "]");
            new Thread(Init).Start();
            new Thread(Read).Start();
        }

        private void Init()
        {
            SendPacket(new PROTOCOL_BASE_SERVER_LIST(this));
        }

        private void Read()
        {
            try
            {
                StateObject state = new StateObject(_socket);
                _socket.BeginReceive(state.buffer, 0, StateObject.BufferSize, SocketFlags.None, new AsyncCallback(OnReceiveCallback), state);
            }
            catch
            {
                Close(0, true);
            }
        }

        private byte[] lastCompleteBuffer;
        private void OnReceiveCallback(IAsyncResult ar)
        {
            StateObject state = (StateObject)ar.AsyncState;
            try
            {
                int bytesCount = state.workSocket.EndReceive(ar);
                if (bytesCount > 0)
                {
                    byte[] babyBuffer = new byte[bytesCount];
                    Array.Copy(state.buffer, 0, babyBuffer, 0, bytesCount);

                    int length = BitConverter.ToUInt16(babyBuffer, 0) & 0x7FFF;

                    byte[] buffer = new byte[length + 2];
                    Array.Copy(babyBuffer, 2, buffer, 0, buffer.Length);

                    lastCompleteBuffer = babyBuffer;
                    byte[] decrypted = Decrypt(buffer, _shift);

                    RunPacket(decrypted, buffer);
                    Checkout(babyBuffer, length);
                    new Thread(Read).Start();
                }
            }
            catch (ObjectDisposedException e)
            {

            }
            catch
            { 
                Close(0, true); 
            }
        }

        private byte[] Decrypt(byte[] data, int shift)
        {
            byte[] newBuffer = new byte[data.Length];
            Array.Copy(data, 0, newBuffer, 0, newBuffer.Length);
            byte lastElement = newBuffer[newBuffer.Length - 1];
            for (int i = newBuffer.Length - 1; i > 0; --i)
                newBuffer[i] = (byte)(((newBuffer[i - 1] & 255) << (8 - shift)) | ((newBuffer[i] & 255) >> shift));
            newBuffer[0] = (byte)((lastElement << (8 - shift)) | ((newBuffer[0] & 255) >> shift));
            return newBuffer;
        }

        public void Checkout(byte[] buffer, int FirstLength)
        {
            int tamanho = buffer.Length;
            try
            {
                byte[] newPacketENC = new byte[tamanho - FirstLength - 4];
                Array.Copy(buffer, FirstLength + 4, newPacketENC, 0, newPacketENC.Length);
                if (newPacketENC.Length == 0)
                    return;

                int lengthPK = BitConverter.ToUInt16(newPacketENC, 0) & 0x7FFF;

                byte[] newPacketENC2 = new byte[lengthPK + 2];
                Array.Copy(newPacketENC, 2, newPacketENC2, 0, newPacketENC2.Length);


                byte[] newPacketGO = new byte[lengthPK + 2];

                Array.Copy(Decrypt(newPacketENC2, _shift), 0, newPacketGO, 0, newPacketGO.Length);

                RunPacket(newPacketGO, newPacketENC);
                Checkout(newPacketENC, lengthPK);
            }
            catch
            {
            }
        }

        public void SendPacket(OutgoingPacket packet)
        {
            try
            {
                using(packet)
                {
                    byte[] data = packet.GetPacket();
                    if (data.Length < 2) 
                        return;

                    _socket.BeginSend(data, 0, data.Length, SocketFlags.None, new AsyncCallback(SendCallback), _socket);
                    AuthEnvironment.GetLogger().Debug("Packet sended. [Name: " + packet.GetType().Name + "; Size: " + data.Length + "]");
                }

                packet.Dispose();
            }
            catch(Exception e)
            {
                Close(0, true);
                AuthEnvironment.GetLogger().SaveException(e);
            }
        }

        private void SendCallback(IAsyncResult ar)
        {
            try
            {
                Socket handler = (Socket)ar.AsyncState;
                if (handler != null && handler.Connected)
                    handler.EndSend(ar);
            }
            catch
            {
                Close(0, true);
            }
        }

        private void RunPacket(byte[] buff, byte[] simple)
        {
            UInt16 opcode = BitConverter.ToUInt16(buff, 0);
            if (_closed)
                return;

            if(opcode == 2654)
            {
                this.Close(0, true);
                return;
            }

            IncomingPacket packet = AuthEnvironment.GetPacketHandler().GetPacket(opcode);
            if (packet != null)
            {
                packet.SetData(buff, this);
                packet.Execute();
            }
            else AuthEnvironment.GetLogger().Debug("Received unhandled packet. [Opcode: " + opcode + "; Size: " + simple.Length + "]");
        }

        public void Close(int time, bool destroyConnection)
        {
            if (_closed)
                return;

            AuthEnvironment.GetLogger().Debug("Client closed. [Session: " + sessionId + "; Shift: " + _shift + "]");

            try
            {
                Thread.Sleep(time);

                _closed = true;

                if(destroyConnection)
                {
                    _socket.Close();
                    _socket.Dispose();
                }

                AuthEnvironment.GetCommunication().RemoveClient(this);
            }
            catch (Exception e)
            {
                AuthEnvironment.GetLogger().SaveException(e);
            }
        }
    }
}
