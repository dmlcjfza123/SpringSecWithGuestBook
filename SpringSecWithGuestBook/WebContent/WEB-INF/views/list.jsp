<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import = "java.text.SimpleDateFormat" %>
<%@ page import = "com.dto.GuestbookPageDto" %>
<%@ page import = "com.dto.GuestBookDto" %>
<%@ page import = "com.service.ListService" %>
<%@ page errorPage = "/error.jsp" %>
<%
SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
%>

<html>
<head><title>글 목록</title>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
</head>

<body>
<center>
<table width = "100%">
	<tr>
		<td><a href= "writeForm">글쓰기</a>
	</tr>
</table>

<%-- 
	if(data.getCount() ==0){
--%>
<!-- <table width = "100%" border = "1" cellpadding= "0" cellspacing="0">
<tr>
	<td bgcolor = "#e9e9e9">방명록에 저장된 글이 없습니다.</td>
</table> -->

<!-- 전체글의 개수가 0보다 큰 경우 글목록(bookList)으로부터 한개씩 GuestBook 객체를 꺼내서 내용을 출력한다. -->
<%-- } else { --%>
<table id="contentTable" width ="100%" border="1"  cellpadding= "0" cellspacing="0"></table>
<!-- 
<table id="contentTable" width ="100%" border="1"  cellpadding= "0" cellspacing="0"> -->
<%--
	for (int i=0; i<data.getBookList().size(); i++){
	//try{
		GuestBookDto  book = data.getBookList().get(i);
--%>
<%-- <tr>
	<td bgcolor = "#e9e9e9" id="test">
	<b><%= book.getName() %>(<%= book.getEmail() %>)</b>
	- <font size ="2">
	<%= formatter.format(book.getRegister()) %></font>
	<%if(book.getPassword() != null && !book.getPassword().equals("")) { %>
	<a href = "updateForm?id=<%=book.getGuestbookId() %>">[수정]</a>
	<a href = "deleteForm?id=<%=book.getGuestbookId() %>">[삭제]</a>
	<%} %>
	</font>
	</td>
</tr>
<tr>
	<td><%=book.getContent() %></td>
</tr>
<%//} catch(Exception e) { e.printStackTrace();}%>
	<% } %><!-- end of for -->
</table>
<%} %> --%>


<a class = "pageChange1">[이전]</a>
<a class = "pageChange1" ></a>
<a class = "pageChange1"> [다음]</a>


</center>

<script>
var callcnt = 0;
// [pre] [1] [2] [3] [4] [5] [6] [7] [8] [9] [10] [next]
// [11] [12] [13]
//실제처리 //참고 https://gangzzang.tistory.com/entry/%EC%A0%9C%EC%9D%B4%EC%BF%BC%EB%A6%ACjQuery-%EC%84%A0%ED%83%9D%EC%9E%90
  	function load(pageNum){
	//alert("load 진입")
  		$.ajax({
  			//async : false,
			url : "/list2",
			type : "get",
			//async: false, //동기 로 변경하면 Return 값을 얻을수있습니다.
			//안되네?
			//data:("#rsltId").serialize(),
			data : {
				'rslt' : pageNum
			},
			//자바스크립트에서 jsp 변수 사용 참고 : https://dev-t-blog.tistory.com/8 
			//참고2: http://egloos.zum.com/dreamform/v/2805065
			//JAVASCRIPT에서 JSP소스로 변수값 전달을 할 수 없습니다. JSP소스가 다 인코딩인 된다음에 동작하는게 HTML/JAVASCRIPT 이기에..
			//할수 있는 방법이라면.. JAVASCRIPT의 값을 HTML의 HIDDEN 값으로 선언하고 폼 submit 후에 다시 JSP에서 request.getParameter로 받을 수 밖에 ...
			success : function(data) {
				//console.log(data);
				//var obj = JSON.parse(data);
				//console.log(obj.count);
				
				//console.log(data.bookList);
				$("#contentTable").children().each(function(){
					$(this).remove();
				});
				
				$.each(data.bookList, function(index, item){
					//console.log(item);
					
					var content = '<tr><td id="test" bgcolor = "#e9e9e9"><b>'+item.name+'('+item.email+')</b>- <font size ="2">'+item.register+'</font>';
					if(item.password != null && item.password != '') {
						content = content +'<a href = "updateForm?id='+item.guestbookId+'">[수정]</a>';
						content = content +'<a href = "deleteForm?id='+item.guestbookId+'">[삭제]</a>';
					}
					content = content +'</td></tr>';
					content = content +'<tr><td>'+item.content+'</td></tr>';
					$("#contentTable").append(content);
				});

				////////////////////////////////////////////////////////////////
				//지워줘야 하는 이유 : 이미 생성된 페이지번호[1][2][3]에 또다시 append 되면 쌓이게되니까 
				$(".pageChange1").eq(1).children().each(function(){
					$(this).remove();
				});
				
				if (data.count >0){
					var pageCount = data.count/<%=ListService.PAGE_SIZE%> +(data.count%<%=ListService.PAGE_SIZE%> == 0 ? 0:1);
					pageCount = parseInt(pageCount);
					var startPage = data.currentPage /<%=ListService.PAGE_SIZE%> +1;
					startPage = parseInt(startPage);
					var endPage = startPage +10;
					
					
					if(endPage > pageCount) endPage = pageCount;
										
					if(startPage > 10) { 

					}
					for (var i= startPage; i<= endPage ; i++){
						//2번째 요소( 0부터 카운트) .html 은 <태그>사이</태그> 사이 내부 값을 바꿔주는 것임.
						//replaceWith 은 전체를 싹다 바꿔줌. replaceWith 쓰면 안되는 이유 : [1][2].. 이런식으로 이어져서 생겨야하기때문.
						var cont = '<a class = "pageChange1" data-pagenum='+i+'>['+i+']</a>'; 
						//$(".pageChange1").eq(1).replaceWith(cont);
						$(".pageChange1").eq(1).append(cont);
				
					}
					if(endPage < pageCount){
					
					}
				}
				
				var valcheck =$(".pageChange1").length;
				console.log(valcheck);
				//alert("size : "+valcheck);
				
				$(".pageChange1 > .pageChange1").on('click', function() {
			  		var val = $(".pageChange1").index(this);
			  		var pageData = $(this).data();
			  		console.log("click 바인딩 - pageData.pagenum " + pageData.pagenum);
			  		//callcnt++;
			  		//console.log("click 바인딩 - callcnt " + callcnt);
			  		load(pageData.pagenum);
				}); 
				
			},
			error : function() {
				alert('ajax error');
			}
		});
	}
  	
  	
  	//세팅된 data 속성 값이 없으므로, 무의미. 
	/*$(".pageChange1").on('click', function() {
  		var val = $(".pageChange1").index(this);
  		var pageData = $(this).data();
  		console.log(pageData.pagenum);
  		load(pageData.pagenum);
	}); 
  	*/
	
	$(document).ready(function(){
		alert('ready');
		console.log("ready호출");
		load(1);
	});
	
</script>

</body>
</html>