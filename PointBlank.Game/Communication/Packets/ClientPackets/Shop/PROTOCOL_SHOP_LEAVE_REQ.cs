using PointBlank.Data;
using PointBlank.Data.Communication;
using PointBlank.Data.Database.Interfaces;
using PointBlank.Data.Model;
using PointBlank.Data.Model.Enum;
using PointBlank.Data.Model.Enum.Items;
using PointBlank.Game.Communication.Packets.ServerPackets.Shop;

namespace PointBlank.Game.Communication.Packets.ClientPackets.Shop
{
    class PROTOCOL_SHOP_LEAVE_REQ : IncomingPacket
    {
        private Player Player;

        public override void Execute()
        {
            Player = GetConnection().Player;
            int Type = GetInt();

            GetConnection().SendPacket(new PROTOCOL_SHOP_LEAVE_ACK(Type));
            if ((Type & 1) == 1)
                UpdateCharacters();
            if ((Type & 2) == 2)
                UpdateWeapons();
        }

        private void UpdateIfNecessary(ItemTypeSpecific Type, int NewId)
        {
            PlayerItem AlreadyEquippedItem = Player.GetEquippedItemsByType(Type);
            PlayerItem ToEquipItem = Player.GetItemByItemId(NewId);

            if (ToEquipItem == AlreadyEquippedItem)
            {
                GameEnvironment.GetLogger().Info("Item già equippato, skippo!");
                return;
            }

            if (AlreadyEquippedItem != null)
            {
                AlreadyEquippedItem.Location = PlayerItemLocation.INVENTORY;
                using (IQueryAdapter db = DataEnvironment.Database.GetQueryReactor())
                {
                    db.SetQuery("UPDATE player_items SET location = @location WHERE id = @id;");
                    db.AddParameter("id", AlreadyEquippedItem.Id);
                    db.AddParameter("location", PlayerItemLocation.INVENTORY.ToString());
                    db.RunQuery();
                }
                GameEnvironment.GetLogger().Info("Item già equippato aggiornato non equippato! " + AlreadyEquippedItem.BaseItem.Id);
            }

            if (ToEquipItem != null)
            {
                ToEquipItem.Location = PlayerItemLocation.EQUIPPED;
                using (IQueryAdapter db = DataEnvironment.Database.GetQueryReactor())
                {
                    db.SetQuery("UPDATE player_items SET location = @location WHERE id = @id;");
                    db.AddParameter("id", ToEquipItem.Id);
                    db.AddParameter("location", PlayerItemLocation.EQUIPPED.ToString());
                    db.RunQuery();
                }
                GameEnvironment.GetLogger().Info("Item nuovo aggiornato a equippato! " + ToEquipItem.BaseItem.Id);
            }
        }

        private void UpdateWeapons()
        {
            UpdateIfNecessary(ItemTypeSpecific.WEAPON, GetInt());
            UpdateIfNecessary(ItemTypeSpecific.PISTOL, GetInt());
            UpdateIfNecessary(ItemTypeSpecific.KNIFE, GetInt());
            UpdateIfNecessary(ItemTypeSpecific.THROW, GetInt());
            UpdateIfNecessary(ItemTypeSpecific.SPECIAL, GetInt());
        }

        private void UpdateCharacters()
        {
            UpdateIfNecessary(ItemTypeSpecific.RED, GetInt());
            UpdateIfNecessary(ItemTypeSpecific.BLUE, GetInt());
            UpdateIfNecessary(ItemTypeSpecific.HEAD, GetInt());
            UpdateIfNecessary(ItemTypeSpecific.BERET, GetInt());
            UpdateIfNecessary(ItemTypeSpecific.DINO, GetInt());
        }
    }
}
