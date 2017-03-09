<%@ page contentType="text/html; charset=UTF-8"%>
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
	margin-top: 20px;
	margin-bottom: 20px;
}

table {
	width: 60%;
	margin: auto;
}

table, th, td {
	border: 1px solid black;
	border-collapse: collapse;
}

img {
	width: 500px;
	height: 500px;
}

#ftd {
	text-align: center;
}
</style>
<script type="text/javascript">
	function updat(num) {
		var url = "update";
		url += "?num=" + num;
		url += "&col=${col}";
		url += "&word=${word}";
		url += "&nowPage=${nowPage}";
		location.href = url;
	}
	function alist() {
		var url = "list";
		url += "?col=${col}";
		url += "&word=${word}";
		url += "&nowPage=${nowPage}";
		location.href=url;
	}
</script>
</head>
<body>


	<div>조회</div>
	<table>
		<tr>
			<td>이름</td>
			<td>${dto.name }</td>
		</tr>
		<tr>
			<td>전화번호</td>
			<td>${dto.phone }</td>
		</tr>
		<tr>
			<td>우편번호</td>
			<td>${dto.zipcode }</td>
		</tr>
		<tr>
			<td>주소</td>
			<td>${dto.address1 }</td>
		</tr>
		<tr>
			<td>상세주소</td>
			<td>${dto.address2 }</td>
		</tr>
		<tr>
			<td>입력날짜</td>
			<td>${dto.wdate}</td>
		</tr>


	</table>
	<div>
		<input type="button" value="수정" onclick="updat('${dto.num}')"> 
		<input type="button" value="목록" onclick="alist()">
	</div>

</body>
</html>