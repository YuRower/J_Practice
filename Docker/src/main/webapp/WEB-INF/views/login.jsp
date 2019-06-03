
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
      <title>Login page</title>
      <%@ include file="/WEB-INF/jspf/header.jspf"%>
      
   </head>
   <body>
      <div class="container">
         <section style="padding-bottom: 50px; padding-top: 50px;">
            <div class="login-card">
               <div class="form-group">
                  <c:url var="loginUrl" value="/login" />
                  <form action="${loginUrl}" method="post" class="form-horizontal">
                     <c:if test="${param.error != null}">
                        <div class="alert alert-danger">
                           <p>Invalid username and password.</p>
                        </div>
                     </c:if>
                     <c:if test="${param.logout != null}">
                        <div class="alert alert-success">
                           <p>You have been logged out successfully.</p>
                        </div>
                     </c:if>
                     <div class="form-group">
                        <label class="control-label col-sm-2">Login:</label>
                        <div class="col-sm-8">
                           <input type="text" class="form-control" id="username" name="login" placeholder="Enter Username" required>
                        </div>
                     </div>
                     <div class="form-group">
                        <label class="control-label col-sm-2" for="pwd">Password:</label>
                        <div class="col-sm-8">
                           <input type="password" class="form-control" id="password" name="password" placeholder="Enter Password" required>
                        </div>
                     </div>
                     <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                     <div class="form-group">
                        <div class="col-sm-offset-9 col-sm-10">
                           <button type="submit" class="btn btn-default">Sig in</button>
                        </div>
                     </div>
                  </form>
               <div class="form-group">
                        <div class="col-sm-offset-9 col-sm-10">
				<a href="${pageContext.request.contextPath}/registr" class="btn btn-default">Sig up</a>
					  </div>
                     </div>
               </div>
            </div>
         </section>
      </div>
   </body>
</html>

