<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Main</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel='stylesheet' type='text/css' media='screen' href='${pageContext.request.contextPath}/resources/main.css'>
</head>
<body>
    <section class="wrap">
   	    <a href="listSelect">목록 보기</a>
  	    <a href="listSelect_midSort">중간고사 성적순으로 보기</a>
        <a href="listSelect_finSort">기말고사 성적순으로 보기</a>
        <a href="listSelect_avgSort">평균점수 성적순으로 보기</a>
        <a href="insertSelect">데이터 입력</a>
        <a href="createSelect">테이블 생성</a>
    </section>
</body>
</html>