using PointBlank.Data.Communication;
using PointBlank.Data.Model;

namespace PointBlank.Game.Communication.Packets.ServerPackets.Lobby
{
    class PROTOCOL_LOBBY_GET_ROOMLIST_ACK : OutgoingPacket
    {
        public PROTOCOL_LOBBY_GET_ROOMLIST_ACK(Channel Channel) : base(3074)
        {
            WriteInt(0);
            WriteInt(0);
            WriteInt(0);

            /*
               WriteD(r.id);
				WriteS(r.name, 23);
				WriteH(r.map);
				WriteC(r.stage4v4);
				WriteC(r.type.ordinal());
				WriteC(r.rstate.ordinal() > 1 ? 1 : 0);
				WriteC(r.getPlayers().size());
				WriteC(r.slots());
				WriteC(r.ping);
				WriteC(r.allWeapons);
				WriteC(r.restrictions());
				WriteC(r.special);
            */
            WriteInt(Channel.Players.Count);
            WriteInt(0);
            WriteInt(Channel.Players.Count);

            foreach (Player Player in Channel.Players)
            {
                WriteInt(Player.Connection.SessionId);
                WriteByte(255);
                WriteByte(255);
                WriteByte(255);
                WriteByte(255);
                WriteString("", 17);
                WriteShort((short) Player.Rank);
                WriteString(Player.Nickname, 33);
                WriteByte(0);
                WriteByte(31);
            }
        }
    }
}
