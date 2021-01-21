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
 * Обьект описывающий ТОВАР
 *
 * @author Felixx
 */
public class Good {

	private final int good_id, priceCredits, pricePoints, stockType, quantity;
	private Item item;

	public Good(int good_id, Item item, int quantity, int priceCredits, int pricePoints, int stockType) {
		this.good_id = good_id;
		this.item = item;
		this.quantity = quantity;
		this.priceCredits = priceCredits;
		this.pricePoints = pricePoints;
		this.stockType = stockType;
	}

	public Item getItem() {
		return item;
	}

	public int getGoodId() {
		return good_id;
	}

	public int getQuantity() {
		return quantity;
	}

	public int getPriceCredits() {
		return priceCredits;
	}

	public int getPricePoints() {
		return pricePoints;
	}

	public int getStockType() {
		return stockType;
	}

	@Override
	public String toString() {
		return "Good{" +
				"id=" + good_id +
				", item=" + item +
				'}';
	}
}
