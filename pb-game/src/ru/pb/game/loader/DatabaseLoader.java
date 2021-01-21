package ru.pb.game.loader;

import ru.pb.global.data.loaders.ItemsLoader;

/**
 * @author Felixx
 */
public final class DatabaseLoader {
	public static void loadTables() {
		ItemsLoader.getInstance();
		//TODO Goods, LevelUp, Channels, Gameservers and etc controllers
	}
}
