package modelo.entidades;

import java.io.Serializable;
import java.sql.Date;

public class Recordatorio implements Serializable {

	private static final long serialVersionUID = 1L;

	private int idRecordatorio;
	private String mensaje;
	private Date fechaHora;
	private int frecuencia;
	private boolean estado;
	private Habito habitoAsociado;
	private boolean repetir;
	private Date fechaInicio;
	private Date fechaFin;

	public Recordatorio() {
	}

	public Recordatorio(int idRecordatorio, String mensaje, Date fechaHora, int frecuencia, boolean estado,
			Habito habitoAsociado, boolean repetir, Date fechaInicio, Date fechaFin) {
		this.idRecordatorio = idRecordatorio;
		this.mensaje = mensaje;
		this.fechaHora = fechaHora;
		this.frecuencia = frecuencia;
		this.estado = estado;
		this.habitoAsociado = habitoAsociado;
		this.repetir = repetir;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	public int getIdRecordatorio() {
		return idRecordatorio;
	}

	public void setIdRecordatorio(int idRecordatorio) {
		this.idRecordatorio = idRecordatorio;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public int getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(int frecuencia) {
		this.frecuencia = frecuencia;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Habito getHabitoAsociado() {
		return habitoAsociado;
	}

	public void setHabitoAsociado(Habito habitoAsociado) {
		this.habitoAsociado = habitoAsociado;
	}

	public boolean isRepetir() {
		return repetir;
	}

	public void setRepetir(boolean repetir) {
		this.repetir = repetir;
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
}
