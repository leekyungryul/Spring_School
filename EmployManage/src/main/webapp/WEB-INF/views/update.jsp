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
        <form action="update_action">
            <input type="hidden" name="idx" value="${idx }" />
            <label> 이름 입력 : 
                <input type="text" name="name" placeholder="이름" value="${name }" />
            </label>
            <label> 성별 입력 : 
                <input type="text" name="sex" placeholder="성별" value="${sex }" />
            </label>
            <label> 주소 입력 : 
                <input type="text" name="adress" placeholder="주소" value="${adress }" />
            </label>
            <label> 부서 입력 : 
                <input type="text" name="part" placeholder="소속부서" value="${part }" />
            </label>
            <input type="submit" value="입력 완료" />
        </form>
    </section>
</body>
</html>