using PointBlank.Data.Communication;
using PointBlank.Data.Model;
using PointBlank.Data.Model.Enum.Items;

namespace PointBlank.Auth.Communication.Packets.ServerPackets
{
    class PROTOCOL_BASE_USER_INFO_ACK : OutgoingPacket
    {
        public PROTOCOL_BASE_USER_INFO_ACK(Player player) : base(0xA06)
        {
            WriteInt(0);
            WriteByte(0);
            WriteString(player.Nickname, 33);
            WriteInt(player.Experience);
            WriteInt(player.Rank);
            WriteInt(player.Rank);
            WriteInt(player.Points);
            WriteInt(player.Moneys);
            WriteBytes(new byte[4 + 4 + 8 + 1]); // TODO: Clan infos
            WriteByte(0); // TODO: Tournament Level
            WriteByte(0); // TODO: Nickname color coupon
            WriteBytes(new byte[17 + 1 + 1 + 4 + 1]); // TODO: Other clan infos
            WriteInt(10000);
            WriteByte(0);
            WriteInt(0);
            WriteInt(0); // Last levelup

            WriteInt(player.Stats.Fights);
            WriteInt(player.Stats.Wins);
            WriteInt(player.Stats.Loses);
            WriteInt(player.Stats.Fights - (player.Stats.Wins + player.Stats.Loses));
            WriteInt(player.Stats.Kills);
            WriteInt(player.Stats.Headshots);
            WriteInt(player.Stats.Deaths);
            WriteInt(player.Stats.TotalFights);
            WriteInt(player.Stats.TotalKills);
            WriteInt(player.Stats.Escapes);
            WriteInt(player.StatsSeason.Fights);
            WriteInt(player.StatsSeason.Wins);
            WriteInt(player.StatsSeason.Loses);
            WriteInt(player.StatsSeason.Fights - (player.StatsSeason.Wins + player.StatsSeason.Loses));
            WriteInt(player.StatsSeason.Kills);
            WriteInt(player.StatsSeason.Headshots);
            WriteInt(player.StatsSeason.Deaths);
            WriteInt(player.StatsSeason.TotalFights);
            WriteInt(player.StatsSeason.TotalKills);
            WriteInt(player.StatsSeason.Escapes);

            WriteInt(player.GetEquippedItemByItemSpecificType(ItemTypeSpecific.RED));
            WriteInt(player.GetEquippedItemByItemSpecificType(ItemTypeSpecific.BLUE));
            WriteInt(player.GetEquippedItemByItemSpecificType(ItemTypeSpecific.HEAD));
            WriteInt(player.GetEquippedItemByItemSpecificType(ItemTypeSpecific.BERET));
            WriteInt(player.GetEquippedItemByItemSpecificType(ItemTypeSpecific.DINO));
            WriteInt(player.GetEquippedItemByItemSpecificType(ItemTypeSpecific.WEAPON));
            WriteInt(player.GetEquippedItemByItemSpecificType(ItemTypeSpecific.PISTOL));
            WriteInt(player.GetEquippedItemByItemSpecificType(ItemTypeSpecific.KNIFE));
            WriteInt(player.GetEquippedItemByItemSpecificType(ItemTypeSpecific.THROW));
            WriteInt(player.GetEquippedItemByItemSpecificType(ItemTypeSpecific.SPECIAL));
            WriteShort(0);
            WriteBytes(new byte[4 + 4 + 33 + 2]); // TODO: Fake rank / nick items / sight color
            WriteByte(31);

            // TODO: Add compatible with version 1.15.37
            // TODO: Add missions / titles infos bottom here

            WriteByte(0); // TODO: Enable / disable Outpost
            WriteInt(0);
            WriteInt(0);
            WriteInt(0);
            WriteInt(0);
            WriteByte(0); // active mission
            WriteByte(0); // card 1
            WriteByte(0); // card 2
            WriteByte(0); // card 3
            WriteByte(0); // card 4

            for (int i = 0; i < 10; i++)
                WriteShort(0);
            for (int i = 0; i < 10; i++)
                WriteShort(0);
            for (int i = 0; i < 10; i++)
                WriteShort(0);
            for (int i = 0; i < 10; i++)
                WriteShort(0);

            WriteByte(0); // mission id 1
            WriteByte(0); // mission id 2
            WriteByte(0); // mission id 3
            WriteByte(0); // mission id 4

            WriteBytes(new byte[40]); // C with count of completed task per mission
            WriteBytes(new byte[40]); // C with count of completed task per mission
            WriteBytes(new byte[40]); // C with count of completed task per mission
            WriteBytes(new byte[40]); // C with count of completed task per mission

            WriteByte(0); // title pos 1
            WriteByte(0); // title pos 2
            WriteByte(0); // title pos 3
            WriteByte(0); // title pos 4
            WriteByte(0); // title pos 5
            WriteByte(0); // title pos 6
            WriteShort(0);
            WriteByte(0); // title equipped 1
            WriteByte(0); // title equipped 2
            WriteByte(0); // title equipped 3
            WriteInt(0); // title slots

        }
    }
}
