package com.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GuestbookPageDto {
	private int count;
	private List<GuestBookDto> bookList;
	private int currentPage;
}
