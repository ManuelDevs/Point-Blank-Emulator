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
 * Copyright (C) 2013 PBDev™
 */

package ru.pb.global.models;

import ru.pb.global.enums.SlotState;

/**
 * Слот в комнате
 *
 * @author sjke
 */
public class RoomSlot {

	private int id;
	private int allKills, oneTimeKills;
	private int lastKillMessage, killMessage;
	private int allDeath;
	private int botScore;
	private Player player;
	private int playing;
	private int allExp;
	private int allGP;
	private int spawnCount;
	
	public int lastKillState;
	public boolean repeatLastState;

	private SlotState state = SlotState.SLOT_STATE_EMPTY;

	public int getId() {
		return id;
	}
	
	public void setPlaying(int playing) {
		this.playing = playing;
	}
	
	public int getPlaying() {
		return playing;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public SlotState getState() {
		return state;
	}

	public void setState(SlotState state) {
		this.state = state;
	}

	public void setAllKills(int kills) {
		allKills = kills;
	}

	public int getAllKills() {
		return allKills;
	}

	public void setOneTimeKills(int kills) {
		oneTimeKills = kills;
	}

	public int getOneTimeKills() {
		return oneTimeKills;
	}

	public void setLastKillMessage(int message) {
		lastKillMessage = message;
	}

	public int getLastKillMessage() {
		return lastKillMessage;
	}

	public int getKillMessage() {
		return killMessage;
	}

	public void setKillMessage(int message) {
		killMessage = message;
	}

	public void setAllDeahts(int death) {
		allDeath = death;
	}

	public int getAllDeath() {
		return allDeath;
	}

	public void setBotScore(int score) {
		botScore = score;
	}

	public int getBotScore() {
		return botScore;
	}

	public int getAllExp() {
		return allExp;
	}

	public int getAllGp() {
		return allGP;
	}

	public void setAllExp(int allExp1) {
		allExp = allExp1;
	}

	public void setAllGP(int allGP1) {
		allGP = allGP1;
	}

	public void resetSlot() {
		this.playing = 0;
		this.spawnCount = 0;
		this.setAllDeahts(0);
		this.setAllDeahts(0);
		this.setBotScore(0);
		this.setLastKillMessage(0);
		this.setAllExp(0);
		this.setAllGP(0);
		this.state = SlotState.SLOT_STATE_NORMAL;
	}
	
	public int increaseSpawn() {
		return ++this.spawnCount;
	}
	
	@Override
	public String toString() {
		return "RoomSlot{" +
				"id=" + id +
				", state=" + state +
				'}';
	}
}
