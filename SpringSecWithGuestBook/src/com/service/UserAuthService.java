package com.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.dao.UserDao;
import com.dto.UserDto;

import lombok.Data;

/*
 UserDetailsService 인터페이스는 DB에서 유저 정보를 가져오는 역할을 한다. 
 해당 인터페이스의 메소드에서 DB의 유저 정보를 가져와서 AuthenticationProvider 인터페이스로 유저 정보를 리턴하면, 그 곳에서 사용자가 입력한 정보와 DB에 있는 유저 정보를 비교한다. 
 지금 우리가 할 것은 유저 정보를 가져오는 인터페이스를 구현하는 것이다
여기에서 필요한 인터페이스는 UserDetails 인터페이스와 UserDetailsService 인터페이스이다

출처: https://to-dy.tistory.com/86?category=720806 [todyDev]
 */

@Data
public class UserAuthService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	/*
	 사용자의 정보를 담을 객체(UserDetails를 구현한 UserDto) 를 만들었으니, 
	 DB에서 유저정보를 직접 가져오는 인터페이스를 구현해보자.
	 UserDetailsService 인터페이스에는 DB에서 유저 정보를 불러오는 중요한 메소드가 존재한다. 
	 바로 loadUserByUsername() 메소드이다. 이메소드에서 유저 정보를 불러오는 작업을 하면 된다.
	 유저정보를 UserDto 형으로 가져와서 가져온 사용자 정보를 유/무에 따라 리턴 시키면 된다.

	출처: https://to-dy.tistory.com/86?category=720806 [todyDev]
	 */
	
	// loadUserByUsername 메소드의 인자 username 대신에 다른이름으로 쓰고싶다면,
	// SecurityConfig.java - .usernameParameter("userId") 여기서 세팅해준 이름이
	// loadUserByUsername의 파라미터 명칭으로 붙일 수 있게된다.
	// 주의할점은 jsp 단의 input 태그중 userId 에 해당되는 name이 파라미터로 전달되므로 전달될 name
	// 3가지(config쪽,메소드파라미터명칭,jsp의 name)를 맞춰주면 되겠다.
	// 참고 : https://dublin-java.tistory.com/31
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		// DB 혹은 유저 저장소에서 유저 정보를 로드한다.

		UserDto userDto = userDao.getUser(userId);
		
		System.out.println("in UserAuthService - loadUserByUsername 메소드 호출 진입");
		
		if (userDto == null) {
			System.out.println("userDto 못찾음");
			throw new UsernameNotFoundException("No user found with userId" + userDto.getUserId());
		}

		//version1. 접근권한도 여기서 줘보기.
		// 여기서부터 아래 return 전까지는 원래 provider에서 접근권한을 체크해서 줘야하나보다.
		// 참고 : https://bghgu.tistory.com/15

//		Collection<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
//		roles.add(new SimpleGrantedAuthority("ROLE_USER"));
//		UserDetails user = new User(userId, userDto.getPassword(), roles);
//		return user;
		
		
		//version2. 찾은 user만 줘보기.
		return userDto;
		
		/*
		 * 설명) 사용자가 입력한 ID/PWD 를 검증하기위해 저장소에 그 ID/PWD 가 있는지 확인하는 소스이다. 저장소는 인메모리가 될수도있고,
		 * 이것처럼 DB 가 될수도있고 어떠한것(페이스북,네이버등등) 도 될수있다. userService는 DB 에 접근하기위해 내가 만든
		 * 서비스이다. 그냥 DAO 를 만들어도되고 DB에 접근하는 아무거나 만들면된다. 중요한것은 loadUserByUsername 함수안에서는
		 * 사용자가 입력한 username에 대한 데이터를 못찾으면 UsernameNotFoundException 예외를 날려주고 찾으면
		 * UserDetails 객체에 값들 (이름,비밀번호, 해당사용자의 롤) 을 넣어서 리턴시켜준다는것이다. 저 위의 소스에서는 누구든지
		 * ROLE_USER 가 되는데, DB에 사용자테이블에 맞춤식으로 넣는게 일반적일것이다. 저렇게 리턴된 객체를 가지고 스프링 내부 에서 인증에
		 * 대한 판단을 한다.
		 * 
		 * 
		 * 출처: https://hamait.tistory.com/325 [HAMA 블로그]
		 */
	}
}
