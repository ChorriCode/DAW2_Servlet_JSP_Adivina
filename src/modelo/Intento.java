package modelo;

import java.time.LocalDate;

public class Intento {
	
	private LocalDate fechaHora;
	private int orden;
	private int numeroJugado;
	private String mensaje;
	
	public Intento() {
		super();
	}

	public LocalDate getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDate fechaHora) {
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
