package modelo.entidades;

import java.io.Serializable;
import java.sql.Date;

public class Notificacion implements Serializable {

	private static final long serialVersionUID = 1L;

	private int idNotificacion;
	private String tipo;
	private String mensaje;
	private Date fechaEnvio;
	private boolean leido;

	public Notificacion() {
	}

	public Notificacion(int idNotificacion, String tipo, String mensaje, Date fechaEnvio, boolean leido) {
		this.idNotificacion = idNotificacion;
		this.tipo = tipo;
		this.mensaje = mensaje;
		this.fechaEnvio = fechaEnvio;
		this.leido = leido;
	}

	public int getIdNotificacion() {
		return idNotificacion;
	}

	public void setIdNotificacion(int idNotificacion) {
		this.idNotificacion = idNotificacion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public boolean isLeido() {
		return leido;
	}

	public void setLeido(boolean leido) {
		this.leido = leido;
	}

}
