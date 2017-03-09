<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<style type="text/css">
* {
	font-family: gulim;
	font-size: 20px;
}
</style>
<script type="text/javascript">
function blist() {
	var url = "list.do"
	url += "?col=${param.col}";
	url += "&word=${param.word}";
	url += "&nowPage=${param.nowPage}";
		location.href = url;
	}
</script>
<link href="${pageContext.request.contextPath }/css/style.css" rel="Stylesheet" type="text/css">
</head>
<!-- *********************************************** -->
<body>
	<!-- *********************************************** -->

	<div class="title">등록</div>
	<DIV class="content">
		<c:choose>
		<c:when test="${flag }">답변이 등록되었습니다</c:when>
		<c:otherwise>답변 등록에 실패했습니다</c:otherwise>
		</c:choose>
		
		
		
	</DIV>
	<div class="bottom">
		<input type="button" value="계속등록" onclick="location.href='create.do'">
		<input type="button" value="목록" onclick="blist()">

	</div>



	<!-- *********************************************** -->
</body>
<!-- *********************************************** -->
</html>
