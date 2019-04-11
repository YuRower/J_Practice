<%@ page isErrorPage="true"%>
<%@ page import="java.io.PrintWriter"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<script src='<c:url value="/js/main.js"/>'></script>

<body>
	<table id="main-container">
		<tr>
			<td class="content center">
				<h2 class="error">
					<button onclick="goBack()">Go Back</button>
				</h2> <c:set var="code"
					value="${requestScope['javax.servlet.error.status_code']}" /> <c:set
					var="message"
					value="${requestScope['javax.servlet.error.message']}" /> <c:set
					var="exception"
					value="${requestScope['javax.servlet.error.exception']}" /> <c:if
					test="${not empty code}">
					<h3>Error code: ${code}</h3>
				</c:if> <c:if test="${not empty message}">
					<h3>${message}</h3>
				</c:if> <c:if test="${not empty exception}">
					<% exception.printStackTrace(new PrintWriter(out)); %>
				</c:if> <c:if test="${not empty requestScope.errorMessage}">
					<h3>${requestScope.errorMessage}</h3>
				</c:if>
			</td>
		</tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>
</body>
</html>