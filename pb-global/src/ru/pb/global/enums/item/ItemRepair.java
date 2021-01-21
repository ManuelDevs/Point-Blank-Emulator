package ru.pb.global.enums.item;

/**
 * @author Felixx
 */
public class ItemRepair {
	private int repairCredits, repairPoints, repairQuantity;

	public ItemRepair(int credits, int points, int quantity) {
		repairCredits = credits;
		repairPoints = points;
		repairQuantity = quantity;
	}

	public int getRepairCredits() {
		return repairCredits;
	}

	public int getRepairPoints() {
		return repairPoints;
	}

	public int getRepairQuantity() {
		return repairQuantity;
	}

	@Override
	public String toString() {
		return "Repair{" +
				"repairCredits=" + repairCredits +
				", repairPoints=" + repairPoints +
				", repairQuantity=" + repairQuantity +
				'}';
	}
}
