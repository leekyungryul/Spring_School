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
            <label> 이름 입력 : 
                <input type="text" name="student_name" placeholder="이름" />
            </label>
            <label> 중간고사 점수 입력 : 
                <input type="number" name="middle_score" placeholder="점수" />
            </label>
            <label> 기말고사 점수 입력 : 
                <input type="number" name="final_score" placeholder="점수" />
            </label>
            <input type="submit" value="입력 완료" />
        </form>
    </section>
</body>
</html>