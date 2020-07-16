package com.dto;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GuestBookDto {

	private int guestbookId;
	private Timestamp register;
	private String name;
	private String email;
	private String password;
	private String content;
	
}
