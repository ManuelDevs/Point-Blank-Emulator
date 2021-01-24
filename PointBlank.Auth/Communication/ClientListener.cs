﻿using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading;

namespace PointBlank.Auth.Communication
{
    public class ClientListener
    {
        private Socket socket;
        private Dictionary<int, Client> clients;

        public ClientListener()
        {
            try
            {
                clients = new Dictionary<int, Client>();

                socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
                socket.Bind(new IPEndPoint(IPAddress.Parse(AuthEnvironment.GetConfig().GetString("auth.ip")), AuthEnvironment.GetConfig().GetInt("auth.port")));
                socket.Listen(10);
                socket.BeginAccept(new AsyncCallback(AcceptSocket), socket);
                AuthEnvironment.GetLogger().Info("Socket listening to connection. [" + AuthEnvironment.GetConfig().GetString("auth.ip") + ":" + AuthEnvironment.GetConfig().GetString("auth.port") + "]");
            }
            catch(Exception e)
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
                    if (client != null && clients.ContainsKey(client.sessionId))
                    {
                        AuthEnvironment.GetLogger().Warn("Accepted socket have an existing sessionId.");
                        client.Close(0, true);
                    }
                    else if (client != null)
                    {
                        clients.Add(client.sessionId, client);
                    }
                    else AuthEnvironment.GetLogger().Warn("Accepted socket was null on adding.");
                    
                    Thread.Sleep(5);
                }
            }
            catch
            {
                AuthEnvironment.GetLogger().Warn("Failed a connection.");
            }

            socket.BeginAccept(new AsyncCallback(AcceptSocket), socket);
        }

        public void RemoveClient(Client client)
        {
            if (client == null)
                return;

            if (clients.ContainsKey(client.sessionId))
                clients.Remove(client.sessionId);
        }
    }
}
