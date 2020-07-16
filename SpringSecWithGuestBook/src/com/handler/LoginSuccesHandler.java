package com.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfToken;

import lombok.Data;
/*
 AuthenticationFailureHandler 인터페이스에는 로그인 실패 정보를 가지고 있었다면, 
 AuthenticationSuccessHandler 인터페이스에는 로그인 성공 정보를 가지고 있다.

1. 로그인 성공시, 어떤 URL로 Redirect 할 지 결정
- 인증 권한이 필요한 페이지에 접근했을 때 =>아직미구현
- 직접 로그인 페이지로 접근했을 때 =>구현

2. 로그인 실패 에러 세션 지우기
3. 로그인 성공시, 실패 카운터 초기화
4. 로그인 성공시, 마지막 접속 날짜 갱신 (실패카운터 초기화 로직과 비슷)
5. 이외에 방문자 수 를 업데이트 하거나, 방문 카운터를 증가시키는 등 부가 작업 추가해주면 된다.


출처: https://to-dy.tistory.com/94?category=720806 [todyDev] 
 
 */
@Data
public class LoginSuccesHandler implements AuthenticationSuccessHandler {

    private String defaultTargetUrl;
    private boolean alwaysUseDefaultTargetUrl;
    
    private String InputUserIdname;

    //출처: https://to-dy.tistory.com/94 [todyDev]
    private RedirectStrategy redirectStratgy = new DefaultRedirectStrategy();
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
    		HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    	
    	System.out.println("로그인 성공");
        //로그인 성공 시 처리
    	
//    	CsrfToken _csrf = (CsrfToken) request.getAttribute("_csrf");
    	
    	//에러세션을 지우는 메소드를 실행한다.
    	clearAuthenticationAttributes(request);
    	
//    	request.setAttribute(_csrf.getParameterName(), _csrf.getToken());
//    	System.out.println("_csrf : " + _csrf);
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	
    	System.out.println("권한 : " + auth.getAuthorities());
    	
    	
    	redirectStratgy.sendRedirect(request, response, defaultTargetUrl);
    }
    
    //출처: https://to-dy.tistory.com/94?category=720806 [todyDev]
    //로그인 과정에서 실패후 로그인에 성공한 경우, 한번이라도 실패했다면 에러가 세션에 저장되어 남게된다.
    //로그인에 성공했어도 세션에 에러가 남겨진채로 넘어갈수 없다. 따라서 에러세션을 지우는 작업을 진행해준다.
    //직접만든 메소드
    protected void clearAuthenticationAttributes(HttpServletRequest request) {
    	//세션을 받아온다.
        HttpSession session = request.getSession(false);
        //세션이 null 이면 에러가 없다는것이므로 그냥 return 처리.
        if(session==null) return;
        //WebAttributes.AUTHENTICATION_EXCEPTION 라는 이름으로 지정된 세션을 지워주면 된다.
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }



    
}
