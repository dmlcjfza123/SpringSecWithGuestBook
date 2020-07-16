package com.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dao.GuestBookDao;
import com.dto.GuestBookDto;

@Component
public class WriteService {
	@Autowired private GuestBookDao manager;
	
	public boolean write(GuestBookDto book) {
		//현재 시간을 book 자바빈 객체의 register 프로퍼티의 값ㄷ을 지정
		book.setRegister(new Timestamp(System.currentTimeMillis()));
		//GuestBookManager.inser() 메소드를 통해서 book 자바빈 객체에 저장된 데이터를 GUESTBOOK 테이블에 저장.
		
		try {
			manager.insert(book);
			return true;
		} catch (RuntimeException re) {
			re.printStackTrace();
			return false;
		} 
	}
}
