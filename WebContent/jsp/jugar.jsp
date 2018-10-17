<%@page import="modelo.Intento, java.util.ArrayList, java.time.LocalDateTime, java.time.Duration"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% 	ArrayList<Intento> listaIntentos = (ArrayList<Intento>)session.getAttribute("listaIntentos");
	Boolean visualizo = (Boolean)session.getAttribute("visualizo"); 
	
	
	%>
<!DOCTYPE html>
	<html>
	<head>
		<meta charset="utf-8">
		<title>Jugar Adivinar Número</title>
		<link rel="stylesheet" type="text/css" href="css/styles.css">
	</head>
	<script>
	var inicio=0;
	var timeout=0;

	function empezarDetener()
	{
		if(timeout==0)
		{
			// empezar el cronometro
 
 
			// Obtenemos el valor actual
			inicio=vuelta=new Date().getTime();
 
			// iniciamos el proceso
			funcionando();
		}else{
			// detemer el cronometro
			
			clearTimeout(timeout);
			timeout=0;
		}
	}
 
	function funcionando()
	{
		// obteneos la fecha actual
		var actual = new Date().getTime();
 
		// obtenemos la diferencia entre la fecha actual y la de inicio
		var diff=new Date(actual-inicio);
 
		// mostramos la diferencia entre la fecha actual y la inicial
		var result=LeadingZero(diff.getUTCHours())+":"+LeadingZero(diff.getUTCMinutes())+":"+LeadingZero(diff.getUTCSeconds());
		document.getElementById('crono').innerHTML = result;
 
		// Indicamos que se ejecute esta función nuevamente dentro de 1 segundo
		timeout=setTimeout("funcionando()",1000);
	}
 
	/* Funcion que pone un 0 delante de un valor si es necesario */
	function LeadingZero(Time) {
		return (Time < 10) ? "0" + Time : + Time;
	}
<%-- 	if (<%= (visualizo != false)%>) {
		empezarDetener();
	} --%>
	
	</script>
		<style>
	.crono_wrapper {text-align:center;width:200px;}
	</style>
	<body>
	
	<h3>Adivina un número entre ${sessionScope.intervalo1} y ${sessionScope.intervalo2}</h3>

	<c:if test="${visualizo == true}">
		<form action='adivina' method='post'>
			Intruduce valor: <input id ='numero' type='number' name='numero' onFocus='document.getElementById("numero").value=""'> 
			<input type='submit' name='dataInput' value='aceptar'>
		</form>
	</c:if>
	
	<%-- <h4>Adivina un número entre <%=session.getAttribute("intervalo1") %> y <%=session.getAttribute("intervalo2") %></h4>
	<%
		System.out.println("visualizo: " + visualizo);
		if (visualizo) {
			out.print(	"<form action='adivina' method='post'>" + 
						"<input id ='numero' type='number' name='numero' onFocus='document.getElementById(\"numero\").value=\"\"'>" + 
						"<input type='submit' name='dataInput' value='aceptar'>" + 
						"</form>");
		}
	
	%> --%>
	<!-- <form action='adivina' method='post'>
		<input id ='numero' type='number' name='numero' onFocus='document.getElementById("numero").value=""'>
		<button type="submit">Aceptar</button>
	</form> -->
	<table>
		<tr>
			<th>intento</th>
			<th>tiempo</th>
			<th>numero</th>
			<th>mayor/menor</th>
	
		<c:forEach var="intento" begin="0" items="${listaIntentos}">
			<tr> 
				<td>${intento.orden}</td>  
				<td>${intento.fechaHora}</td>   
				<td>${intento.numeroJugado}</td>   
				<td>${intento.mensaje}</td> 
			</tr>
			
		</c:forEach>
		
		<%-- <% 
		
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
		
		
		%> --%>

	</table>
	<c:catch var="catchExeption">
		<c:set var="numIntentos" value="${listaIntentos.size()-1}"/>
		<c:set var="acierto" value="${ listaIntentos.get(numIntentos).getMensaje()}"/>
			<c:if test="${acierto == 'Lo has Encontrado' }">
				<p>Tiempo total en segundos: ${Duration.between(listaIntentos.get(0).getFechaHora(),listaIntentos.get(listaIntentos.size()-1).getFechaHora()).getSeconds()}</p>			
				<form action='adivina' method='post'>
					<br><input type='submit' name='dataInput' value='reiniciar'>
				</form>
    		</c:if>
	</c:catch>
     <c:if test = "${catchException != null}">

     </c:if>
		<%-- <% 
		
		try {
			int a = listaIntentos.size()-1;
			String b = listaIntentos.get(a).getMensaje();
			if (b == "Lo has Encontrado") {
				out.print(	"<p>Tiempo total en segundos:" +
							Duration.between(listaIntentos.get(0).getFechaHora(),listaIntentos.get(listaIntentos.size()-1).getFechaHora()).getSeconds() +
							"</p>"
							
						);
				
				out.print (	"<form action='adivina' method='post'>" + 
							"<br><input type='submit' name='dataInput' value='reiniciar'>" +
							"</form>"
						);			
			}
			
		} catch (IndexOutOfBoundsException e) {
			System.out.print("dentro del catch de jugar.jsp ---- " + e);
		}
		%> --%>

		<c:if test = "${visualizo == true}">
			<div class="crono_wrapper">
				<h2 id='crono'>00:00:00</h2>
			</div>
    	</c:if>
		
		<%-- <%
		if (visualizo) {
		%>
		<div class="crono_wrapper">
			<h2 id='crono'>00:00:00</h2>
		</div>
		<%
		}
		%> --%>
		<!-- El cronometro solo puede reinciarse si metes un numero correcto
			En caso de que se pierda el tiempo metiendo letras o numeros no correctos
			El contador de tiempo continúa por listo -->
		<c:if test="${sessionScope.error != 'error' }">
			<script>empezarDetener();</script>
		</c:if>
		
	</body>
</html>