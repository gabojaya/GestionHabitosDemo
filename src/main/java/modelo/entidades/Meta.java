package modelo.entidades;

import java.io.Serializable;
import java.sql.Date;

public class Meta implements Serializable {

	private static final long serialVersionUID = 1L;

	private int idMeta;
	private int idUsuario;
	private String descripcion;
	private Date fechaInicio;
	private Date fechaFin;
	private Habito[] habitos;
	private double progreso;
	private boolean estado;
	private int diasObjetivo;
	private String nombre;

	public Meta() {
	}

	public Meta(int idMeta, int idUsuario, String descripcion, Date fechaInicio, Date fechaFin, Habito[] habitos, double progreso,
			boolean estado, int diasObjetivo, String nombre) {
		this.idMeta = idMeta;
		this.idUsuario = idUsuario;
		this.descripcion = descripcion;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.habitos = habitos;
		this.progreso = progreso;
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

	public Habito[] getHabitos() {
		return habitos;
	}

	public void setHabitos(Habito[] habitos) {
		this.habitos = habitos;
	}

	public double getProgreso() {
		return progreso;
	}

	public void setProgreso(double progreso) {
		this.progreso = progreso;
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

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	

}
