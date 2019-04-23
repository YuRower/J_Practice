<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<div class="authbar">
	<c:if test="${empty registr}">
		<span class="floatRight"><a href="<c:url value="/logout" />">Logout</a></span>
		</c:if>
	</div>
