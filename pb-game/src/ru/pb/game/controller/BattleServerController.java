package ru.pb.game.controller;

import ru.pb.game.network.battle.FromBattleToGameConnection;
import ru.pb.global.controller.BaseController;
import ru.pb.global.enums.BattleServerAuthResponse;
import ru.pb.global.models.BattleServerInfo;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Контроллер боевых серверов подключенных к игровому серверу
 *
 * @author Felixx
 */
public class BattleServerController extends BaseController {

	private static Map<Integer, BattleServerInfo> battleservers = new HashMap<Integer, BattleServerInfo>();

	private BattleServerController() {
	}

	public static BattleServerController getInstance() {
		return Singleton.INSTANCE;
	}

	public Collection<BattleServerInfo> getBattleServers() {
		return Collections.unmodifiableCollection(battleservers.values());
	}

	public BattleServerAuthResponse registerBattleServer(FromBattleToGameConnection connection, int id, String password, int port) {
		BattleServerInfo temp = new BattleServerInfo();
		temp.setPassword("test");
		battleservers.put(id, temp);

		BattleServerInfo bsi = getBattleServerInfo(id);
		if (bsi == null) {
			log.info(connection + " requested id [" + id + "] not aviable!");
			return BattleServerAuthResponse.NOT_FOUND;
		}
		if (bsi.getConnection() != null) {
			log.info(connection + " requested id [" + id + "] is already registered!");
			return BattleServerAuthResponse.ALREADY_REGISTERED;
		}
		if (!bsi.getPassword().equals(password)) {
			log.info(connection + " requested id [" + id + "] incorrect password [" + password + "]");
			return BattleServerAuthResponse.INCORRECT;
		}
		bsi.setId(id);
		bsi.setPort(port);
		bsi.setIp(connection.getIp());
		bsi.setConnection(connection);
		//bsi.setAvailable(true);
		connection.setBattleServerInfo(bsi);
		log.info(connection + " requested id [" + id + "] successfully registred.");
		return BattleServerAuthResponse.AUTHED;
	}

	public void unregisterBattleServer(FromBattleToGameConnection connection, int id) {
		battleservers.remove(id);
		log.info("Unregister BattleServer id: " + id);
	}

	public BattleServerInfo getBattleServerInfo(int battleServerId) {
		return battleservers.get(battleServerId);
	}

	private static class Singleton {
		private static final BattleServerController INSTANCE = new BattleServerController();
	}
}