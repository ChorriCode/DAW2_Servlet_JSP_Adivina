<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Adivina Número</title>
		<link rel="stylesheet" type="text/css" href="css/styles.css">
	</head>
	<h1>Adivina Número</h1>
	<body>
<%-- 	<% response.sendRedirect("adivina"); %> --%>
	<h2>Introduce intervalo para adivinar</h2>
	<p>Sólo números enteros positivos</p>
	<div class="caja">
		<form action="adivina" method="post">
			Intervalo 1: <input type="number" name="intervalo1" value="1"><br>
			Intervalo 2: <input type="number" name="intervalo2" value="100"><br>
      		<input type="submit" name="dataInput" value="valida">
		</form>
	</div>

		<% Object respuesta = session.getAttribute("error") == null ? "" : session.getAttribute("error"); %>
	<p id="error"><%=respuesta %></p>
	</body>
</html>