using PointBlank.Data.Model.Enum;
using System.Collections.Generic;

namespace PointBlank.Data.Model
{
    public class Channel
    {
        public int Id;
        public string Announce;
        public GameServerType Type;

        public List<Player> Players = new List<Player>();

        public void AddPlayer(Player player)
        {
            if (!Players.Contains(player))
                Players.Add(player);
        }
    }
}
