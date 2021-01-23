package ru.pb.game.chat.commands;

import ru.pb.game.network.client.ClientConnection;
import ru.pb.global.data.holder.ItemHolder;
import ru.pb.global.enums.ItemState;
import ru.pb.global.enums.item.ItemLocation;
import ru.pb.global.models.Player;
import ru.pb.global.models.PlayerItem;

import java.util.concurrent.ConcurrentHashMap;

public class AddItemAdminCommand implements BaseCommand {
	@Override
	public String getPrefix() {
		return "add item";
	}

	@Override
	public String call(ClientConnection connection, String message) {
		return "";
	}
}
