package controlador;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Intento;

/**
 * Servlet implementation class ServletControlador
 */
@WebServlet("/adivina")
public class ServletControlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletControlador() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String reiniciar = request.getParameter("reiniciar");
		System.out.println(".equals(reiniciar)    " + "reiniciar".equals(reiniciar));
		if ("reiniciar".equals(reiniciar)) {
			System.out.println("request.getParameter(\"reiniciar\")===>>>>> " + request.getParameter("reiniciar"));
			session.invalidate();
			//request.getRequestDispatcher("index.jsp").include(request, response);
			response.setContentType("text/html");
			response.setCharacterEncoding("utf-8");
			request.getRequestDispatcher("index.jsp").forward(request, response);
			System.out.println("espero que esto no se ejecute");
		}
		ArrayList<Intento> listaIntentos = (ArrayList<Intento>) session.getAttribute("listaIntentos");
		String int1 = request.getParameter("intervalo1");
		String int2 = request.getParameter("intervalo2");
		if (session.getAttribute("listaIntentos") == null) {
			session.setAttribute("visualizo", true);
			System.out.println("session.getAttribute(\"listaIntentos\") = null");
			listaIntentos = new ArrayList<Intento>();
			session.setAttribute("listaIntentos", listaIntentos);
		}
		
		if (request.getParameter("valida") != null) {
			if (!validaIntervalo(int1, int2, response)) {
				System.out.println("Estoy dentro del intevarlo incorrecto");
				session.setAttribute("error", "intervalo incorrecto");
				response.sendRedirect("index.jsp");
			} else {
				int numAleatorio = (int) Math.round((Math.random() * (Integer.parseInt(int2)-Integer.parseInt(int1) + Integer.parseInt(int1))));
				session.setAttribute("numAleatorio", numAleatorio);
				session.setAttribute("error", "");
				session.setAttribute("intervalo1", int1);
				session.setAttribute("intervalo2", int2);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/jugar.jsp");
				dispatcher.forward(request,response);
			}
			
		} else {
			int orden = listaIntentos.size();
			String num = request.getParameter("numero");
			System.out.println("numero: " + num);
			int intervaloMin = Integer.parseInt((String) session.getAttribute("intervalo1"));
			int intervaloMax = Integer.parseInt((String) session.getAttribute("intervalo2"));
			System.out.println("intervaloMin: " + intervaloMin + "   intervaloMax: " + intervaloMax);
			if (validaNumero(num, intervaloMin, intervaloMax )) {
				int numero = Integer.parseInt(request.getParameter("numero"));
				Intento unIntento = new Intento();
				System.out.println("unIntento: " + unIntento);	
				unIntento.setFechaHora(LocalDateTime.now());
				unIntento.setNumeroJugado(numero);
				unIntento.setOrden(++orden);				
				System.out.println("unIntento.getNumeroJugado(): " + unIntento.getNumeroJugado());
				String mensaje = aciertoFallo(unIntento, session);
				unIntento.setMensaje(mensaje);			
				listaIntentos.add(unIntento);
				System.out.println("listaIntentos: " + listaIntentos);
				for (Intento intento : listaIntentos) {
					System.out.println("numeros: " + intento.getNumeroJugado() + " - lenght: " + listaIntentos.size());
				}
				
				//session.setAttribute("listaIntentos", listaIntentos);
				//System.out.println("------------------");
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/jugar.jsp");
				dispatcher.forward(request,response);
				
			} else {
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/jugar.jsp");
				dispatcher.forward(request,response);
			}

		}
		
		
	}
	
	public boolean validaIntervalo(String int1, String int2, HttpServletResponse response) {
		
		try {
			int num1 = Integer.parseInt(int1);
			int num2 = Integer.parseInt(int2);
			System.out.println("num1: " + num1 + " - num2: " + num2);
			if (num1 > num2 || num1 == num2) {
				System.out.println("Estoy dentro de validaIntervalo IF");
				return false;
			}

		} catch (NumberFormatException e) {
	
				System.out.println("Estoy dentro de validaIntervalo catch");
				return false;
		}
		System.out.println("Estoy dentro de validaIntervalo");
		return true;
	}

	public boolean validaNumero(String numero, int intervaloMin, int intervaloMax) {		
		try {
			int numValido = Integer.parseInt(numero);
			if (numValido >= intervaloMin && numValido <= intervaloMax) {
				System.out.println("numero validado");
				return true;
			}			
		} catch (NumberFormatException e) {
			e.printStackTrace();		
		}		
		return false;		
	}
	
	public String aciertoFallo(Intento unIntento, HttpSession session) {
		int numAleatorio = (int) session.getAttribute("numAleatorio");
		int numeroJugado = unIntento.getNumeroJugado();
		System.out.println(">>>>>>>>>>>numAleatorio: " + numAleatorio + " - numeroJugado: " + numeroJugado);
		if (numeroJugado > numAleatorio) {
			return "intenta uno menor";
		}else if (numeroJugado < numAleatorio) {
			return "intenta uno mayor";
		} 
		session.setAttribute("visualizo", false);
		return "Lo has Encontrado";
	
	}
}
