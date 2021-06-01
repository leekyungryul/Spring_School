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
    	<form action="list">
    		<label> 1반 : 
    			<input type="submit" name="class" value="class1" /><br><br>
   			</label>
   			<label> 2반 : 
    			<input type="submit" name="class" value="class2" /><br><br>
   			</label>
            
            <!-- <input type="submit" value="입력 완료" /> -->
        </form>
    </section>
    
    
</body>
</html>