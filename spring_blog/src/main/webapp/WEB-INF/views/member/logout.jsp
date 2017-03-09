<%@ page contentType="text/html; charset=UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	session.invalidate();//모든세션제거(id,grade)
// 	response.sendRedirect("../index.jsp");
%>
<script>var url="${pageContext.request.contextPath }/index.jsp";
	location.href = url;
</script>