<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%
	boolean isSuccess = (boolean) request.getAttribute("isSuccess");
	if (isSuccess) {
%>
<script language="JavaScript">
	alert("글을 수정하였습니다.");
	location.href = "list.do";
</script>
<%
	} else {
%>
<script language="JavaScript">
	alert("암호가다릅니다.");
	history.go(-1);
</script>
<%
	}
%>