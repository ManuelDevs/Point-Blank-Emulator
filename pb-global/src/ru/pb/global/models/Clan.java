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
 * Клан
 *
 * @author sjke, DarkSkeleton
 */
public class Clan {

	public static final int CLAN_NAME_SIZE = 17;
	private Long id;
	private String name;
	private Short rank;
	private Long ownerId;
	private int dateCreated; //временно
	private Short color;
	private Short logo1;
	private Short logo2;
	private Short logo3;
	private Short logo4;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getRank() {
		return rank;
	}

	public void setRank(Short rank) {
		this.rank = rank;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public void setDateCreated(int date) {
		this.dateCreated = date;
	}

	public int getDateCreated() {
		return dateCreated;
	}

	public Short getColor() {
		return color;
	}

	public void setColor(Short color) {
		this.color = color;
	}

	public Short getLogo1() {
		return logo1;
	}

	public void setLogo1(Short logo1) {
		this.logo1 = logo1;
	}

	public Short getLogo2() {
		return logo2;
	}

	public void setLogo2(Short logo2) {
		this.logo2 = logo2;
	}

	public Short getLogo3() {
		return logo3;
	}

	public void setLogo3(Short logo3) {
		this.logo3 = logo3;
	}

	public Short getLogo4() {
		return logo4;
	}

	public void setLogo4(Short logo4) {
		this.logo4 = logo4;
	}

	@Override
	public String toString() {
		return "Clan{" +
				"id=" + id +
				", name='" + name + '\'' +
				", rank=" + rank +
				", ownerId=" + ownerId +
				", dateCreated=" + dateCreated +
				", color=" + color +
				", logo1=" + logo1 +
				", logo2=" + logo2 +
				", logo3=" + logo3 +
				", logo4=" + logo4 +
				'}';
	}

	public Object[] toObject() {

		Object[] args = new Object[8];
		args[0] = this.getName();
		args[1] = this.getRank();
		args[2] = this.getOwnerId();
		args[3] = this.getColor();
		args[4] = this.getLogo1();
		args[5] = this.getLogo2();
		args[6] = this.getLogo3();
		args[7] = this.getLogo4();
		return args;
	}
}
