<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page errorPage = "/error.jsp" %>
<% 
boolean isSuccess = (boolean)request.getAttribute("isSuccess");
if(isSuccess){	
%>
<script type="text/javascript">
alert("방명록에 글을 등록하였습니다.");
location.href = "list";
</script>
<%
}
%>