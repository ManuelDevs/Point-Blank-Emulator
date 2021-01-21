package ru.pb.game.chat.commands;

import ru.pb.game.network.client.ClientConnection;
import ru.pb.global.models.Player;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Команда администратора. Решает вопрос смены ника игрока
 * -Особенностью использование команды является подача входных данных.
 * Осуществляется по следущему примеру:
 * /adm change name OldName/NewName
 * <p/>
 * Автор: Grizly
 * Date: 11.12.13
 * Time: 7:48
 */
public class ChangePlayerNameAdminCommand implements BaseCommand {


	@Override
	public String getPrefix() {
		return "change name";
	}

	@Override
	public String call(ClientConnection connection, String message) {
		try {
			ConcurrentHashMap<Long, Player> playerList = connection.getServerChannel().getPlayers();
			String[] _params = message.split("/");
			for (Player p : playerList.values()) {
				if (p.getName().equals(_params[0])) {
					p.setName(_params[1]);
					return "Player name successfuly changed!";
				}
			}
			return "Player " + _params[0] + " not found!";
		} catch (Exception ex) {
			ex.printStackTrace();
			return "Sorry... Something goes wrong!";
		}
	}
}
