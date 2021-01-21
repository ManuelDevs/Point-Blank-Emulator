package ru.pb.game.chat.commands;

import ru.pb.game.network.client.ClientConnection;
import ru.pb.game.network.client.packets.server.SM_INVENTORY_ADD_ITEM;
import ru.pb.global.data.holder.ItemHolder;
import ru.pb.global.enums.ItemState;
import ru.pb.global.enums.item.ItemLocation;
import ru.pb.global.models.Player;
import ru.pb.global.models.PlayerItem;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Команда администратора. Решает вопрос добавления вещи в сумку игрока.
 * Автор: Grizly,Felixx
 * Date: 11.12.13
 * Time: 1:06
 */
public class AddItemAdminCommand implements BaseCommand {
	@Override
	public String getPrefix() {
		return "add item";
	}

	@Override
	public String call(ClientConnection connection, String message) {
		try {
			ConcurrentHashMap<Long, Player> playerList = connection.getServerChannel().getPlayers();
			String[] params = message.split("/");
			for (Player p : playerList.values()) {
				if (p.getName().equals(params[0])) {
					PlayerItem playerItem = ItemHolder.getInstance().createItem(ItemLocation.INVENTORY, Integer.parseInt(params[1]), 1);
					playerItem.setFlag(ItemState.INSERT);
					p.getEqipment().addItem(playerItem);
					p.getConnection().sendPacket(new SM_INVENTORY_ADD_ITEM(Integer.parseInt(params[1])));
					return "Successfully added item[" + params[1] + "] to player: " + p.getName();
				}
			}
			return "Player " + params[0] + " not found!";
			// TODO: Пакет битый чтоли...

		} catch (Exception ex) {
			ex.printStackTrace();
			return "Command syntax : /adm add item NickName/ItemID";
		}
	}
}
