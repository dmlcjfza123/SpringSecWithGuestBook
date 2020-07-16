<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- 사용자 정보를 화면에 가져오는 방법에는 여러가지가 있다. 
bean이나 controller에서 가져오거나 annotation을 이용하는 등등 찾아보면 다양한 방법들이 있다.
크게보면, Spring Security에서 자체적 제공방법 하나, HttpServletRequest 객체에서 가져오는 방법 하나.
그 중에서 나는 SpringContextHolder를 이용해서 spring security 자체적으로 제공하는 방법을 사용할 것이다.

출처: https://to-dy.tistory.com/82?category=720806 [todyDev] 
자세한 출처 : https://zgundam.tistory.com/51?category=430446
-->

<!-- 먼저 해야할것 : Spring Security 에서 제공하는 jsp 페이지에서 사용할 수 있는 Custom Tag 등록해주자. -->
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!-- SecurityContextHolder에서 Authentication을 가져와 제공 받으려면 이하 태그 두개를 선언시켜줘야함. -->
<%@ page
	import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ page import="org.springframework.security.core.Authentication"%>

<%
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	//auth.getPrincipal(); 메소드는 인증에 성공한 상태인경우 사용자의 정보가 담긴 Object 객체를 return 해주거나,
	//인증하지 않은 상태에서는 anonymousUser 라는 문자열이 담긴 String 객체를 return 시켜준다.
	Object principal = auth.getPrincipal();

	String userId = "";
	if (principal != null) {
		userId = auth.getName();
	}
%>
<!-- access="isAuthenticated()" 의 의미 : 인증된 이용자일때 해당아래 태그의 내용을 보여주게된다. -->
<!-- access="isAnonymous()" 로 해두면, 인증하지 않은 사용자는 로그인 버튼이 보이게 할때 유용하다. -->
<html>
<head>
<link href="/css/header_style.css" rel="stylesheet" type="text/css">
<style type = "text/css">
</style>
</head>
<sec:authorize access="isAuthenticated()">
	<h3><%=userId%>님, 반갑습니다.
	</h3>
	<form action="/logout" method="post">
		<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}" />

		<button class = "btn" type="submit">LOGOUT</button>

	</form>

</sec:authorize>

</html>

<!-- header -->
