/*
 * Java Server Emulator Project Blackout / PointBlank
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Authors: DarkSkeleton, sjke, Felixx
 * Copyright (C) 2013 PBDevâ„¢
 */

package ru.pb.global.models;

import ru.pb.game.network.client.packets.server.PROTOCOL_BATTLE_READYBATTLE_ACK;
import ru.pb.game.network.client.packets.server.PROTOCOL_ROOM_SLOT_INFO_ACK;
import ru.pb.global.enums.RoomBalance;
import ru.pb.global.enums.RoomState;
import ru.pb.global.enums.RoomType;
import ru.pb.global.enums.SlotState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Room {

	public static final int ROOM_NAME_SIZE = 23;
	public static final int ROOM_PASSWORD_SIZE = 4;
	public static final int[] TIMES = new int[] { 3, 5, 7, 5, 10, 15, 20, 25, 30 };
	public static final int[] KILLS = new int[] {  60, 80, 100, 120, 140, 160 };
	public static final int[] ROUNDS = new int[] { 1, 2, 3, 5, 7, 9 };
	public static final int[] RED_TEAM = new int[] {0, 2, 4, 6, 8, 10, 12, 14};
	public static final int[] BLUE_TEAM = new int[] {1, 3, 5, 7, 9, 11, 13, 15};
	
	
	private final RoomSlot[] ROOM_SLOT = new RoomSlot[16];
	private ConcurrentHashMap<Long, Player> players = new ConcurrentHashMap<Long, Player>();
	private int id;
	private String name;
	private int mapId;
	private int type;
	private int stage4v4;
	private int allWeapons;
	private int randomMap;
	private int limit;
	private int seeConf;
	private RoomBalance autobalans;
	private Player leader;
	private boolean figth;
	private int slots;
	private String password = "";
	private int special;
	private int ping;
	private RoomState state;
	
	private int killMask;
	private int timeLost;

	private int redKills;
	private int redDeaths;

	private int blueKills;
	private int blueDeaths;

	private int aiCount;
	private int aiLevel;

	private int channelId = -1;
	
	private int totalSpawns;
	
	public Room() {
		this.totalSpawns = 0;
		this.state = RoomState.READY;
		for (int i = 0; i < ROOM_SLOT.length; i++) {
			ROOM_SLOT[i] = new RoomSlot();
			ROOM_SLOT[i].setId(i);
		}
	}

	public int increaseSpawns() {
		return totalSpawns++;
	}
	
	public int getValidSlots() {
		int count = 0;
		for(RoomSlot slot : ROOM_SLOT)
			if(slot.getState() != SlotState.SLOT_STATE_CLOSE)
				count++;
		return count;
	}
	
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public void setState(RoomState state) {
		this.state = state;
	}
	
	public RoomState getState() {
		return state;
	}
	
	public boolean isBot() {
		return special == 6 || special == 8 || special == 9;
	}
	
	public int getPing() {
		return ping;
	}
	
	public void setPing(int ping) {
		this.ping = ping;
	}
	
	public boolean isPreparing() {
		return state.ordinal() >= 2;
	}
	
	public int getId() {
		return id;
	}
	
	public int getTimeByMask()
    {
        return TIMES[killMask >> 4];
    }
    public int getRoundsByMask()
    {
        return ROUNDS[killMask & 15];
    }
    public int getKillsByMask()
    {
        return KILLS[killMask & 15];
    }

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMapId() {
		return mapId;
	}

	public int getChannel() {
		return this.channelId;
	}
	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public RoomType getTypeEnum() {
		try
		{
			return RoomType.values()[type];
		}
		catch(Exception e)
		{
			return RoomType.NONE;
		}
	}
	public int getStage4v4() {
		return stage4v4;
	}

	public void setStage4v4(int stage4v4) {
		this.stage4v4 = stage4v4;
	}

	public int getAllWeapons() {
		return allWeapons;
	}

	public void setAllWeapons(int allWeapons) {
		this.allWeapons = allWeapons;
	}

	public int getRandomMap() {
		return randomMap;
	}

	public void setRandomMap(int randomMap) {
		this.randomMap = randomMap;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getSeeConf() {
		return seeConf;
	}

	public void setSeeConf(int seeConf) {
		this.seeConf = seeConf;
	}

	public RoomBalance getAutobalans() {
		return autobalans;
	}

	public void setAutobalans(int autobalans) {
		this.autobalans = RoomBalance.values()[autobalans];
	}

	public Player getLeader() {
		if (leader == null)
			setNewLeader();

		return leader;
	}

	public void setLeader(Player leader) {
		this.leader = leader;
	}

	public boolean isFigth() {
		return figth;
	}

	public void setFigth(boolean figth) {
		this.figth = figth;
	}

	public ConcurrentHashMap<Long, Player> getPlayers() {
		return players;
	}

	public int getSlots() {
		return slots;
	}
	
	public void updateSlotsWithPacket() {
		for(Player player : players.values()) {
			player.getConnection().sendPacket(new PROTOCOL_ROOM_SLOT_INFO_ACK(this));
		}
	}

	public void setNewLeader() {
		for (int i = 0; i < 16; i++) {
			if (ROOM_SLOT[i].getState() != SlotState.SLOT_STATE_CLOSE && ROOM_SLOT[i].getState() != SlotState.SLOT_STATE_EMPTY) {
				setLeader(ROOM_SLOT[i].getPlayer());
				break;
			}
		}
	}
	
	public short getSlotFlag(boolean onlySpectators, boolean onlyMissionSuccess) {
		short result = 0;
		for (int i = 0; i < 16; i++) {
			RoomSlot slot = ROOM_SLOT[i];
			if(slot.getState().ordinal() >= 9)
				result += (1 << i);
		}
		return result;
	}

	public void setSlots(int count) {
		if (count == 0) {
			count = 1;
		}
		this.slots = count;
		for (int i = 0; i < ROOM_SLOT.length; i++)
			if (i >= count)
				ROOM_SLOT[i].setState(SlotState.SLOT_STATE_CLOSE);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getSpecial() {
		return special;
	}

	public void setSpecial(int special) {
		this.special = special;
	}

	public void removePlayer(Player player) {
		if (getRoomSlotByPlayer(player) != null) {
			getRoomSlotByPlayer(player).setState(SlotState.SLOT_STATE_EMPTY);
			getRoomSlotByPlayer(player).setPlayer(null);
		}
		players.remove(player.getId());
	}

	public void addPlayer(Player player) {
		for (RoomSlot slot : ROOM_SLOT) {
			if (slot.getState() == SlotState.SLOT_STATE_EMPTY) {
				slot.setPlayer(player);
				slot.setState(SlotState.SLOT_STATE_NORMAL);
				players.put(player.getId(), player);
				break;
			}
		}
	}

	public void addPlayerToLeaderTeam(Player player) {
		RoomSlot leaderSlot = getRoomSlotByPlayer(leader);
		if (!addPlayerToTeam(player, Arrays.binarySearch(RED_TEAM, leaderSlot.getId()) >= 0 ? RED_TEAM : BLUE_TEAM)) {
			addPlayer(player);
		}
	}

	private boolean addPlayerToTeam(Player player, int[] team) {
		for (int slotId : team) {
			RoomSlot slot = getRoomSlot(slotId);
			if (slot.getState() == SlotState.SLOT_STATE_EMPTY) {
				slot.setPlayer(player);
				slot.setState(SlotState.SLOT_STATE_NORMAL);
				players.put(player.getId(), player);
				return true;
			}
		}
		return false;
	}

	public RoomSlot getRoomSlotByPlayer(Player player) {
		for (RoomSlot slot : ROOM_SLOT) {
			if (player.equals(slot.getPlayer())) {
				return slot;
			}
		}
		return null;
	}

	public void updateRoomSlotState(RoomSlot slot, SlotState state)
	{
		if(slot != null)
		{
			if(slot.getState() == state)
				return;
			
			slot.setState(state);
			this.updateSlotsWithPacket();
		}
	}
	
	public RoomSlot getRoomSlot(int slotId) {
		for (RoomSlot slot : ROOM_SLOT) {
			if (slot.getId() == slotId) {
				return slot;
			}
		}
		return null;
	}

	public RoomSlot[] getRoomSlots() {
		return ROOM_SLOT;
	}

	public RoomSlot changeTeam(Player player, int team) {
		RoomSlot slot = getRoomSlotByPlayer(player);
		for (int teamSlot : team == 0 ? RED_TEAM : BLUE_TEAM) {
			RoomSlot rslot = ROOM_SLOT[teamSlot];
			if (player.equals(rslot.getPlayer()) || rslot.getState() == SlotState.SLOT_STATE_EMPTY) {
				slot.setPlayer(null);
				slot.setState(SlotState.SLOT_STATE_EMPTY);
				rslot.setPlayer(player);
				rslot.setState(SlotState.SLOT_STATE_NORMAL);
				return rslot;
			}
		}
		return null;
	}

	public int getKillMask() {
		return killMask;
	}

	public void setTimeLost(int time) {
		timeLost = time;
	}

	public int getTimeLost() {
		return timeLost;
	}

	/**
	 * Ð£Ñ�Ñ‚Ð°Ð½Ð°Ð²Ð»Ð¸Ð²Ð°ÐµÑ‚ Ð¼Ð°Ñ�ÐºÑƒ Ð´Ð»Ñ� Ð¾Ð¿Ñ€ÐµÐ´ÐµÐ»ÐµÐ½Ð¸Ñ�, ÐºÐ¾Ð³Ð´Ð° Ð½ÑƒÐ¶Ð½Ð¾ Ð·Ð°ÐºÐ¾Ð½Ñ‡Ð¸Ñ‚ÑŒ Ð±Ð¾Ð¹.
	 *
	 * @param variants
	 */
	public void setKillMask(int variants) {
		this.killMask = variants;
	}

	public int getRedKills() {
		return redKills;
	}

	public void setRedKills(int kills) {
		redKills = kills;
	}

	public int getRedDeaths() {
		return redDeaths;
	}

	public void setRedDeaths(int deaths) {
		redDeaths = deaths;
	}

	public int getBlueKills() {
		return blueKills;
	}

	public void setBlueKills(int kills) {
		blueKills = kills;
	}

	public int getBlueDeaths() {
		return blueDeaths;
	}

	public void setBlueDeaths(int deaths) {
		blueDeaths = deaths;
	}

	public int getAiCount() {
		return aiCount;
	}

	public void setAiCount(int aiCount) {
		this.aiCount = aiCount;
	}

	public int getAiLevel() {
		return aiLevel;
	}

	public void setAiLevel(int aiLevel) {
		this.aiLevel = aiLevel;
	}

	public void startRoom(boolean byCountdown)
	{	
		List<RoomSlot> startingSlot = new ArrayList<RoomSlot>();
		
		for(Player player : players.values())
		{
			RoomSlot slot = getRoomSlotByPlayer(player);
			if(slot == null || slot.getState() != SlotState.SLOT_STATE_READY)
				continue;
			
			slot.setState(SlotState.SLOT_STATE_LOAD);
			startingSlot.add(slot);
		}
		
		for(Player player : players.values())
		{
			RoomSlot slot = getRoomSlotByPlayer(player);
			if(slot == null || slot.getState() == SlotState.SLOT_STATE_LOAD)
				continue;
			
			player.getConnection().sendPacket(new PROTOCOL_BATTLE_READYBATTLE_ACK(this, startingSlot));
		}
	}
	
	public List<Player> getPlayersByState(SlotState state, int type)
	{
		List<Player> result = new ArrayList<Player>();
		for(Player player : players.values())
		{
			RoomSlot slot = getRoomSlotByPlayer(player);
			if(type == 0 && slot.getState() == state || type == 1 && slot.getState().ordinal() > state.ordinal())
			{
				result.add(player);
			}
		}
		return result;
	}
	
	public void resetRoom()
	{
		this.totalSpawns = 0;
		
		for (Player member : getPlayers().values())
			if (getRoomSlotByPlayer(member).getState().ordinal() >= 9) 
				getRoomSlotByPlayer(member).resetSlot();
		
		this.updateSlotsWithPacket();
	}
	
	public void calculateResults()
	{
		for (Player member : getPlayers().values()) {
			if (getRoomSlotByPlayer(member).getState().ordinal() >= 9) {
				RoomSlot slot = getRoomSlotByPlayer(member);
				slot.setAllExp((slot.getAllKills() * 6) + (slot.getAllDeath() * 2));
				slot.setAllGP((slot.getAllKills() * 5) + (int) (slot.getAllDeath() * 1.75));

				member.setExp(member.getExp() + slot.getAllExp());
				member.setGp(member.getGp() + slot.getAllGp());
				member.getStats().setFights(member.getStats().getFights() + 1);
				member.getStats().setDeaths(member.getStats().getDeaths() + slot.getAllDeath());
				member.getStats().setKills(member.getStats().getKills() + slot.getAllKills());
			}
		}
	}
	
	@Override
	public String toString() {
		return "Room{" +
				"id=" + id +
				", name='" + name + '\'' +
				", mapId=" + mapId +
				", type=" + type +
				", stage4v4=" + stage4v4 +
				", allWeapons=" + allWeapons +
				", randomMap=" + randomMap +
				//", valueToWin=" + valueToWin +
				", limit=" + limit +
				", seeConf=" + seeConf +
				", autobalans=" + autobalans +
				", leader=" + leader.getName() +
				", figth=" + figth +
				", players=" + players.size() +
				", slots=" + slots +
				", password='" + password + '\'' +
				", special=" + special +
				'}';
	}
}
