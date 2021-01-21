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

import ru.pb.global.network.packets.Connection;

/**
 * Игрок
 *
 * @author sjke
 */
public class Player {
	private Long id;
	private Long accountId;
	private String name;
	private byte color;
	private Short rank;
	private Integer gp;
	private Integer exp;
	private Short pcCafe;
	private Boolean online;
	private PlayerStats stats;
	private PlayerEqipment eqipment;
	private Clan clan;

	private Connection connection;
	public static final int MAX_NAME_SIZE = 33;

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getColor() {
		return color;
	}

	public void setColor(byte color) {
		this.color = color;
	}

	public Short getRank() {
		return rank;
	}

	public void setRank(Short rank) {
		this.rank = rank;
	}

	public Integer getGp() {
		return gp;
	}

	public void setGp(Integer gp) {
		this.gp = gp;
	}

	public Integer getExp() {
		return exp;
	}

	public void setExp(Integer exp) {
		this.exp = exp;
	}

	public Short getPcCafe() {
		return pcCafe;
	}

	public void setPcCafe(Short pcCafe) {
		this.pcCafe = pcCafe;
	}

	public Boolean getOnline() {
		return online;
	}

	public void setOnline(Boolean online) {
		this.online = online;
	}

	public PlayerStats getStats() {
		return stats;
	}

	public void setStats(PlayerStats stats) {
		this.stats = stats;
	}

	public PlayerEqipment getEqipment() {
		return eqipment;
	}

	public void setEqipment(PlayerEqipment eqipment) {
		this.eqipment = eqipment;
	}

	public Clan getClan() {
		return clan;
	}

	public void setClan(Clan clan) {
		this.clan = clan;
	}

	@Override
	public String toString() {
		return "Player{" +
				"id=" + id +
				", accountId=" + accountId +
				", name='" + name + '\'' +
				", color=" + color +
				", rank=" + rank +
				", gp=" + gp +
				", exp=" + exp +
				", pcCafe=" + pcCafe +
				", online=" + online +
				", stats=" + stats +
				", eqipment=" + eqipment +
				", clan=" + clan +
				'}';
	}

	public Object[] toObject(boolean includeId) {
		int size = 8;
		if (includeId) {
			size++;
		}
		Object[] args = new Object[size];
		args[0] = this.accountId;
		args[1] = this.name;
		args[2] = this.color;
		args[3] = this.rank;
		args[4] = this.gp;
		args[5] = this.exp;
		args[6] = this.pcCafe;
		args[7] = this.online;
		if (includeId) {
			args[8] = this.id;
		}
		return args;
	}
}
