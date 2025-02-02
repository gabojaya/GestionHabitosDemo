package modelo.entidades;

import java.io.Serializable;
import java.sql.Time;

import jakarta.persistence.CascadeType;
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
	
	@ManyToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "idHabito", nullable = false)
	private Habito habito;
	
	@Column(name="cantidadAcumulada")
	private int cantidadAcumulada;
	
	@Column(name="tiempoAcumulado")
	private Time tiempoAcumulado;
	
	@Column(name="totalEjecuciones")
	private int totalEjecuciones;
	
	@Column(name="cantidadFinalEsperada")
	private int cantidadFinalEsperada;
	
	@Column(name="tiempoFinalEsperado")
	private Time tiempoFinalEsperado;
	
	@Column(name="progresoAcumulado")
	private double progresoAcumulado;
	
	@Column(name="estado")
	private boolean estado;

	public Estadistica() {
	}

	public Estadistica(int idEstadistica, Habito habito, int cantidadAcumulada, Time tiempoAcumulado,
			int totalEjecuciones, int cantidadFinalEsperada, Time tiempoFinalEsperado, double progresoAcumulado,
			boolean estado) {
		super();
		this.idEstadistica = idEstadistica;
		this.habito = habito;
		this.cantidadAcumulada = cantidadAcumulada;
		this.tiempoAcumulado = tiempoAcumulado;
		this.totalEjecuciones = totalEjecuciones;
		this.cantidadFinalEsperada = cantidadFinalEsperada;
		this.tiempoFinalEsperado = tiempoFinalEsperado;
		this.progresoAcumulado = progresoAcumulado;
		this.estado = estado;
	}

	public int getIdEstadistica() {
		return idEstadistica;
	}

	public void setIdEstadistica(int idEstadistica) {
		this.idEstadistica = idEstadistica;
	}

	public Habito getHabito() {
		return habito;
	}

	public void setHabito(Habito habito) {
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

	public int getCantidadFinalEsperada() {
		return cantidadFinalEsperada;
	}

	public void setCantidadFinalEsperada(int cantidadFinalEsperada) {
		this.cantidadFinalEsperada = cantidadFinalEsperada;
	}

	public Time getTiempoFinalEsperado() {
		return tiempoFinalEsperado;
	}

	public void setTiempoFinalEsperado(Time tiempoFinalEsperado) {
		this.tiempoFinalEsperado = tiempoFinalEsperado;
	}
	
	

}
