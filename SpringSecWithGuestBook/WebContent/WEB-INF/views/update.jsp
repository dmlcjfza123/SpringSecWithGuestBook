<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%
	boolean isSuccess = (boolean) request.getAttribute("isSuccess");
	if (isSuccess) {
%>
<script language="JavaScript">
	alert("���� �����Ͽ����ϴ�.");
	location.href = "list.do";
</script>
<%
	} else {
%>
<script language="JavaScript">
	alert("��ȣ���ٸ��ϴ�.");
	history.go(-1);
</script>
<%
	}
%>