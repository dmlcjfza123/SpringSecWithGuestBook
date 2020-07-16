package com.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dto.GuestBookDto;
import com.util.GuestBookException;

@Component
public class GuestBookDao {

	@Autowired
	//private SqlSessionFactory sqlSessionFactory;
	private SqlSession sqlSession;

	public List<GuestBookDto> getGuestBook() {
		return this.sqlSession.selectList("GuestBook.getGuestBook");
	}

	public List<GuestBookDto> getGuestBookById(GuestBookDto dto) {
		return this.sqlSession.selectList("GuestBook.getGuestBookById", dto);
	}

	public GuestBookDto getGuestBookById(int guestbookId) {
		return (GuestBookDto) this.sqlSession.selectOne("GuestBook.getGuestBookById", guestbookId);
	}

	public List<GuestBookDto> getGuestBookByIds(int guestbookId, int adfs) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("guestbookId", guestbookId);
		paramMap.put("guestbookId2", adfs);
		return this.sqlSession.selectList("GuestBook.getGuestBookById", paramMap);
	}

	public void insert(GuestBookDto dto) {
		this.sqlSession.insert("GuestBook.insertGuestBook", dto);
	}

	public void update(GuestBookDto dto) {
		this.sqlSession.update("GuestBook.updateGuestBook", dto);
	}

	public void delete(GuestBookDto dto) {
		this.sqlSession.delete("GuestBook.deleteGuestBook", dto);
	}

	public int getCount() {
		try {
			int rslt = (Integer) this.sqlSession.selectOne("GuestBook.getCountGuestBook");
			return rslt;
		}
		catch(RuntimeException re) {
			throw new GuestBookException(re.getMessage());
		}
	}

	public List<GuestBookDto> getList(int startRow, int endRow) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("start", startRow-1);
		paramMap.put("end", endRow-startRow);
		return this.sqlSession.selectList("GuestBook.getListGuestbook", paramMap);
	}

	public GuestBookDto getGuestBook(int guestBookId) throws RuntimeException {
		return (GuestBookDto) this.sqlSession.selectOne("GuestBook.getGuestbook", guestBookId);
	}

}
