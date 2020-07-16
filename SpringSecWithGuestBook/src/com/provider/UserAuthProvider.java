package com.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dto.UserDto;

import lombok.Data;

/*
AuthenticationProvider 인터페이스는 화면에서 입력한 로그인 정보와 DB에서 가져온 사용자의 정보를 비교해주는 인터페이스이다. 
해당 인터페이스에 오버라이드되는 authenticate() 메서드는 화면에서 사용자가 입력한 로그인 정보를 담고 있는 Authentication 객체를 가지고 있다.
앞서 DB에서 사용자의 정보를 가져오는 건 UserDetailsService 인터페이스에서 loadUserByUsername() 메서드로 구현했다.
따라서 메서드에서 loadUserByUsernmae() 메서드를 이용해 DB에서 사용자 정보를 가져와서 Authentication 객체에서 화면에서 가져온 로그인 정보와 비교하면 된다.
AuthenticationProvider 인터페이스는 인증에 성공하면 인증된 Authentication 객체를 생성하여 리턴하기 때문에 비밀번호, 계정 활성화, 잠금 모든 부분에서 확인이 되었다면 리턴해주도록 하자.

출처: https://to-dy.tistory.com/87?category=720806 [todyDev]
*/

@Data
public class UserAuthProvider implements AuthenticationProvider {
	
	//참고 https://yakolla.tistory.com/49
	//private static final Logger logger = org.slf4j.LoggerFactory.getLogger(GuestBookContoller.class);

    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        /**
         * 프로젝트 내부에서 로그인 프로세스를 진행할 때 로직을 생성한다.
         * 기본적인것을 사용하려면 DaoAuthenticationProvider(스프링 시큐리티 jar파일 내부에) 있는 프로바이더를 사용하면 됨
         */
    	
    	System.out.println("in UserAuthProvider - authenticate 메소드 진입");
    	
    	//String userId = authentication.getName();
    	String userId = (String)authentication.getPrincipal();
    	
    	String password = (String) authentication.getCredentials();
    	
    	//logger.info("Welcome authenticate {}", userId + " / " +password);
    	
    	
    	//version4.
    	UserDto userDto = (UserDto) userDetailsService.loadUserByUsername(userId);
    	
    	System.out.println("in UserAuthProvider - userDto.id : " + userDto.getUserId());
    	System.out.println("in UserAuthProvider - userDto.pwd : " + userDto.getPassword());
    	
    	//입력된 비밀번호와 DB에 저장된 비밀번호가 일치하지 않는다면 잘못된 비밀번호 에러를 던진다.
    	if(!matchPassword(password, userDto.getPassword())) {
    		throw new BadCredentialsException(userId);
    	}
    	
    	//휴면계정에대한 처리를 하고싶은경우 이와같이 할 수도 있다.
//    	if(!userDto.isEnabled()) {
//            throw new BadCredentialsException(username);
//        }
    	
    	//계정이 인증됬다면 UsernamePasswordAuthenticationToken 객체에 화면에서 입력한 정보와 DB에서 가져온 권한을 담아 리턴한다.
    	return new UsernamePasswordAuthenticationToken(userId, password, userDto.getAuthorities());
    	
    	///////////////////////////////////////////////////////////////////////
    	
    	//version1. provider에서 세팅된 권한까지 받아온 userdetail로 처리
//    	UserDetails user = null;
//    	
//    	user = userDetailsService.loadUserByUsername(userId);
//
//      return new UsernamePasswordAuthenticationToken(uesr,password);
    	
    	///////////////////////////////////////////////////////////////////////
    	
    	//version2. service에 있는 권한부여를 provider로 가져와봄.
    	
//    	List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
//        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
//
//    	UsernamePasswordAuthenticationToken rslt = new UsernamePasswordAuthenticationToken(userId,password,roles);
//    	
    	 
    	//https://osc131.tistory.com/88 에서 아래 코드 보고 힌트 얻음.
    	/*
    	User user= new User();
        user.setId(user_id);
        user.setPw(user_pw);
        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(user_id, user_pw, roles);
        result.setDetails(user);
    	 */
//    	
//    	rslt.setDetails(userDetailsService.loadUserByUsername(userId));
//    	
//      return rslt;
        
    	//////////////////////////////////////////////////////////////////////
    	
        //version3. setDetails 없이 리턴
    	//참고 https://to-dy.tistory.com/87?category=720806
        //UserDetails user = userDetailsService.loadUserByUsername(userId);
        
        //return new UsernamePasswordAuthenticationToken(userId, password,user.getAuthorities());
    }

    //AuthenticationProvider 인터페이스가 지정된 Authentication 객체를 지원하는 경우에 true를 리턴한다.
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
    
    //사용자가 입력한 비밀번호와 이미 DB에 저장된 비밀번호를 비교해서 boolean으로 리턴 시켜주는 메소드 작성한것임.
    private boolean matchPassword(String inputPwd, String password) {
    	return inputPwd.equals(password);
    }
}
