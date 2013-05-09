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
		<h1>Appointments</h1>
		<div class="widget">
			<div class="row">
				Group: <select name="group" data-required="true">
							<option value="1">Gruppe 1</option>
					   </select>
			</div>
			<div class="row">
				Lecture <select name="lecture" data-required="true">
							<option value="1">Datenbanken</option>
						</select>
			</div>
			<div class="row">
				Category: <select name="name" data-required="true">
						  		<option>default</option>
						  		<option>exam</option>
						  </select>
			</div>
			<div class="row">
				Date: <input name="date" type="text" data-required="true">
			</div>
			<div class="row">
				Time: <input name="time" type="text" data-required="true">
			</div>
			<div class="row">
				Duration: <input name="duration" type="number" data-required="true">
			</div>
			<div class="row">
				Room: <select name="room" data-required="true">
							<option>C-239</option>
							<option>A-374</option>
							<option>A-474</option>
					  </select>
			</div>
			<div id="error"></div>
			<input type="submit" class="button positive" value="Submit">
			<input type="button" class="button negative" value="Cancel">
		</div>
	</form>
</body>