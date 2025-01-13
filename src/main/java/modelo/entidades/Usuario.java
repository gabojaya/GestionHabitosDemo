package modelo.entidades;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="idUsuario")
	private int idUsuario;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="apellido")
	private String apellido;
	
	@Column(name="nombreUsuario")
	private String nombreUsuario;
	
	@Column(name="email")
	private String email;
	
	@Column(name="clave")
	private String clave;
	
	@Column(name="fechaInicio")
	private Date fechaInicio;
	
	@Column(name="notificacionesActivas")
	private boolean notificacionesActivas;

	public Usuario() {
	}

	public Usuario( String nombre, String apellido, String nombreUsuario, String email, String clave) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.clave = clave;
		this.fechaInicio = new Date(System.currentTimeMillis());
		this.notificacionesActivas = true;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public boolean isNotificacionesActivas() {
		return notificacionesActivas;
	}

	public void setNotificacionesActivas(boolean notificacionesActivas) {
		this.notificacionesActivas = notificacionesActivas;
	}
	

}
