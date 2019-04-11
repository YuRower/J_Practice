<html>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
</head>
<body>
<div class="container">
		<section style="padding-bottom: 50px; padding-top: 50px;">

  <form class="form-horizontal" action="login_controller" method = "post">
    <div class="form-group">
      <label class="control-label col-sm-2" >Login:</label>
      <div class="col-sm-8">
        <input type="text" class="form-control" id="login" placeholder="Enter login" name="login">
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="pwd">Password:</label>
      <div class="col-sm-8">          
        <input type="password" class="form-control" id="pwd" placeholder="Enter password" name="pwd">
      </div>
    </div>
    <div class="form-group">        
      <div class="col-sm-offset-9 col-sm-10">
        <button type="submit" class="btn btn-default">Sig in</button>
      </div>
    </div>
    
  </form>
  </section>
</div>
</body>
</html>
