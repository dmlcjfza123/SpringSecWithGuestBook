package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dao.GuestBookDao;
import com.dto.GuestBookDto;

@Component
public class UpdateService {
	
	@Autowired private GuestBookDao manager;
	
	public boolean update(GuestBookDto book) {
		System.out.println("updateService - getGuestbookId : " + book.getGuestbookId());
			System.out.println("updateService - getPassword : " + book.getPassword());
			System.out.println("updateService - getName : " + book.getName());
			System.out.println("updateService - getEmail : " + book.getEmail());
			System.out.println("updateService - getContent : " + book.getContent());
			
		
		
			try {
				//변경할 GuestBook데이터를 oldBook에 저장
				GuestBookDto oldBook = manager.getGuestBook(book.getGuestbookId());
				
				//System.out.println("in UpdateService GuestbookId : " + book.getGuestbookId());
				System.out.println("in UpdateService oldBook.getPassword : " + oldBook.getPassword());
				
				//암호를 올바르게 입력했다면 방명록 데이터 변경
				if(book.getPassword().compareTo(oldBook.getPassword()) == 0){
					manager.update(book);
					System.out.println("updateService - return true");
					return true;
				}else {
					System.out.println("updateService - return false");
					return false;
				}
			} catch (RuntimeException re) {
				System.out.println("runtime exception 진입 ");
				re.printStackTrace();
				return false;
			}
	}
}
