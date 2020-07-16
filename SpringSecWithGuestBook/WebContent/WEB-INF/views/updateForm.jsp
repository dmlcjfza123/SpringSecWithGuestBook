<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.dto.GuestBookDto"%>
<%@ page import="java.util.Map"%>
<%@ page errorPage="/error.jsp"%>

<%
	Map<String, Object> updateFormData = (Map<String, Object>) request.getAttribute("data");
	int guestbookId = (Integer) updateFormData.get("id");

	GuestBookDto book = (GuestBookDto) updateFormData.get("book");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<title>글수정</title>
</head>
<body>
	<center>
		<form id = "frm">
			<input id="Id" type="hidden" name="guestbookId" value="<%=guestbookId%>">
			<table width="100%" border="1" cellpadding="0" cellspacing="0">
				<tr>
					<td>암호</td>
					<td><input id="Pwd" type="password" name="password" size="10"><br>
						글을 쓸때 입력한 암호와 동일해야 글이 수정됩니다.</td>
				</tr>
				<tr>
					<td>이름</td>
					<td><input id="Name" type="text" name="name"
						value="<%=book.getName()%>" size="10"></td>
				</tr>
				<tr>
					<td>이메일</td>
					<td><input id="Email" type="text" name="email"
						value="<%=book.getEmail()%>" size="30"></td>
				</tr>
				<tr>
					<td>내용</td>
					<td><textarea id="Text" name="content" rows="5" cols="50"><%=book.getContent()%></textarea></td>
				</tr>
				<tr>
					<td colspan="2">
					<button type="button" id="confrmUpdate">글수정버튼</button>
					<!-- <input type= "button" id="confrmUpdate" value="글수정하기"> -->
					</td>
				</tr>
			</table>
		</form>
	</center>
	
	<script>
	
	//https://sseambong.tistory.com/183
	//dto 객체로 data 값 보내기.

		$("#confrmUpdate").click(function() {
			alert("버튼 클릭");
			submitBtn();
		});

		function submitBtn() {
			
			var guestbookId = $("#Id").val();
			guestbookId = parseInt(guestbookId);
			var password = $("#Pwd").val();
			var name = $("#Name").val();
			var email = $("#Email").val();
			//var content = $("#Text").val();
			var content = $("textarea[name=content]").val();
			//var content = document.getElementById('Text').value;
			
			alert("content :" +content );
			
			if(content == null){
				alert("content 가 null")
				content = "asd"
				alert(content);
			}

			alert("confrmUpdate 진입")
			$.ajax({
				url : "/update2",
				type : "post",
				data : {
					'guestbookId' : guestbookId,
					'password' : password,
					'name' : name,
					'email' : email,
					'content' : content
				}, 
				//data:{'password' : password},
				success : function(data) {
					console.log(data);
					alert(data);
					
					if(data){
						alert("글을 수정하였습니다.");
						location.href = "list";
					}
					else{
						alert("비밀번호가 다릅니다.")
					}

					//location.href = "list?num=1";
					//$(location).attr('href',"/list2");
				},
				error : function() {
					alert('updateForm ajax error');
				}
			});
		}
	</script>
	
</body>
</html>