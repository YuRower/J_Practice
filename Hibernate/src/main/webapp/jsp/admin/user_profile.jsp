<html>
<head>

<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">

<%@ include file="/WEB-INF/jspf/header.jspf"%>
<c:set var="userType" value="admin" />

<c:set var="action_user" value="${action}" />
<c:if test="${empty action}">
	<c:set var="action_user" value="Add " />
	<c:set var="controller" value="create_controller" />


</c:if>
<c:if test="${not empty action}">
	<c:set var="action_user" value="Edit " />
	<c:set var="controller" value="update_controller" />
		<c:set var="path" value="${pageContext.request.contextPath}" />
	
	<script type="text/javascript">
		window.onload = function() {
			var cantidadCopias = document.getElementById("loginField");
			cantidadCopias.readOnly = true;
		}
	/*	document.getElementById("BtnReset").onclick = function () {
	        location.href = "www.yoursite.com";
	    };*/
	</script>
</c:if>
<body>
	<div class="container">
		<section style="padding-bottom: 50px; padding-top: 50px;">
			<div class="text-right">
				Admin ${user.lastName} (<a
					href="${pageContext.request.contextPath}/controller_logout">Logout</a>)
			</div>
			<div class="row">
				<form id="create_edit"
					action="${pageContext.request.contextPath}/${controller}"
					method="post" onSubmit="return checkPassword(this)">
					<div class="col-md-12">
						<div class="alert alert-info">
							<h2>${action_user}User</h2>
						</div>
						<div class="form-group col-md-8">
							<label>login</label> <input class="form-control" id="loginField"
								class="form-control is-valid" name="login" placeholder="Login"
								required type="text" value="${userProfile.login}"
								pattern="^[a-zA-Z0-9_-]{3,15}$"
								title="Match characters and symbols in the list, a-z,A-Z, 0-9, underscore, hyphen
                           {3,15} and Length at least 3 characters and maximum length of 15 ">
							<label>Password</label> <input class="form-control"
								class="form-control is-valid" name="password" type="password"
								value="${userProfile.password}"
								pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{4,}" required>
							<label>Password Again</label> <input class="form-control"
								class="form-control is-valid" name="confirmed_password"
								type="password" value="${userProfile.password}"
								pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{4,}" required>
							<label>Email</label> <input class="form-control"
								class="form-control is-valid" name="Email" placeholder="Email"
								required type="email" value="${userProfile.email} "> <label>First
								Name</label> <input class="form-control" class="form-control is-valid"
								name="FirstName" placeholder="First Name" required type="text"
								value="${userProfile.firstName}"
								pattern="^[a-zA-Z0-9_-]{3,15}$"
								title="Match characters and symbols in the list, a-z,A-Z, 0-9, underscore, hyphen
                           {3,15} and Length at least 3 characters and maximum length of 15 ">
							<label>Last Name</label> <input class="form-control"
								class="form-control is-valid" name="LastName"
								placeholder="Last Name" required type="text"
								value="${userProfile.lastName}" pattern="^[a-zA-Z0-9_-]{3,15}$"
								title="Match characters and symbols in the list, a-z,A-Z, 0-9, underscore, hyphen
                           {3,15} and Length at least 3 characters and maximum length of 15 ">
							<label>Birthday</label> <input class="form-control"
								name="Birthday" placeholder="Birthday" required type="date"
								value="${userProfile.birthday}"> <br> <label>Role</label>
							<select required name="Role">
							    <option disabled selected value> -- select an option -- </option>
								<option value="1">User</option>
								<option value="2">Admin</option>
							</select>
						</div>
					</div>
					<div class="form-group col-md-8">
						<button type="submit" class="btn btn-primary">Ok</button>
						<button type="reset"  class="btn btn-secondary">
						Cancel</button>
						<pre>Password format:<br />4 characters minimum.<br />At least one lowercase letter, one uppercase and one number required.<br />Only latin letters allowed.
						</pre>
					</div>
				</form>

			</div>
		</section>
	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>
</html>

