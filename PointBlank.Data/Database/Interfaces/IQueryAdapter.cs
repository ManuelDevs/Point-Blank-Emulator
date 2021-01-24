using System;

namespace PointBlank.Data.Database.Interfaces
{
    public interface IQueryAdapter : IRegularQueryAdapter, IDisposable
    {
        long InsertQuery();
        void RunQuery();
    }
}
