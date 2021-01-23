package ru.pb.global.dao;

import java.util.List;

public interface MapsDao {
	void load();
	
	int getMapList(int list);
	List<Short> getModes();
	List<Byte> getTags();
}
