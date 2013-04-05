<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
	<title>CoolRapla</title>
	<meta charset="utf-8"/>
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css"/>
</head>
<body>
	<h1>Heutige Vorlesungen</h1>
	<div class="line">
		<div class="widget">
			<h2>Aktuelle Vorlesung</h2>
			<div class="course">Datenbanken</div>
			<div class="docent">Schoknecht, Andreas</div>
			<div class="room">A474 Hörsaal Wirtschaft WI</div>
			<div class="meter" style="width: 98%;">
				<div class="progress" style="width: 50%;"></div>
				<div class="value">noch 1:15</div>
			</div>
		</div>
		<div class="widget">
			<h2>Pause</h2>
			<div class="course">30min</div>
			<br>
			<div class="room">A474 Hörsaal Wirtschaft WI</div>
			<div class="meter" style="width: 98%;">
				<div class="progress" style="width: 100%;"></div>
				<div class="value">Beginn um 11:30</div>
			</div>
		</div>
		<div class="widget">
			<h2>Nächste Vorlesung</h2>
			<div class="course">Projektmanagement</div>
			<div class="docent">Klink, Stefan</div>
			<div class="room">A474 Hörsaal Wirtschaft WI</div>
			<div class="meter" style="width: 98%;">
				<div class="progress" style="width: 100%;"></div>
				<div class="value">Beginn um 12:00</div>
			</div>
		</div>
	</div>
	<h1>Anstehende Klausuren</h1>
	<div class="line">
		<div class="widget">
			<h2>Nächste Klausur</h2>
			<div class="course">Datenbanken</div>
			<div class="docent">Schoknecht, Andreas</div>
			<div class="room">A474 Hörsaal Wirtschaft WI</div>
			<div class="meter" style="width: 98%;">
				<div class="progress progress-red" style="width: 25%;"></div>
				<div class="value">noch 4 Tage</div>
			</div>
		</div>
		<div class="widget">
			<h2>Folgende Klausur</h2>
			<div class="course">Projektmanagement</div>
			<div class="docent">Klink, Stefan</div>
			<div class="room">A474 Hörsaal Wirtschaft WI</div>
			<div class="meter" style="width: 98%;">
				<div class="progress progress-orange" style="width: 60%;"></div>
				<div class="value">noch 12 Tage</div>
			</div>
		</div>
		<div class="widget">
			<h2>Folgende Klausur</h2>
			<div class="course">Operations Research</div>
			<div class="docent">Hugelmann, Peter</div>
			<div class="room">A474 Hörsaal Wirtschaft WI</div>
			<div class="meter" style="width: 98%;">
				<div class="progress" style="width: 90%;"></div>
				<div class="value">noch 20 Tage</div>
			</div>
		</div>
	</div>
	<div id="branding">Powered by CoolRapla, &copy;2012 CoolPeople with CoolSkillz</div>
</body>