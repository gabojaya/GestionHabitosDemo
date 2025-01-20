package modelo.entidades;

import java.io.Serializable;
import java.sql.Time;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="estadistica")
public class Estadistica implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idEstadistica")
	private int idEstadistica;
	
	@ManyToOne
    @JoinColumn(name = "idHabito", nullable = false)
	private Habito habito;
	
	@Column(name="cantidadAcumulada")
	private int cantidadAcumulada;
	
	@Column(name="tiempoAcumulado")
	private Time tiempoAcumulado;
	
	@Column(name="totalEjecuciones")
	private int totalEjecuciones;
	
	@Column(name="progresoAcumulado")
	private double progresoAcumulado;
	
	@Column(name="estado")
	private boolean estado;

	public Estadistica() {
	}

	public Estadistica(int idEstadistica, Habito habito, int cantidadAcumulada, Time tiempoAcumulado,
			int totalEjecuciones, double progresoAcumulado, boolean estado) {
		this.idEstadistica = idEstadistica;
		this.habito = habito;
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

	public Habito getHabitos() {
		return habito;
	}

	public void setHabitos(Habito habito) {
		this.habito = habito;
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
