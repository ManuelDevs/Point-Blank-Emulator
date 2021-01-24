using PointBlank.Data.Model.Enum.Items;
using System.Collections.Generic;
using System.Linq;

namespace PointBlank.Data.Model
{
    public class Player
    {
        public long Id;
        public string Nickname;
        public int Rank, Experience, Points, Money;
        public List<PlayerItem> Items = new List<PlayerItem>();

        public Player(string Login)
        {
            LoadUserData(Login, 0);
        }

        public Player(long Id)
        {
            LoadUserData(Id, 1);
        }

        private void LoadUserData(object Value, int Type)
        {

        }

        public List<PlayerItem> GetItemsByItemType(ItemType type)
        {
            return Items.Where(x => x != null && x.BaseItem != null && x.BaseItem.Type.Equals(type)).ToList();
        }
    }
}
