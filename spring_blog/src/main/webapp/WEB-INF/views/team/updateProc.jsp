<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<script type="text/javascript">
function tlist() {
	var url = "list";
	url +="?col=${param.col}";
	url +="&word=${param.nowPage}";
	url +="&nowPage=${param.word}";
	
	

		location.href = url;
	}
</script>
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
	<c:when test="${flag }">팀정보를 수정했습니다.</c:when>
	<c:otherwise>수정에 실패했습니다</c:otherwise>
	</c:choose>
		
		<br> <input type="button" value="목록" onclick="tlist()">
	</div>
</body>
</html>