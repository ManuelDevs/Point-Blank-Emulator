using PointBlank.Data.Communication;
using PointBlank.Data.Model;
using PointBlank.Data.Model.Enum;
using PointBlank.Data.Model.Enum.Items;
using PointBlank.Game.Communication.Packets.ServerPackets.Inventory;
using PointBlank.Game.Communication.Packets.ServerPackets.Lobby;
using System.Collections.Generic;
using System.Linq;

namespace PointBlank.Game.Communication.Packets.ClientPackets.Lobby
{
    public class PROTOCOL_LOBBY_CREATE_NICKNAME_REQ : IncomingPacket
    {
        public override void Execute()
        {
            string name = GetString(GetByte());
            if (name.Length < GameEnvironment.GetConfig().GetInt("game.nick.min")
                || name.Length > GameEnvironment.GetConfig().GetInt("game.nick.max"))
            {
                GetConnection().SendPacket(new PROTOCOL_LOBBY_CREATE_NICKNAME_ACK(0x80001013));
                return;
            }

            if(GameEnvironment.Services.Players.ExistName(name))
            {
                GetConnection().SendPacket(new PROTOCOL_LOBBY_CREATE_NICKNAME_ACK(0x80000113));
                return;
            }

            GetConnection().SendPacket(new PROTOCOL_LOBBY_CREATE_NICKNAME_ACK(0));
            // TODO: Send BASE_QUEST_GET_INFO_PAK

            GameEnvironment.Services.Players.UpdateNickname(GetConnection().Player.Id, name);
            GetConnection().Player.Nickname = name;

            List<PlayerItem> items = new List<PlayerItem>();
            items.Add(AddItem(100003004));
            items.Add(AddItem(200004006));
            items.Add(AddItem(300005003));
            items.Add(AddItem(400006001));
            items.Add(AddItem(601002003, PlayerItemLocation.EQUIPPED));
            items.Add(AddItem(702001001, PlayerItemLocation.EQUIPPED));
            items.Add(AddItem(803007001, PlayerItemLocation.EQUIPPED));
            items.Add(AddItem(904007002, PlayerItemLocation.EQUIPPED));
            items.Add(AddItem(1001001005, PlayerItemLocation.EQUIPPED));
            items.Add(AddItem(1001002006, PlayerItemLocation.EQUIPPED));
            items.Add(AddItem(1102003001, PlayerItemLocation.EQUIPPED));
            GetConnection().SendPacket(new PROTOCOL_INVENTORY_ITEM_CREATE_ACK(items, 1));
        }

        private PlayerItem AddItem(int ItemId, PlayerItemLocation Location = PlayerItemLocation.INVENTORY)
        {
            Item Item = GameEnvironment.Services.Items.Items.Where(x => x.Id == ItemId).FirstOrDefault();
            if (Item == null)
                return null;

            return GetConnection().Player.AddItem(Item, ItemState.DISABLED, 1, Location);
        }
    }
}
