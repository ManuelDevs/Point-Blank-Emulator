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

/**
 * Ð¡Ñ‚Ð°Ñ‚Ð¸Ñ�Ñ‚ÐºÐ¸Ð° Ð¸Ð³Ñ€Ð¾ÐºÐ°
 *
 * @author sjke
 */
public class PlayerStats {

	private int seasonFights;
	private int seasonWins;
	private int seasonLosts;
	private int seasonKills;
	private int seasonDeaths;
	private int seasonSeriaWins;
	private int seasonKpd;
	private int seasonEscapes;
	private int fights;
	private int wins;
	private int losts;
	private int kills;
	private int deaths;
	private int seriaWins;
	private int kpd;
	private int escapes;

	public int getSeasonFights() {
		return seasonFights;
	}

	public void setSeasonFights(int seasonFights) {
		this.seasonFights = seasonFights;
	}

	public int getSeasonWins() {
		return seasonWins;
	}

	public void setSeasonWins(int seasonWins) {
		this.seasonWins = seasonWins;
	}

	public int getSeasonLosts() {
		return seasonLosts;
	}

	public void setSeasonLosts(int seasonLosts) {
		this.seasonLosts = seasonLosts;
	}

	public int getSeasonKills() {
		return seasonKills;
	}

	public void setSeasonKills(int seasonKills) {
		this.seasonKills = seasonKills;
	}

	public int getSeasonDeaths() {
		return seasonDeaths;
	}

	public void setSeasonDeaths(int seasonDeaths) {
		this.seasonDeaths = seasonDeaths;
	}

	public int getSeasonSeriaWins() {
		return seasonSeriaWins;
	}

	public void setSeasonSeriaWins(int seasonSeriaWins) {
		this.seasonSeriaWins = seasonSeriaWins;
	}

	public int getSeasonKpd() {
		return seasonKpd;
	}

	public void setSeasonKpd(int seasonKpd) {
		this.seasonKpd = seasonKpd;
	}

	public int getSeasonEscapes() {
		return seasonEscapes;
	}

	public void setSeasonEscapes(int seasonEscapes) {
		this.seasonEscapes = seasonEscapes;
	}
	
	public int getDraws() {
		return this.fights - (this.wins + this.losts);
	}

	public int getSeasonDraws() {
		return this.fights - (this.wins + this.losts);
	}

	public int getFights() {
		return fights;
	}

	public void setFights(int fights) {
		this.fights = fights;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLosts() {
		return losts;
	}

	public void setLosts(int losts) {
		this.losts = losts;
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public int getSeriaWins() {
		return seriaWins;
	}

	public void setSeriaWins(int seriaWins) {
		this.seriaWins = seriaWins;
	}

	public int getKpd() {
		return kpd;
	}

	public void setKpd(int kpd) {
		this.kpd = kpd;
	}

	public int getEscapes() {
		return escapes;
	}

	public void setEscapes(int escapes) {
		this.escapes = escapes;
	}

	@Override
	public String toString() {
		return "PlayerStats{" +
				"seasonFights=" + seasonFights +
				", seasonWins=" + seasonWins +
				", seasonLosts=" + seasonLosts +
				", seasonKills=" + seasonKills +
				", seasonDeaths=" + seasonDeaths +
				", seasonSeriaWins=" + seasonSeriaWins +
				", seasonKpd=" + seasonKpd +
				", seasonEscapes=" + seasonEscapes +
				", fights=" + fights +
				", wins=" + wins +
				", losts=" + losts +
				", kills=" + kills +
				", deaths=" + deaths +
				", seriaWins=" + seriaWins +
				", kpd=" + kpd +
				", escapes=" + escapes +
				'}';
	}
}
