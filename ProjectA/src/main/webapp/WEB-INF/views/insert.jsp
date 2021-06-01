<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>데이터 입력</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel='stylesheet' type='text/css' media='screen' href='${pageContext.request.contextPath}/resources/insert.css'>
</head>
<body>
    <section class="wrap">
        <form action="insert_action">
            <label> 아이디 입력 : 
                <input type="text" name="id" placeholder="아이디" />
            </label>
            <label> 패스워드 입력 : 
                <input type="password" name="pwd" placeholder="패스워드" />
            </label>
            <label> 이름 입력 : 
                <input type="text" name="name" placeholder="이름" />
            </label>
            <label> 생일 입력 : 
                <input type="date" name="birthday" placeholder="생일" />
            </label>
            <label> 주소 입력 : 
                <input type="text" name="address" placeholder="주소" />
            </label>
            <input type="submit" value="입력 완료" />
        </form>
    </section>
</body>
</html>