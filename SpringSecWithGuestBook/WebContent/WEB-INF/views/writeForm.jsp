<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title>글쓰기</title>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
</head>
<body>

	<center>
		<!-- <form action = "write" method = "post"> -->
		<form id="frm">
			<table width="100%" border="1" cellpadding="0" cellspacing="0">
				<tr>
					<td>이름</td>
					<td><input id="name" type="text" name="name" size="10"></td>
				</tr>

				<tr>
					<td>암호</td>
					<td><input id="pwd" type="password" name="password" size="10"></td>
				</tr>

				<tr>
					<td>이메일</td>
					<td><input id="email" type="text" name="email" size="30"></td>
				</tr>

				<tr>
					<td>내용</td>
					<td><textarea id="text" rows="5" cols="50" name="content"></textarea></td>
				</tr>
				<tr>
					<td colspan="2">
					<!--  <button id="btn" type="button" value="글남기기"></button>-->
					<input id="btn" type="button" value="글남기기"> 
					</td>
				</tr>
			</table>
		</form>
		<!-- </form> -->

	</center>

	<script>
		$("#btn").click(function() {
			alert("btn 클릭")
			var name = $("#name").val();
			var pwd = $("#pwd").val();
			var email = $("#email").val();
			var text = $("#text").val();

			$.ajax({
				url : "/write2",
				type : "post",
				data : {
					'name' : name,
					'pwd' : pwd,
					'email' : email,
					'text' : text
				},
				success : function(data) {
					alert("ajax 로 방명록에 글을 등록하였습니다.");
					location.href = "list";
				},
				error : function() {
					alert("writeForm ajax error");
				}
			});
		});
	</script>

</body>

</html>