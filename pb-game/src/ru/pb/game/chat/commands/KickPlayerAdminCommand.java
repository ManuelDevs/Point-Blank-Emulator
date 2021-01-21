package ru.pb.game.chat.commands;

import ru.pb.game.network.client.ClientConnection;
import ru.pb.game.network.client.packets.server.PROTOCOL_BASE_LEAVE_ACK;
import ru.pb.global.enums.SlotState;
import ru.pb.global.models.Player;
import ru.pb.global.models.RoomSlot;

import java.util.concurrent.ConcurrentHashMap;

public class KickPlayerAdminCommand implements BaseCommand {
	@Override
	public String getPrefix() {
		return "kick";
	}

	@Override
	public String call(ClientConnection connection, String message) {
		try {
			if(connection.getRoom() == null)
			{
				ConcurrentHashMap<Long, Player> playerList = connection.getServerChannel().getPlayers();
				for (Player p : playerList.values()) {
					if (p.getName().equals(message)) {
						p.getConnection().close(new PROTOCOL_BASE_LEAVE_ACK());
						return "Player name successfuly kicked!";
					}
				}
			}
			else
			{
				Player p = connection.getRoom().getPlayers().get(Integer.getInteger(message));
				RoomSlot roomSlot = connection.getRoom().getRoomSlot(Integer.getInteger(message));
				roomSlot.setState(SlotState.SLOT_STATE_EMPTY);
				p.getConnection().close(new PROTOCOL_BASE_LEAVE_ACK());
				return "Player name successfuly kicked!";
			}
			return "Player " + message + " not found!";
		} catch (Exception ex) {
			ex.printStackTrace();
			return "Sorry... Something goes wrong!";
		}
	}
}
