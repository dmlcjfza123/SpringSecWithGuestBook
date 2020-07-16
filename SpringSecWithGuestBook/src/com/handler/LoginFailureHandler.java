package com.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import lombok.Data;

//참고 https://to-dy.tistory.com/92?category=720806
@Data
public class LoginFailureHandler implements AuthenticationFailureHandler {
	//로그인 실패시 보여줄 화면 url
    private String defaultFailureUrl;
    
    //Id 값이 들어오는 Input 태그 name
    private String inputUserIdname;
    //password값이 들어오는 Input 태그 name
    private String inputPasswordname;
    //로그인페이지에서 에러메시지 가져올때 사용할 변수명
    private String errorMsg;

    //로그인에실패하게되면 해당 메서드가 실행된다.
    /*
	 * HttpServletRequest 객체: 웹에서 넘어온 Request 값을 가지고 있는 객체
	 * HttpServletResponse 객체: 출력을 정의할 수 있는 객체
	 * AuthenticationException 객체: 로그인 실패 정보를 가지고 있는 객체
	 *
	 * 출처: https://to-dy.tistory.com/92?category=720806 [todyDev]
	 */ 
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, 
    		HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //로그인 실패 시 처리
    	
    	//아이디와 비밀번호를 getParameter 메서드로 가져온다.
    	String userId = request.getParameter(inputUserIdname);
    	String password = request.getParameter(inputPasswordname);
    	//세션을 통해 에러메시지를 가져온다.
    	String errormsg = exception.getMessage();
    	
    	//input태그의 name에 가져온 아이디와 비밀번호 정보를 세팅해준다.
    	request.setAttribute(inputUserIdname, userId);
    	request.setAttribute(inputPasswordname, password);
    	
    	//에러메시지 세팅
    	request.setAttribute(errorMsg, errormsg);
    	
    	//System.out.println("로그인 실패");
    	//System.out.println("in LoginFailureHandler - inputUserIdname/userId : " +inputUserIdname +" / "+ userId);
    	//System.out.println("in LoginFailureHandler - inputPasswordname/password : "+inputPasswordname+" / " + password);
    	//System.out.println("in LoginFailureHandler - errorMsg/errormsg : " +errorMsg +" / "+ errormsg);
    	
    	//보여줄 화면으로 forward 해준다.
    	request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
    }
}
