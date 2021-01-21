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

/**
 * @author: Felixx
 * Date: 18.10.13
 * Time: 3:38
 */
public class LevelUpInfo {
	private byte rank;
	private int needExp;
	private int allExp;
	private int rewardGp;
	private Integer[] rewards;

	public LevelUpInfo(byte rank, int needExp, int allExp, int rewardGp, Object rewards) {
		this.rank = rank;
		this.needExp = needExp;
		this.allExp = allExp;
		this.rewardGp = rewardGp;
		this.rewards = (Integer[]) rewards;
	}

	public byte getRank() {
		return rank;
	}

	public int getNeedExp() {
		return needExp;
	}

	public int getAllExp() {
		return allExp;
	}

	public int getRewardGp() {
		return rewardGp;
	}

	public Integer[] getRewards() {
		return rewards;
	}
}
