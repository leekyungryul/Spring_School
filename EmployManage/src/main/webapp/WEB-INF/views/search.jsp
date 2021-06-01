<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='utf-8'>
<meta http-equiv='X-UA-Compatible' content='IE=edge'>
<title>Main</title>
<meta name='viewport' content='width=device-width, initial-scale=1'>
<link rel='stylesheet' type='text/css' media='screen'
	href='${pageContext.request.contextPath}/resources/main.css'>
</head>
<body>
	<section class="wrap">
		<form action="search">
			<label> 이름 : <input type="text" name="name" placeholder="이름"
				value="${name }" />
			</label> <input type="submit" value="입력 완료" />

		</form>
	</section>
	<section class="wrap2">
		<a href="/lkr" style="margin-top: 50px;">홈으로</a>
	</section>

</body>
</html>