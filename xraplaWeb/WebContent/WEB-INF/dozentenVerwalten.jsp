<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
	<title>CoolRapla</title>
	<meta charset="utf-8"/>
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
	
	<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script src="${pageContext.request.contextPath}/js/form.js"></script>
</head>
<body>
	<form id="form">
		<h1>Sample form</h1>
		<div class="widget">
			<div class="row">
				Student-Nr: <input name="nr" type="number" value="0000">
			</div>
			<div class="row">
				Name: <input name="name" type="text">
			</div>
			<div class="row">
				Required text: <input name="name" type="text" data-required="true">
			</div>
			<div class="row">
				Course: <select name="course">
							<option>WWI10B1</option>
							<option>WWI10B2</option>
							<option>WWI11B1</option>
							<option>WWI11B2</option>
							<option>WWI12B1</option>
							<option>WWI10B2</option>
						</select>
			</div>
			<div id="error"></div>
			<input type="submit" class="button positive" value="Submit">
			<input type="button" class="button negative" value="Cancel">
		</div>
	</form>
</body>