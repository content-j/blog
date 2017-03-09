<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>삭제페이지</title>
<style type="text/css">
* {
	font-family: gulim;
	font-size: 24px;
}

table {
	border: 0px;
	margin: 15% auto;
	width: 50%;
	height: 50px;
}

td, th {
	color: black;
	border: 0px solid white;
	text-align: center;
}

body {
	background-image: url('dan.jpg');
	background-size: 1950px 1000px;
}

td#t1 {
	font-size: 50px;
}
</style>
<script>
function alist(){
	var url="list.jsp";
	url+= "?col=${param.col}";
	url+= "&word=${param.word}";
	url += "&nowPage=${param.nowPage}";
		location.href = url;
	}
</script>
</head>
<table>
	<tr>
		<td id=t1>
		<c:choose>
		<c:when test="${flag }">삭제 성공</c:when>
		<c:otherwise>삭제 실패</c:otherwise>
		</c:choose>
		
		</td>
	</tr>
	<tr>
		<td><input type="button" value="목록으로" onclick="alist()"></td>
		</body>