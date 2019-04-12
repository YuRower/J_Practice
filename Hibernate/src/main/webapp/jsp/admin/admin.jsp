
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="t" uri="/WEB-INF/tld/user_table.tld"%>
<c:set var="userType" value="admin" />

<head>
<meta charset="UTF-8">
<title>Admin Page</title>

</head>
<body>
	<div class="container">

		<p class="text-right">
			Admin, ${user.lastName} <a
				href="${pageContext.request.contextPath}/logout_controller">(Logout)</a>
		</p>
		
		<p> 
			<a href="${pageContext.request.contextPath}/redirect_controller?page=new_user">Add new user</a>
		</p>
		<div class="center">
			<t:userListTable userList="${userList}" tableStyle="thead-dark" />
		</div>

	</div>
</body>
</html>