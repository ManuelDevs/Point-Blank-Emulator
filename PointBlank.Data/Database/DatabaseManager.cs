using MySql.Data.MySqlClient;
using PointBlank.Data.Database.Interfaces;

namespace PointBlank.Data.Database
{
    public sealed class DatabaseManager
    {
        private readonly string _connectionStr;

        public DatabaseManager(string ConnectionStr)
        {
            this._connectionStr = ConnectionStr;
        }

        public bool IsConnected()
        {
            try
            {
                MySqlConnection Con = new MySqlConnection(this._connectionStr);
                Con.Open();
                MySqlCommand CMD = Con.CreateCommand();
                CMD.CommandText = "SELECT 1+1";
                CMD.ExecuteNonQuery();

                CMD.Dispose();
                Con.Close();
            }
            catch (MySqlException)
            {
                return false;
            }

            return true;
        }

        public IQueryAdapter GetQueryReactor()
        {
            try
            {
                IDatabaseClient DbConnection = new DatabaseConnection(this._connectionStr);

                DbConnection.connect();

                return DbConnection.GetQueryReactor();
            }
            catch
            {
                return null;
            }
        }

        public static string GenerateConnectionString(string Host, string User, string Password, string Database, int Port, int PoolMin, int PoolMax)
        {
            var connectionString = new MySqlConnectionStringBuilder
            {
                Port = (uint)Port,
                Server = Host,
                UserID = User,
                Password = Password,
                Database = Database,
                MaximumPoolSize = (uint)PoolMax,
                MinimumPoolSize = (uint)PoolMin,
                ConnectionTimeout = 10,
                DefaultCommandTimeout = 30,
                Logging = false,
                Pooling = true,
                AllowZeroDateTime = true,
                ConvertZeroDateTime = true
            };

            return connectionString.ToString();
        }
    }
}
