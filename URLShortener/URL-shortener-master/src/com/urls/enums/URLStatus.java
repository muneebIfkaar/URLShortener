package com.urls.enums;

public enum URLStatus {
	
	ACTIVE(1),
	EXPIRED(2),
	DELETED(3);
	
	private int value;
	
	private URLStatus(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static URLStatus valueOf(int val) {
		
		switch(val) {
		case 1:
			return ACTIVE;
		case 2:
			return URLStatus.EXPIRED;
		case 3:
			return URLStatus.DELETED;
		}
		
		return null;
		
	}
}
