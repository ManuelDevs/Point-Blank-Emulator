using System.Data;

namespace PointBlank.Data.Database.Interfaces
{
    public interface IRegularQueryAdapter
    {
        void AddParameter(string name, object query);
        bool FindsResult();
        int GetInteger();
        DataRow GetRow();
        string GetString();
        DataTable GetTable();
        void RunQuery(string query);
        void SetQuery(string query);
    }
}
