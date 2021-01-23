package ru.pb.global.dao.impl;

import static ru.pb.global.dao.enums.QueryList.GET_ALL_MAPS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.pb.global.dao.MapsDao;
import ru.pb.global.dao.jdbc.DatabaseFactory;
import ru.pb.global.dao.jdbc.DatabaseUtils;
import ru.pb.global.enums.Modes;
import ru.pb.global.enums.item.ItemConsumeType;
import ru.pb.global.enums.item.ItemRepair;
import ru.pb.global.enums.item.ItemSlotType;
import ru.pb.global.enums.item.ItemType;
import ru.pb.global.models.Item;

public class MapsDaoImpl implements MapsDao {

	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	private List<Byte> mapsTags = new ArrayList<Byte>();
	private List<Short> mapsModes = new ArrayList<Short>();
	private Map<Integer, Integer> mapsLists = new ConcurrentSkipListMap<Integer, Integer>();
	
	public MapsDaoImpl()
	{
		this.load();
	}
	
	@Override
	public void load() {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			con = DatabaseFactory.getInstance().newConnection();
			statement = con.prepareStatement(GET_ALL_MAPS.getQuery());
			rs = statement.executeQuery();
			
			int lastList = 0;
			int listIdx = 0;
			while (rs.next()) {
				int id = rs.getInt("id");
				int list = rs.getInt("list");
				String name = rs.getString("name");
				String modes = rs.getString("mode");
				String tag = rs.getString("tag");
				
				if(lastList != list)
				{
					lastList = list;
					listIdx = 0;
				} 
				else listIdx++;
				
				int tagValue = tag.contentEquals("NEW") ? 1 : 0;
				int mode = 0;

				if(!modes.endsWith(","))
					modes += ",";
				
				for(String modeName : modes.split(","))
				{
					try
					{
						if(modeName.trim().length() == 0) 
							continue;
						
						Modes modeModel = Modes.valueOf(modeName);
						mode += modeModel.get();
					}
					catch(Exception e)
					{
						
					}
				}
					
				mapsTags.add((byte) tagValue);
				mapsModes.add((short) mode);
				
				int value = (int) Math.pow(2, listIdx);
				
				if(mode == 0)
					value = 0;
				
				if(!mapsLists.containsKey(list))
					mapsLists.put(list, value);
               else
               {
            	   final int oldValue = mapsLists.get(list);
            	   mapsLists.remove(list);
            	   mapsLists.put(list, oldValue + value);
               }
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.closeDatabaseCSR(con, statement, rs);
		}
	}

	@Override
	public int getMapList(int list) {
		if(mapsLists.containsKey(list))
			return mapsLists.get(list);
		return 0;
	}

	@Override
	public List<Short> getModes() {
		return this.mapsModes;
	}

	@Override
	public List<Byte> getTags() {
		return this.mapsTags;
	}

}
