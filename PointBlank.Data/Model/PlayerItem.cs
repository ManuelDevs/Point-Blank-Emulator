using PointBlank.Data.Model.Enum;
using PointBlank.Data.Model.Enum.Items;

namespace PointBlank.Data.Model
{
    public class PlayerItem
    {
        public long Id;
        public int Count;
        public ItemState State;
        public Item BaseItem;
        public PlayerItemLocation Location;
    }
}
