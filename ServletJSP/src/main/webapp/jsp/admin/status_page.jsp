<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<!DOCTYPE html>
<html>
<head>
<title>Successfully</title>
</head>
<body>
	<table id="main container">
		<tr>
			<td class="content center"><c:out
					value="${action_command} was successfully " /></td>
		</tr>
	</table>
	<a href="${pageContext.request.contextPath}/UpdateUserListServlet">Back
	</a>
</body>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</html>