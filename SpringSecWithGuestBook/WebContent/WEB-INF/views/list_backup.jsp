<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import = "java.text.SimpleDateFormat" %>
<%@ page import = "com.dto.GuestbookPageDto" %>
<%@ page import = "com.dto.GuestBookDto" %>
<%@ page import = "com.service.ListService" %>
<%@ page errorPage = "/error.jsp" %>
<%
SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

//GuestbookPageDto data = (GuestbookPageDto)request.getAttribute("data");

//GuestbookPageDto tempData= null;

//의미 없음. 어차피 아래에서 값 다시 담아줌. 그래서 9244 로 테스트값 그냥 넣어본것.
//int rsltNum=9244;

//boolean ajaxAccess = false;

%>

<%
/*
if(data.getBookList().size() < 0){
	System.out.println("in list.jsp - data 못받음");
	System.out.println("in list.jsp - count 개수 : " + data.getCount());
}
else{
	System.out.println("in list.jsp - data 잘받음");
	System.out.println("in list.jsp - count 개수 : " + data.getCount());
}
*/
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

<%--
	//전체글의 개수가 0보다큰 경우, 쉽게 이동할 수 있도록 링크를 출력해준다.
	if (data.getCount() >0){
		int pageCount = data.getCount()/ListService.PAGE_SIZE +(data.getCount()%ListService.PAGE_SIZE == 0 ? 0:1);
		int startPage = data.getCurrentPage() / ListService.PAGE_SIZE +1;
		int endPage = startPage +10;
		if(endPage > pageCount) endPage = pageCount;
		
		//System.out.println("in list.jsp startPage : " + startPage);
		//System.out.println("in list.jsp endPage : " + endPage);
		//System.out.println("in list.jsp pageCount : " + pageCount);
		
		
		
		if(startPage > 10) { --%>
		<!--  <a href = "list?num=<%--= startPage -10 %>" class = "pageChange1">[이전]</a> -->
		
	<%--}
		for (int i= startPage; i<= endPage ; i++){ rsltNum = i;--%>
		<!-- <a class = "pageChange1" data-pagenum="<%//=i %>" >[<%//=i %>]</a>-->
	<%--
		}
		if(endPage < pageCount){ --%>
		 <!--  <a href = "list?num=<%=// startPage +10 %>" class = "pageChange1"> [다음]</a>-->
<%--
		}
	}
--%>
</center>

<script>
//<input type="hidden" id = "rsltId" name="rslt" value = <%=// rsltNum %>>
//참고: https://milkye.tistory.com/277
//version1. 자바 코드 그대로 변수값 가져와서 jqeury로 사용하기위한 변수로 정의후 가져오기.
//var rsltnum = <%//= rsltNum%>;
//alert("rsltnum : " + rsltnum);
//console.log('list - rsltnum',rsltnum);

//version2. input 태그안에 value로 자바코드 변수값 넣어놓고 input 태그 id로 접근해서 value 접근해서 값 가져오기.
//var rsltnum2 = $("#rsltId").val();
//alert("rsltnum2 : " + rsltnum2);
//console.log('list - rsltnum2',rsltnum2);

//test 용
/*
$.ajax({
	url:"/list1",
	type:"get",
	
	//안되네?
	//data:("#rsltId").serialize(),
	data:{'rslt' :rsltnum },
	
	success:function(data){
		alert(data);
	},
	error:function(){
		alert('ajax error');
	}	
});
*/

//a 태그 class 클릭 test
/* $(".pageChange1").click(function() {
	
	alert('tempData전 테스트');
}); */
// [pre] [1] [2] [3] [4] [5] [6] [7] [8] [9] [10] [next]
// [11] [12] [13]
//실제처리 //참고 https://gangzzang.tistory.com/entry/%EC%A0%9C%EC%9D%B4%EC%BF%BC%EB%A6%ACjQuery-%EC%84%A0%ED%83%9D%EC%9E%90
  	function load(pageNum){
  		$.ajax({
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
				//한번 DOM이 구성된이후에 ajax 처리가 되고나서 다시 바인딩 해주어야 클릭 기능을 사용할 수 있음.
				/*
				$("#test").click(function(){
					alert('click!!!');
				});
				*/
				////////////////////////////////////////////////////////////////
				//지워줘야 하는 이유 : 이미 생성된 페이지번호[1][2][3]에 또다시 append 되면 쌓이게되니까 
				$(".pageChange1").eq(1).children().each(function(){
					$(this).remove();
				});
				
				if (data.count() >0){
					var pageCount = data.count()/ListService.PAGE_SIZE +(data.count()%ListService.PAGE_SIZE == 0 ? 0:1);
					var startPage = data.currentPage() / ListService.PAGE_SIZE +1;
					var endPage = startPage +10;
					if(endPage > pageCount) endPage = pageCount;
										
					if(startPage > 10) { 
						$(".pageChange1").html("태그되냐");
					
					
					}
					for (int i= startPage; i<= endPage ; i++){
						//2번째 요소( 0부터 카운트) .html 은 <태그>사이</태그> 사이 내부 값을 바꿔주는 것임.
						//replaceWith 은 전체를 싹다 바꿔줌. replaceWith 쓰면 안되는 이유 : [1][2].. 이런식으로 이어져서 생겨야하기때문.
						var cont = '<a class = "pageChange1" data-pagenum='+i+'>['+i+']</a>'; 
						$(".pageChange1").eq(1).replaceWith(cont);
					
				
					}
					if(endPage < pageCount){
					
					}
				}
				
				
				$(".pageChange1").on('click', function() {
			  		var val = $(".pageChange1").index(this);
			  		var pageData = $(this).data();
			  		load(pageData.pagenum);
				});
				
			},
			error : function() {
				alert('ajax error');
			}
		});
	}
  	
  	/*
	$(".pageChange1").on('click', function() {
  		var val = $(".pageChange1").index(this);
  		var pageData = $(this).data();
  		load(pageData.pagenum);
	});
  	*/
	
	$(document).ready(function(){
		alert('ready');
		load(1);
	});
	
</script>
<%--
	if (ajaxAccess) {
		System.out.println("ajaxAccess 진입.");
		if (tempData == null)
			System.out.println("tempData가 null 임.");

		if (tempData.getBookList().size() < 0) {
			System.out.println("in list.jsp - tempData 못받음");
			System.out.println("in list.jsp - tempData - count 개수 : " + tempData.getCount());
		} else {
			System.out.println("in list.jsp - tempData 잘받음");
			System.out.println("in list.jsp - tempData - count 개수 : " + tempData.getCount());

			data = tempData;

			ajaxAccess = false;
		}
	}
--%>

</body>
</html>