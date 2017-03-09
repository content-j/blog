<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<script type="text/javascript">
	function blist() {
		var url = "list.do";
		url += "?col=${col}" ;
		url += "&word=${word}";
		url += "&nowPage=${nowPage}";
		location.href = url;
	}
	function read(bbsno) {
		var url = "read.do"
		url += "?bbsno=" + bbsno;
		location.href = url;
	}
</script>
<style type="text/css">
* {
	font-family: gulim;
	font-size: 20px;
}
</style>
<link href="${pageContext.request.contextPath }/css/style.css" rel="Stylesheet" type="text/css">
</head>
<!-- *********************************************** -->
<body>
	<!-- *********************************************** -->

	<DIV class="title">처리결과</DIV>

	<div class="content">
	<c:choose>
	<c:when test="${not pflag }">패스워드가 일치하지 않습니다.</c:when>
	<c:when test="${flag }">게시판의 글을 수정하였습니다.</c:when>
	<c:otherwise>글변경을 실패하였습니다.</c:otherwise>
	
	
	</c:choose>
	
	
	
	</div>
	<c:choose>
	<c:when test="${not pflag }">
	DIV class='bottom'>
		<input type="button" value='다시 시도' onclick="history.back()"> <input
			type='button' value='목록' onclick="blist()">
	</DIV>
	
	</c:when>
	<c:otherwise>
	<div class='bottom'>
		<input type="button" value='계속 등록' onclick="location.href='create.do'">
		<input type="button" value='읽기' onclick="read('${param.bbsno}')">
		<input type='button' value='목록' onclick="blist()">
	</div>
	</c:otherwise>
	</c:choose>
	


	<!-- *********************************************** -->
</body>
<!-- *********************************************** -->
</html>
