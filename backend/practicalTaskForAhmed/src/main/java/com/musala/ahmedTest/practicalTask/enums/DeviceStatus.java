package com.musala.ahmedTest.practicalTask.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DeviceStatus {
	ONLINE("online"), OFFLINE("offline");

	@JsonValue
	private final String text;

	DeviceStatus(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
