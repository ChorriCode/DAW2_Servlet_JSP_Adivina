package modelo;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Intento implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocalDateTime fechaHora;
	private int orden;
	private int numeroJugado;
	private String mensaje;
	
	public Intento() {
		super();
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public int getNumeroJugado() {
		return numeroJugado;
	}

	public void setNumeroJugado(int numeroJugado) {
		this.numeroJugado = numeroJugado;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	
	
	
	
}
