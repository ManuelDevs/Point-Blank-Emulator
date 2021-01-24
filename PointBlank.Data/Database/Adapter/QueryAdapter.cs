using System.Data;
using MySql.Data.MySqlClient;
using PointBlank.Data.Database.Interfaces;

namespace PointBlank.Data.Database.Adapter
{
    public class QueryAdapter : IRegularQueryAdapter
    {
        protected IDatabaseClient client;
        protected MySqlCommand command;

        public bool dbEnabled = true;

        public QueryAdapter(IDatabaseClient Client)
        {
            client = Client;
        }

        public void AddParameter(string parameterName, object val)
        {
            command.Parameters.AddWithValue(parameterName, val);
        }

        public bool FindsResult()
        {
            bool hasRows = false;
            try
            {
                using (MySqlDataReader reader = command.ExecuteReader())
                {
                    hasRows = reader.HasRows;
                }
            }
            catch
            {

            }

            return hasRows;
        }

        public int GetInteger()
        {
            int result = 0;
            try
            {
                object obj2 = command.ExecuteScalar();
                if (obj2 != null)
                {
                    int.TryParse(obj2.ToString(), out result);
                }
            }
            catch
            {

            }

            return result;
        }

        public DataRow GetRow
            ()
        {
            DataRow row = null;
            try
            {
                DataSet dataSet = new DataSet();
                using (MySqlDataAdapter adapter = new MySqlDataAdapter(command))
                {
                    adapter.Fill(dataSet);
                }
                if ((dataSet.Tables.Count > 0) && (dataSet.Tables[0].Rows.Count == 1))
                {
                    row = dataSet.Tables[0].Rows[0];
                }
            }
            catch
            {

            }

            return row;
        }

        public string GetString()
        {
            string str = string.Empty;
            try
            {
                object obj2 = command.ExecuteScalar();
                if (obj2 != null)
                {
                    str = obj2.ToString();
                }
            }
            catch
            {

            }

            return str;
        }

        public DataTable GetTable()
        {
            var dataTable = new DataTable();
            if (!dbEnabled)
                return dataTable;

            try
            {
                using (MySqlDataAdapter adapter = new MySqlDataAdapter(command))
                {
                    adapter.Fill(dataTable);
                }
            }
            catch
            {

            }

            return dataTable;
        }

        public void RunQuery(string query)
        {
            if (!dbEnabled)
                return;

            SetQuery(query);
            RunQuery();
        }

        public void SetQuery(string query)
        {
            command.Parameters.Clear();
            command.CommandText = query;
        }

        public long InsertQuery()
        {
            if (!dbEnabled)
                return 0;

            long lastInsertedId = 0L;
            try
            {
                command.ExecuteScalar();
                lastInsertedId = command.LastInsertedId;
            }
            catch
            {

            }
            return lastInsertedId;
        }

        public void RunQuery()
        {
            if (!dbEnabled)
                return;

            try
            {
                command.ExecuteNonQuery();
            }
            catch
            {


            }
        }
    }
}
