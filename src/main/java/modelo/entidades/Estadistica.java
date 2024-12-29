package modelo.entidades;

import java.io.Serializable;
import java.sql.Time;

public class Estadistica implements Serializable {

	private static final long serialVersionUID = 1L;

	private int idEstadistica;
	private Habito[] habitos;
	private int cantidadAcumulada;
	private Time tiempoAcumulado;
	private int totalEjecuciones;
	private double progresoAcumulado;
	private boolean estado;

	public Estadistica() {
	}

	public Estadistica(int idEstadistica, Habito[] habitos, int cantidadAcumulada, Time tiempoAcumulado,
			int totalEjecuciones, double progresoAcumulado, boolean estado) {
		this.idEstadistica = idEstadistica;
		this.habitos = habitos;
		this.cantidadAcumulada = cantidadAcumulada;
		this.tiempoAcumulado = tiempoAcumulado;
		this.totalEjecuciones = totalEjecuciones;
		this.progresoAcumulado = progresoAcumulado;
		this.estado = estado;
	}

	public int getIdEstadistica() {
		return idEstadistica;
	}

	public void setIdEstadistica(int idEstadistica) {
		this.idEstadistica = idEstadistica;
	}

	public Habito[] getHabitos() {
		return habitos;
	}

	public void setHabitos(Habito[] habitos) {
		this.habitos = habitos;
	}

	public int getCantidadAcumulada() {
		return cantidadAcumulada;
	}

	public void setCantidadAcumulada(int cantidadAcumulada) {
		this.cantidadAcumulada = cantidadAcumulada;
	}

	public Time getTiempoAcumulado() {
		return tiempoAcumulado;
	}

	public void setTiempoAcumulado(Time tiempoAcumulado) {
		this.tiempoAcumulado = tiempoAcumulado;
	}

	public int getTotalEjecuciones() {
		return totalEjecuciones;
	}

	public void setTotalEjecuciones(int totalEjecuciones) {
		this.totalEjecuciones = totalEjecuciones;
	}

	public double getProgresoAcumulado() {
		return progresoAcumulado;
	}

	public void setProgresoAcumulado(double progresoAcumulado) {
		this.progresoAcumulado = progresoAcumulado;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

}
