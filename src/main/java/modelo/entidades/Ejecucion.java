package modelo.entidades;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class Ejecucion implements Serializable {

	private static final long serialVersionUID = 1L;

	private int idEjecucion;
	private Habito habito;
	private Date fecha;
	private boolean estado;
	private int cantidadTotal;
	private int cantidadCompleta;
	private Time tiempoTotal;
	private Time tiempoCompletado;

	public Ejecucion() {
	}

	public Ejecucion(int idEjecucion, Habito habito, Date fecha, boolean estado, int cantidadTotal,
			int cantidadCompleta, Time tiempoTotal, Time tiempoCompletado) {
		this.idEjecucion = idEjecucion;
		this.habito = habito;
		this.fecha = fecha;
		this.estado = estado;
		this.cantidadTotal = cantidadTotal;
		this.cantidadCompleta = cantidadCompleta;
		this.tiempoTotal = tiempoTotal;
		this.tiempoCompletado = tiempoCompletado;
	}

	public int getIdEjecucion() {
		return idEjecucion;
	}

	public void setIdEjecucion(int idEjecucion) {
		this.idEjecucion = idEjecucion;
	}

	public Habito getHabito() {
		return habito;
	}

	public void setHabito(Habito habito) {
		this.habito = habito;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public int getCantidadTotal() {
		return cantidadTotal;
	}

	public void setCantidadTotal(int cantidadTotal) {
		this.cantidadTotal = cantidadTotal;
	}

	public int getCantidadCompleta() {
		return cantidadCompleta;
	}

	public void setCantidadCompleta(int cantidadCompleta) {
		this.cantidadCompleta = cantidadCompleta;
	}

	public Time getTiempoTotal() {
		return tiempoTotal;
	}

	public void setTiempoTotal(Time tiempoTotal) {
		this.tiempoTotal = tiempoTotal;
	}

	public Time getTiempoCompletado() {
		return tiempoCompletado;
	}

	public void setTiempoCompletado(Time tiempoCompletado) {
		this.tiempoCompletado = tiempoCompletado;
	}

}
