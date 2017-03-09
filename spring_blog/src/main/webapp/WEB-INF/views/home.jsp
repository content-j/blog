<%@page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
	<c:when test="${empty sessionScope.id}">
		<!-- empty는 비어있으면 true -->
		<c:set var="str">Spring MVC Blog 페이지 입니다</c:set>
	</c:when>
	<c:otherwise>
		<c:set var="str">안녕하세요 ${sessionScope.id }님</c:set>
	</c:otherwise>
</c:choose>

<c:set var="title">나의 여행 블로그Spring MVC</c:set>
<c:if test="${not empty sessionScope.id&&sessionScope.grade=='A' }">
	<c:set var="title">관리자 페이지</c:set>
</c:if>

<html>

<head>
<style type="text/css">
input {
	width: 100px;
	height: 100px;
	font-size: 25;
	text-align: center;
	background-color: window;
}
</style>
<title>Home</title>
<link href="${pageContext.request.contextPath }/css/style.css"
	rel="stylesheet">
</head>
<body>
	<div class="title">${title }</div>

<h2>ㅎㅇ</h2>


	<div class="content">
		<h1>${str}</h1>
		<img src="images/비.png" width="50%"><br> <br>
		<c:choose>
			<c:when test="${empty sessionScope.id }">
				<input type="button" value="로그인"
					onclick="location.href = '${pageContext.request.contextPath}/member/login'">
			</c:when>
			<c:otherwise>
				<input type="button" value="로그아웃"
					onclick="location.href = '${pageContext.request.contextPath}/member/logout'">
			</c:otherwise>
		</c:choose>




		<P>The time on the server is ${serverTime}.</P>

		<input type="button" onclick="location.href='./bbs/list'" value="bbs">
		<c:choose>
			<c:when test="${grade=='A' }">
				<input type="button" onclick="location.href='./admin/list'"
					value="member">
			</c:when>
			<c:otherwise>
				<input type="button" onclick="location.href='./member/read'"
					value="myinfo">
			</c:otherwise>

		</c:choose>
		<input type="button" onclick="location.href='./memo/list'"
			value="memo"> <input type="button"
			onclick="location.href='./team/list'" value="team"> <input
			type="button" onclick="location.href='./address/list'"
			value="address"> <input type="button"
			onclick="location.href='./image/list'" value="image">
	</div>



</body>
</html>
