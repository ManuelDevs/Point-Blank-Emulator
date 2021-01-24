using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using System.Threading;

namespace PointBlank.Game.Communication
{
    public class ClientListener
    {
        private Socket socket;
        private Dictionary<int, Client> clients;

        private void UpdateTitle()
        {
            Console.Title = "Game Server - Connected clients: " + clients.Count;
        }

        public ClientListener()
        {
            try
            {
                clients = new Dictionary<int, Client>();

                socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
                socket.Bind(new IPEndPoint(IPAddress.Parse(GameEnvironment.GetConfig().GetString("game.ip")), GameEnvironment.GetConfig().GetInt("game.port")));
                socket.Listen(10);
                socket.BeginAccept(new AsyncCallback(AcceptSocket), socket);
                GameEnvironment.GetLogger().Info("Socket listening to connection. [" + GameEnvironment.GetConfig().GetString("game.ip") + ":" + GameEnvironment.GetConfig().GetString("game.port") + "]");

                UpdateTitle();
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        private void AcceptSocket(IAsyncResult result)
        {
            Socket clientSocket = (Socket)result.AsyncState;
            try
            {
                Socket handler = clientSocket.EndAccept(result);
                if (handler != null)
                {
                    Client client = new Client(handler);
                    if (client != null && clients.ContainsKey(client.SessionId))
                    {
                        GameEnvironment.GetLogger().Warn("Accepted socket have an existing sessionId.");
                        client.Close(0, true);
                    }
                    else if (client != null)
                    {
                        clients.Add(client.SessionId, client);
                    }
                    else GameEnvironment.GetLogger().Warn("Accepted socket was null on adding.");

                    Thread.Sleep(5);
                }
            }
            catch
            {
                GameEnvironment.GetLogger().Warn("Failed a connection.");
            }

            socket.BeginAccept(new AsyncCallback(AcceptSocket), socket);
            UpdateTitle();
        }

        public void RemoveClient(Client client)
        {
            if (client == null)
                return;

            if (clients.ContainsKey(client.SessionId))
                clients.Remove(client.SessionId);
            UpdateTitle();
        }
    }
}
