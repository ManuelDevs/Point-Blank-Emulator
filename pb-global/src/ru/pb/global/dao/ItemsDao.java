package ru.pb.global.dao;

import ru.pb.global.models.Item;

import java.util.Map;

/**
 * @author Felixx
 */
public interface ItemsDao {

	Map<Integer, Item> getAllItems();
}
