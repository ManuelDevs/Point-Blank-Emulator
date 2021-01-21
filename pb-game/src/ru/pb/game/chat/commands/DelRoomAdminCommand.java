package ru.pb.game.chat.commands;

import ru.pb.game.network.client.ClientConnection;
import ru.pb.game.network.client.packets.server.PROTOCOL_BASE_LEAVE_ACK;
import ru.pb.global.models.Player;
import ru.pb.global.models.Room;
import ru.pb.global.models.RoomSlot;

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
					player.getConnection().close(new PROTOCOL_BASE_LEAVE_ACK());
				}
				return "Success remove room!";
			}
			return "Room is not found.";
		}
		return "Please leave room!";
	}
}
