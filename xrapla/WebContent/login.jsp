<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
	<title>XRapla - Welcome.</title>
	<meta charset="utf-8"/>
	
	<link rel="shortcut icon" href="favicon.ico" type="image/x-icon"/>
	<link rel="stylesheet" href="css/style.css"/>
	<link rel="stylesheet" href="css/login.css"/>
	
	<script src="js/jquery.js"></script>
	<script src="js/jquery.spinner.js"></script>
	<script src="js/login.js"></script>
</head>
<body>
	<script>
		document.body.className += "js_enabled";
	</script>
	<div id="js_warning">
		You need to enable JavaScript to properly use XRapla!
	</div>
	<div id="login_container">
		<h1>Welcome.</h1>
		<form id="login" class="widget">
			<div class="row">
				Username: <input id="username" type="text">
			</div>
			<div class="row">
				Password: <input id="password" type="password">
			</div>
			<div id="error"></div>
			<input id="submit" type="submit" class="button" value="Login">
		</form>
	</div>
</body>