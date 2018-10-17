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
	HttpSession session;
	RequestDispatcher dispatcher;
	ArrayList<Intento> listaIntentos;
	String ruta = null;
       
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
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// asignacion de variables;
		session = request.getSession();
		String reiniciar = request.getParameter("reiniciar");
		listaIntentos = (ArrayList<Intento>) session.getAttribute("listaIntentos");
		String int1 = request.getParameter("intervalo1");
		String int2 = request.getParameter("intervalo2");
		String dataInput = request.getParameter("dataInput");
		
		//Comprobamos si es a primera vez creamos la lista de intentoss
		if (session.getAttribute("listaIntentos") == null) {			
			listaIntentos = new ArrayList<Intento>();			
			session.setAttribute("visualizo", true);
			session.setAttribute("listaIntentos", listaIntentos);
		}
		
		//Capturamos los inputs de los jsp para según sean tomar una desición
		switch (dataInput) {
			case "valida":
				ruta = validaIntervalo(int1, int2, response);
				break;
			case "aceptar":
				aceptarNumero(request);
				ruta = "/jsp/jugar.jsp";
				break;
			case "reiniciar":
				reiniciarPartida(session, response, request);
				ruta = "/index.jsp";
				break;
			default:
				break;
		}


		dispatcher = getServletContext().getRequestDispatcher(ruta);
		dispatcher.forward(request,response);
		//getServletConfig().getServletContext().getRequestDispatcher(ruta).forward(request, response);

		
	}
	
	//Si el intervalo es invalido regresamos a index.jsp en caso de que sea valido nos envía a jugar.jsp
	public String validaIntervalo(String int1, String int2, HttpServletResponse response) {		
		try {
			int num1 = Integer.parseInt(int1);
			int num2 = Integer.parseInt(int2);
			if (num1 > num2 || num1 == num2) {
				session.setAttribute("error", "intervalo incorrecto");
				return "/index.jsp";
			}
		} catch (NumberFormatException e) {
				session.setAttribute("error", "intervalo incorrecto");
				return "/index.jsp";
		}
		int numAleatorio = (int) Math.round((Math.random() * (Integer.parseInt(int2)-Integer.parseInt(int1) + Integer.parseInt(int1))));
		session.setAttribute("numAleatorio", numAleatorio);
		session.setAttribute("error", "");
		session.setAttribute("intervalo1", int1);
		session.setAttribute("intervalo2", int2);
		return "/jsp/jugar.jsp";
	}
	
	// Se encarga de validar que el número que vamos a jugar en cada intento esté dentro de los valores permitidos
	public boolean validaNumero(String numero, int intervaloMin, int intervaloMax) {		
		try {
			int numValido = Integer.parseInt(numero);
			if (numValido >= intervaloMin && numValido <= intervaloMax) {
				session.setAttribute("error", "");
				return true;
			}			
		} catch (NumberFormatException e) {	
		}		
		session.setAttribute("error", "error");
		return false;		
	}
	
	// En caso que el número que intentamos adivinar esté entre los intervalos sea correcto lo
	// añadimos al array de intentos llamando al método addNumero()
	public void aceptarNumero(HttpServletRequest request) {
		int orden = listaIntentos.size();
		String num = request.getParameter("numero");
		int intervaloMin = Integer.parseInt((String) session.getAttribute("intervalo1"));
		int intervaloMax = Integer.parseInt((String) session.getAttribute("intervalo2"));
		if (validaNumero(num, intervaloMin, intervaloMax )) {
			addNumero(request, listaIntentos, session, orden);
		}
	}
	
	// Este método es llamado desde aceptarNumero() para que en caso de que sea un número válido lo añadimos al objeto intento
	//y ese objeto al array de intentos.
	public void addNumero(HttpServletRequest request, ArrayList<Intento> listaIntentos, HttpSession session, int orden ) {	
		int numero = Integer.parseInt(request.getParameter("numero"));
		Intento unIntento = new Intento();
		unIntento.setFechaHora(LocalDateTime.now());
		unIntento.setNumeroJugado(numero);
		unIntento.setOrden(++orden);				
		String mensaje = aciertoFallo(unIntento, session);
		unIntento.setMensaje(mensaje);			
		listaIntentos.add(unIntento);
	}
	
	public String aciertoFallo(Intento unIntento, HttpSession session) {
		int numAleatorio = (int) session.getAttribute("numAleatorio");
		int numeroJugado = unIntento.getNumeroJugado();
		System.out.println(">>>>>>>>>>>numAleatorio: " + numAleatorio + " - numeroJugado: " + numeroJugado);
		if (numeroJugado > numAleatorio) {
			return "intenta uno MENOR";
		}else if (numeroJugado < numAleatorio) {
			return "intenta uno MAYOR";
		} 
		session.setAttribute("visualizo", false);
		return "Lo has Encontrado";
	
	}
	
	public void reiniciarPartida(HttpSession session, HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException {
		session.invalidate();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
	}
}
