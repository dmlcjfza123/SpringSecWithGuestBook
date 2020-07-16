package com.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dto.UserDto;

@Component
public class UserDao {
	@Autowired
	private SqlSession sqlSession;
	
	public void insert(UserDto dto) {
		this.sqlSession.insert("MEMBER.insertUser", dto);
	}
	
	public UserDto getUser(String userId) {
		return this.sqlSession.selectOne("MEMBER.getUser", userId);
	}
	
	public void update(UserDto dto) {
		this.sqlSession.update("MEMBER.updateUser", dto);
	}
	
	public void delete(String userId) {
		this.sqlSession.delete("MEMBER.deleteUser", userId);
	}
}
