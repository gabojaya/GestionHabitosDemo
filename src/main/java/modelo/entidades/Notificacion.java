package modelo.entidades;

import java.io.Serializable;
import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="notificacion")
public class Notificacion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idNotificacion")
	private int idNotificacion;
	@Column(name="tipo")
	private String tipo;
	@Column(name="mensaje")
	private String mensaje;
	@Column(name="fechaEnvio")
	private Date fechaEnvio;
	@Column(name="leido")
	private boolean leido;
	@OneToOne
	@JoinColumn(name = "idRecordatorio", nullable = false)
	private Recordatorio recordatorioAsociado;
	

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
