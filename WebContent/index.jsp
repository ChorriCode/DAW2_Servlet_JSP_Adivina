<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Adivina Número</title>
	</head>
	<h1>Adivina Número</h1>
	<body>
<%-- 	<% response.sendRedirect("adivina"); %> --%>
	<h2>Introduce intervalo para adivinar</h2>
	<p>Sólo números enteros positivos</p>
		<form action="adivina" method="post">
			Intervalo 1: <input type="number" name="intervalo1" value="1">
			Intervalo 2: <input type="number" name="intervalo2" value="100">
      		<input type="submit" name="valida" value="valida">
		</form>
		<% Object respuesta = session.getAttribute("error") == null ? "" : session.getAttribute("error"); %>
	<p id="error"><%=respuesta %></p>
	</body>
</html>