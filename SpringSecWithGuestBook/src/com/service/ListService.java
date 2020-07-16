package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dao.GuestBookDao;
import com.dto.GuestBookDto;
import com.dto.GuestbookPageDto;

@Component
public class ListService {
	@Autowired private GuestBookDao manager;
	public static final int PAGE_SIZE = 10;
	
	//private static int LS_CallCnt = 0;

	public GuestbookPageDto getList(String pageNum) {
		
		//LS_CallCnt++;
		//System.out.println();
		//System.out.println("in ListService LS_CallCnt : " +LS_CallCnt);
		
		GuestbookPageDto rslt = GuestbookPageDto.builder().build();
		if (pageNum == null) {
			pageNum = "1";
		}
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage - 1) * PAGE_SIZE + 1;
		int endRow = currentPage * PAGE_SIZE;
		int count = 0;

		rslt.setCurrentPage(currentPage);
		List<GuestBookDto> bookList = null;
		
		//System.out.println("in ListService start of getCount method ");
		count = this.manager.getCount();
		//System.out.println("in ListService end of getCount method");
		
		//System.out.println("in ListService count : " + count);
		//System.out.println("in ListService startrow : " + startRow  );
		//System.out.println("in ListService endrow : " + endRow  );
		if (count > 0) {
			bookList = this.manager.getList(startRow, endRow);
			rslt.setCount(count);
			rslt.setBookList(bookList);
			//System.out.println("in ListService end of getList method - success");
			return rslt;
		} else {
			//System.out.println("in ListService end of getList method - fail");
			return GuestbookPageDto.builder().build();
		}
		
		
	}
}
