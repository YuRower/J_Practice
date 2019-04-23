<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>

 
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>User Registration Form</title>
      <%@ include file="/WEB-INF/jspf/header.jspf"%>
		<script src='https://www.google.com/recaptcha/api.js'></script>
</head>
 
<body>
    <div class="container">
             <section style="padding-bottom: 50px; padding-top: 50px;">
	<sec:authorize access="hasRole('ADMIN')">
		 	<div class="text-right">
		 
				Admin ${personLogin}(<a
					href="${pageContext.request.contextPath}/logout">Logout</a>)
			</div> 
				 	</sec:authorize>
			
        <div class="well lead">User ${action_user} Form</div>
        
        <form:form id = "MyForm" method="POST" modelAttribute="user" class="form-horizontal" onSubmit="return checkPassword(this)">
            <form:input type="hidden" path="id" id="id"/>
            
             					<div class="col-md-12">
             						<div class="form-group col-md-8">
             
            <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-3 control-lable" for="firstName">First Name</label>
                    <div class="col-md-7">
                        <form:input type="text" path="firstName" id="firstName" class="form-control input-sm" 
                         pattern="^[a-zA-Z0-9_-]{3,15}$"
								title="Match characters and symbols in the list, a-z,A-Z, 0-9, underscore, hyphen
                           {3,15} and Length at least 3 characters and maximum length of 15 "/>
                        <div class="has-error">
                            <form:errors path="firstName" class="help-inline"/>
                        </div>
                    </div>
                </div>
            </div>
     
            <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-3 control-lable" for="lastName">Last Name</label>
                    <div class="col-md-7">
                        <form:input type="text" path="lastName" id="lastName" class="form-control input-sm" pattern="^[a-zA-Z0-9_-]{3,15}$"
								title="Match characters and symbols in the list, a-z,A-Z, 0-9, underscore, hyphen
                           {3,15} and Length at least 3 characters and maximum length of 15 "
                           />
                        <div class="has-error">
                            <form:errors path="lastName" class="help-inline"/>
                        </div>
                    </div>
                </div>
            </div>
      <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-3 control-lable" for="login">Login</label>
                    <div class="col-md-7">
                        <c:choose>
                            <c:when test="${edit}">
                                <form:input type="text" path="login" id="login" class="form-control input-sm" disabled="true"/>
                            </c:when>
                            <c:otherwise>
                                <form:input type="text" path="login" id="login" class="form-control input-sm" />
                                <div class="has-error">
                                    <form:errors path="login" class="help-inline"/>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
     
            <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-3 control-lable" for="password">Password</label>
                    <div class="col-md-7">
                        <form:input type="password" path="password" id="password" name="password" class="form-control input-sm" 
                        pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{4,}"
                        />
                        <div class="has-error">
                            <form:errors path="password" class="help-inline"/>
                        </div>
                    </div>
                </div>
            </div>
             <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-3 control-lable" for="password">Password Again</label>
                    <div class="col-md-7">
                        <input type="password"  name="confirmed_password" class="form-control input-sm" 
                        pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{4,}"
                        />
                       
                    </div>
                </div>
            </div>
            
              
       
            <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-3 control-lable" for="email">Email</label>
                    <div class="col-md-7">
                        <form:input type="email" path="email" id="email" class="form-control input-sm" />
                        <div class="has-error">
                            <form:errors path="email" class="help-inline"/>
                        </div>
                    </div>
                </div>
            </div>
            
             <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-3 control-lable" for="birthday">birthday</label>
                    <div class="col-md-7">
                        <form:input type="date" path="birthday" id="birthday" />
        <div class="has-error">
                            <form:errors path="birthday" class="help-inline"/>
                        </div>
                    </div>
                </div>
            </div>
            
            
         
     
            <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-3 control-lable" for="role">Roles</label>
                    
                    <div class="col-md-7">
                    
                      
                       <form:select path="roleId">
                <form:options items="${roles}" />
				       </form:select>

                        <div class="has-error">                    
                            <form:errors path="roleId" class="help-inline"/>
                        </div>
                    </div>
                </div>
            </div>
            
             <div class="g-recaptcha col-md-8"
          data-sitekey="6Ldq754UAAAAAAvR_UeScGk9qrDawlHjdb9g0PFV"></div>
       
            					<div class="form-group col-md-8">
                     <pre>Password format:<br />4 characters minimum.<br />At least one lowercase letter, one uppercase and one number required.<br />Only latin letters allowed.
						</pre>
						  <c:choose>
                        <c:when test="${edit}">
                            <input type="submit" value="Ok" class="btn btn-primary btn-sm"/> or <a href="<c:url value='/list' />">Cancel</a>
                        </c:when>
                        <c:otherwise>
                            <input type="submit" value="Register" class="btn btn-primary btn-sm"/> or <a href="<c:url value='/list' />">Cancel</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
             
					</div>
					
					 
        </form:form>
          </section>
    </div>
</body>
</html>