using System;
using MySql.Data.MySqlClient;

namespace PointBlank.Data.Database.Interfaces
{
    public interface IDatabaseClient : IDisposable
    {
        void connect();
        void disconnect();
        IQueryAdapter GetQueryReactor();
        MySqlCommand createNewCommand();
        void reportDone();
    }
}
