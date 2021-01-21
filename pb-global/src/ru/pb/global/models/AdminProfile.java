package ru.pb.global.models;

/**
 * User: Grizly
 * Date: 19.12.13
 * Time: 1:32
 */
public class AdminProfile {
	public Long accountID;
	public int level;

	public Long getAccId() {
		return accountID;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setAccId(Long accountId) {
		this.accountID = accountId;
	}


}
