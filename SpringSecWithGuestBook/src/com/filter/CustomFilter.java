package com.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class CustomFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest arg0, HttpServletResponse arg1, 
			FilterChain arg2)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//System.out.println("customFilter");
		
//		arg0.setAttribute("asdf", "asdf");
//		
//		if(arg0.getRequestURI().contentEquals("/login")) {
//			
//		}
		
		//doFilter 해줘야 다음 필터가 읽힌다. 이건 꼭 해줘야됨. 안해주면 다음핕터로 못넘어감. 그러면 무한로딩걸림.
		
		
		//req
		
		arg0.setAttribute("greeting", "hello");
		
		arg2.doFilter(arg0, arg1);
		//res
	}
	
}
