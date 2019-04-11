<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
         <c:set var="userType" value="user" />
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
</head>
<body>
<div class="container">
<h1 class="text-center">Hello, ${user.firstName}!</h1>
<p class="text-center">Click <a href="${pageContext.request.contextPath}/logout_controller">here</a> to
		logout</p>
	</div>
</body>
</html>