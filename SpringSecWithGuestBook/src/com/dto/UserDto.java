package com.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

/**
 * 시큐리티에서 사용하는 유저 정보 Dto 객체
 */

/*
Spring Security에서 사용자의 정보를 담는 인터페이스는 UserDetails 인터페이스이다.
우리가 이 인터페이스를 구현한 클래스를 Spring Security 에서 이 클래스를 사용자 정보로 인식하고 인증작업을 한다.
즉, UserDetails 인터페이스는 VO 역할을 한다고 보면 된다.
UserDetails 인터페이스를 구현하게되면 오버라이드 되는 메소드들이 있다.
이 메소드들만 Spring Security 가 알아서 이용하기때문에, 이와 같이 다른 정보를 추가해서 함께 사용해도 무방하다.
 
 출처: https://to-dy.tistory.com/86?category=720806 [todyDev]
*/
@Data
public class UserDto implements UserDetails {

    private String userId;
    private String password;

    /**
     * 추가적으로 필요한 변수를 지정해서 프로젝트 내에 모든 User 공통으로 사용할 수도 있음.
     */
    private Timestamp register;
    private String name;
    private String address;
    private String email;

    //계정이 갖고있는 권한 목록을 리턴한다.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /**
         * 해당 유저가 갖는 권한
         */
    	//참고 : https://yakolla.tistory.com/49
    	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();    
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    	
        return authorities;
    }

    //계정의 비밀번호를 리턴한다.
    @Override
    public String getPassword() {
        return password;
    }

    //계정의 이름(아이디쪽 폼 태그에 name으로 배치하면 id를)을 리턴한다.
    @Override
    public String getUsername() {
        return userId;
    }

    
    /////// 이하 공 통 사항 //////
    /*
       	계정이 잠겨있는 지 등등 이것들에 대해 체크할 필요가 없다면 true를 리턴해주면 된다. 
    	만약 체크할 멤버변수가 존재한다면 그 멤버변수를 리턴해주면 된다.
    	필자가 사용하는 DB에는 계정의 활성/비활성화를 확인하는 멤버변수가 존재한다.(휴면계정) 
    	따라서 isEnabled() 메소드는 해당 멤버변수를 리턴해준다.
    */

    //출처: https://to-dy.tistory.com/86?category=720806 [todyDev]
    
    
    //계정이 만료되지 않았는 지 리턴한다. (true: 만료안됨)
    @Override
    public boolean isAccountNonExpired() {
        /**
         * provider에서 해당 컬럼을 참조해서 Exception을 던진다.
         * 유저 상태값에 맞게끔 True/False 설정을 해주면 되고
         * 커스텀 provider를 개발한다면 해당 부분을 체크해주면 좋고 설계에 따라 체크를 안할 수도 있음
         */
        return true;
    }

    //계정이 잠겨있지 않았는 지 리턴한다. (true: 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        /**
         * provider에서 해당 컬럼을 참조해서 Exception을 던진다.
         * 유저 상태값에 맞게끔 True/False 설정을 해주면 되고
         * 커스텀 provider를 개발한다면 해당 부분을 체크해주면 좋고 설계에 따라 체크를 안할 수도 있음
         */
        return true;
    }

    //비밀번호가 만료되지 않았는 지 리턴한다. (true: 만료안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        /**
         * provider에서 해당 컬럼을 참조해서 Exception을 던진다.
         * 유저 상태값에 맞게끔 True/False 설정을 해주면 되고
         * 커스텀 provider를 개발한다면 해당 부분을 체크해주면 좋고 설계에 따라 체크를 안할 수도 있음
         */
        return true;
    }

    //계정이 활성화(사용가능)인 지 리턴한다. (true: 활성화)
    @Override
    public boolean isEnabled() {
        /**
         * provider에서 해당 컬럼을 참조해서 Exception을 던진다.
         * 유저 상태값에 맞게끔 True/False 설정을 해주면 되고
         * 커스텀 provider를 개발한다면 해당 부분을 체크해주면 좋고 설계에 따라 체크를 안할 수도 있음
         */
        return true;
    }
}
