<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
			Admin, ${personLogin}<a
				href="${pageContext.request.contextPath}/logout">(Logout)</a>
		</p>
		<sec:authorize access="hasRole('ADMIN')">
		 	<p class="h4">
		 		<a href="${pageContext.request.contextPath}/newuser">Add New User</a>
		 	</p>
	 	</sec:authorize>
	<div class="generic-container">
		<div class="center">
			<t:userListTable userList="${userList}" tableStyle="thead-dark" />
		</div>
	</div>

	</div>
</body>
</html>