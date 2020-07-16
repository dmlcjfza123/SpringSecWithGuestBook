<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Hello</title>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
</head>

<body>
	인사말: ${greeting}

	<a class = "page">1</a>
	<a class = "page">
		<a class = "page">333</a>
	</a>
	<a class = "page">3</a>
	<button id="btn">button</button>
	<form id="frm">
		<input type='hidden' name="arg" value="1111">
	</form>
	<script>
		$("#btn").click(function() {
			$.ajax({
				url:"/test2",
				type:"get",
				data:$("#frm").serialize(),
				success:function(data){
					alert(data);
					//$(".page").html("<a>asd</a>")
					//$(".page").replaceWith("<a>asd</a>")
					//$(".page").eq(1).replaceWith("<a>asd</a>")
					//$(".page").eq(1).append("<a>asd</a>")
					$(".page").eq(1).append("<a>asd</a>")
				},
				error:function(){
					alert('ajax error');
				}
			})
		});
	</script>
</body>
</html>
