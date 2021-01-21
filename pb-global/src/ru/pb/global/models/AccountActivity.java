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

import java.util.Date;

/**
 * Активность аккаунта
 *
 * @author sjke
 */
public class AccountActivity {

	private Integer totalActive;
	private String lastIp;
	private Date enterOnServer = new Date();

	public String getLastIp() {
		return lastIp;
	}

	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}

	public Integer getTotalActive() {
		return totalActive;
	}

	public void setTotalActive(Integer totalActive) {
		this.totalActive = totalActive;
	}

	public Date getEnterOnServer() {
		return enterOnServer;
	}

	public void setEnterOnServer(Date enterOnServer) {
		this.enterOnServer = enterOnServer;
	}

	@Override
	public String toString() {
		return "AccountActivity{" +
				", lastIp='" + lastIp + '\'' +
				", totalActive=" + totalActive +
				", enterOnServer=" + enterOnServer +
				'}';
	}
}
