package ru.pb.game.chat.commands;

import ru.pb.game.network.client.ClientConnection;
import ru.pb.game.network.client.packets.server.SM_LEAVE;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;
import ru.pb.global.models.RoomSlot;

/**
 * Удаление комнаты с отключением игроков от сервера из неё
 * User: DarkSkeleton
 * Date: 19.01.14
 * Time: 19:36
 */
public class DelRoomAdminCommand implements BaseCommand
{
	@Override
	public String getPrefix() {
		return "del room";
	}

	@Override
	public String call(ClientConnection connection, String message) {
		if(connection.getRoom() == null)
		{
			int roomId = Integer.getInteger(message);
			Room room = connection.getServerChannel().getRoom(roomId);

			if(room != null)
			{
				for(Player player : room.getPlayers().values())
				{
					RoomSlot roomSlot = room.getRoomSlotByPlayer(player);
					room.removePlayer(player);
					player.getConnection().close(new SM_LEAVE());
				}
				return "Success remove room!";
			}
			return "Room is not found.";
		}
		return "Please leave room!";
	}
}
