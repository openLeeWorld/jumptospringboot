package com.mysite.sbb.user;

import lombok.Getter;

@Getter
public enum UserRole {
	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER"); // 상수 선언 및 ㄱ밧 부여
	
	UserRole(String value) {
		this.value = value;
	}
	
	private String value;
}
