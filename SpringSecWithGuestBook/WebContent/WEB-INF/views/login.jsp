<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<body>
	<form action="/loginProcess" method="post">
	<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}" />
		<table width="100%" border="1" cellpadding="1" cellspacing="0">
			<tr>
				<td>아이디</td>
				<td><input type="text" name="userId" size="10"></td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="password" size="10"></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="로그인"></td>
			</tr>
		</table>
	</form>
</body>
</html>
<%
	String loginStatus = "";
	loginStatus = (String)request.getParameter("status");
	//System.out.println("in login.jsp - loginStatus : " + loginStatus);
	//로그인이 성공했을때는 loginStatus 에 null 값이 들어간다.
	if(loginStatus != null && loginStatus.equals("fail")){
		String userId = (String) request.getAttribute("userId");
		String password = (String) request.getAttribute("password");
		String errorMsg = (String) request.getAttribute("ERRORMSG");
		
		System.out.println("in login.jsp - userId : " + userId);
		System.out.println("in login.jsp - password : " + password);
		System.out.println("in login.jsp - errorMsg : " + errorMsg);
%>
<script language="JavaScript">
	alert("<로그인 실패>\n" + "아이디 : <%=userId%>\n비밀번호 : <%=password%>");
</script>
<%
	}
%>