<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<style type="text/css">
* {
	font-family: gulim;
	font-size: 24px;
}

div {
	text-align: center;
	margin-top: 100px;
}
</style>
</head>
<body>
	<div>
		<c:choose>
			<c:when test="${flag }">팀정보를 등록했습니다</c:when>
			<c:otherwise>정보 등록을 실패했습니다</c:otherwise>
		</c:choose>

		<br> <input type="button" value="계속 등록"
			onclick="location.href='./create.do'"> <input type="button"
			value="목록" onclick="location.href='./list.do'">
	</div>
</body>
</html>




