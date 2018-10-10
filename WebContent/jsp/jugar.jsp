<%@page import="modelo.Intento, java.util.ArrayList, java.time.LocalDateTime, java.time.Duration"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% 	ArrayList<Intento> listaIntentos = (ArrayList<Intento>)session.getAttribute("listaIntentos");
	Boolean visualizo = (Boolean)session.getAttribute("visualizo"); 
	
	
	%>
<!DOCTYPE html>
	<html>
	<head>
		<meta charset="utf-8">
		<title>Jugar Adivinar Número</title>
	</head>
	<body>
	<h4>Adivina un número entre <%=session.getAttribute("intervalo1") %> y <%=session.getAttribute("intervalo2") %></h4>
	<%
		System.out.println("visualizo: " + visualizo);
		if (visualizo) {
			out.print(	"<form action='adivina' method='post'>" + 
						"<input id ='numero' type='number' name='numero' onFocus='document.getElementById(\"numero\").value=\"\"'>" + 
						"<button type='submit'>Aceptar</button>" + 
						"</form>");
		}
	
	%>
	<!-- <form action='adivina' method='post'>
		<input id ='numero' type='number' name='numero' onFocus='document.getElementById("numero").value=""'>
		<button type="submit">Aceptar</button>
	</form> -->
	<table border="1px">
		<tr>
			<th>intento</th>
			<th>tiempo</th>
			<th>numero</th>
			<th>mayor/menor</th>
	

		
		<% 
		
		try {
			for (Intento intento : listaIntentos) {

			int orden = intento.getOrden();
			LocalDateTime fechaHora = intento.getFechaHora();
			int numeroJugado = intento.getNumeroJugado();
			String mensaje = intento.getMensaje();
			out.print(	"<tr>" +
						"<td>" + orden + "</td>" + 
						"<td>" + fechaHora + "</td>" +  
						"<td>" + numeroJugado + "</td>" +  
						"<td>" + mensaje + "</td>" +
						"</tr>");
			}
			
		} catch (IndexOutOfBoundsException e) {
			System.out.print("dentro del catch de jugar.jsp ---- " + e);
		}
		
		
		%>

	</table>
		<% 
		
		try {
			int a = listaIntentos.size()-1;
			String b = listaIntentos.get(a).getMensaje();
			if (b == "Lo has Encontrado") {
				out.print(	"<p>Tiempo total en segundos:" +
							Duration.between(listaIntentos.get(0).getFechaHora(),listaIntentos.get(listaIntentos.size()-1).getFechaHora()).getSeconds() +
							"</p>"
							
						);
				
				out.print (	"<form action='adivina' method='post'>" + 
							"<br><input type='submit' name='reiniciar' value='reiniciar'>" +
							"</form>"
						);			
			}
			
		} catch (IndexOutOfBoundsException e) {
			System.out.print("dentro del catch de jugar.jsp ---- " + e);
		}
		%>
		
		
	</body>
</html>