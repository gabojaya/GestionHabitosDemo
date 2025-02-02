package modelo.entidades;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="meta")
public class Meta implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idMeta")
	private int idMeta;
	
	@ManyToOne(cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "idUsuario", nullable = false)
	private Usuario usuario;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="fechaInicio")
	private Date fechaInicio;
	
	@Column(name="fechaFin")
	private Date fechaFin;
	
	@OneToMany(mappedBy = "metaAsociada", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	@JsonIgnore
	private List<Habito> habitos;
	
	@Column(name="estado")
	private boolean estado;
	
	@Column(name="diasObjetivo")
	private int diasObjetivo;
	
	@Column(name="nombre")
	private String nombre;
	

	public Meta() {
	}
	
	

	public Meta(int idMeta, Usuario usuario, String descripcion, Date fechaInicio, Date fechaFin, List<Habito> habitos,
			boolean estado, int diasObjetivo, String nombre) {
		super();
		this.idMeta = idMeta;
		this.usuario = usuario;
		this.descripcion = descripcion;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.habitos = habitos;
		this.estado = estado;
		this.diasObjetivo = diasObjetivo;
		this.nombre = nombre;
	}



	public int getIdMeta() {
		return idMeta;
	}

	public void setIdMeta(int idMeta) {
		this.idMeta = idMeta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public int getDiasObjetivo() {
		return diasObjetivo;
	}

	public void setDiasObjetivo(int diasObjetivo) {
		this.diasObjetivo = diasObjetivo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}



	public List<Habito> getHabitos() {
		return habitos;
	}



	public void setHabitos(List<Habito> habitos) {
		this.habitos = habitos;
	}
	
	

	
	

}
