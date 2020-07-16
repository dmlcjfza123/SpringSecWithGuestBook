package com.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dao.GuestBookDao;
import com.dto.GuestBookDto;

@Component
public class UpdateFormService {
	
	@Autowired private GuestBookDao manager;
	
	public Map<String,Object> getUpdateFormData(int id){
		Map<String,Object> rslt = new HashMap<String,Object>();	
		rslt.put("id",id);

		try {
			GuestBookDto book = manager.getGuestBook(id);
			rslt.put("book", book);
			return rslt;
		} catch (RuntimeException re) {
			// TODO Auto-generated catch block
			re.printStackTrace();
			return new HashMap<String,Object>();
		}
	}
}
