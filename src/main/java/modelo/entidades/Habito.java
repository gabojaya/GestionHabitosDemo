package modelo.entidades;

import java.io.Serializable;
import java.sql.Time;
import java.util.List;

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
@Table(name="habito")
public class Habito implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idHabito")
	private int idHabito;
	@Column(name="nombre")
	private String nombre;
	@Column(name="categoria")
	private String categoria;
	
	@ManyToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "idMeta", nullable = false)
	private Meta metaAsociada;
	
	@Column(name="estado")
	private boolean estado;
	@Column(name="tipoMedicion")
	private String tipoMedicion;
	@Column(name="frecuencia")
	private int frecuencia;
	@Column(name="cantidadTotal")
	private int cantidadTotal;
	@Column(name="tiempoTotal")
	private Time tiempoTotal;
	
	@OneToMany(mappedBy = "habito", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Ejecucion> ejecuciones;

    @OneToMany(mappedBy = "habito", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Estadistica> estadisticas;

    @OneToMany(mappedBy = "habitoAsociado", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Recordatorio> recordatorios;
	
	

	public Habito() {
	}

	public Habito(int idHabito, String nombre, String categoria, Meta metaAsociada, boolean estado, String tipoMedicion,
			int frecuencia, int cantidadTotal, Time tiempoTotal, List<Ejecucion> ejecuciones,
			List<Estadistica> estadisticas, List<Recordatorio> recordatorios) {
		super();
		this.idHabito = idHabito;
		this.nombre = nombre;
		this.categoria = categoria;
		this.metaAsociada = metaAsociada;
		this.estado = estado;
		this.tipoMedicion = tipoMedicion;
		this.frecuencia = frecuencia;
		this.cantidadTotal = cantidadTotal;
		this.tiempoTotal = tiempoTotal;
		this.ejecuciones = ejecuciones;
		this.estadisticas = estadisticas;
		this.recordatorios = recordatorios;
	}

	public int getIdHabito() {
		return idHabito;
	}

	public void setIdHabito(int idHabito) {
		this.idHabito = idHabito;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	

	public Meta getMetaAsociada() {
		return metaAsociada;
	}

	public void setMetaAsociada(Meta metaAsociada) {
		this.metaAsociada = metaAsociada;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getTipoMedicion() {
		return tipoMedicion;
	}

	public void setTipoMedicion(String tipoMedicion) {
		this.tipoMedicion = tipoMedicion;
	}

	public int getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(int frecuencia) {
		this.frecuencia = frecuencia;
	}

	public int getCantidadTotal() {
		return cantidadTotal;
	}

	public void setCantidadTotal(int cantidadTotal) {
		this.cantidadTotal = cantidadTotal;
	}

	public Time getTiempoTotal() {
		return tiempoTotal;
	}

	public void setTiempoTotal(Time tiempoTotal) {
		this.tiempoTotal = tiempoTotal;
	}

	public List<Ejecucion> getEjecuciones() {
		return ejecuciones;
	}

	public void setEjecuciones(List<Ejecucion> ejecuciones) {
		this.ejecuciones = ejecuciones;
	}

	public List<Estadistica> getEstadisticas() {
		return estadisticas;
	}

	public void setEstadisticas(List<Estadistica> estadisticas) {
		this.estadisticas = estadisticas;
	}

	public List<Recordatorio> getRecordatorios() {
		return recordatorios;
	}

	public void setRecordatorios(List<Recordatorio> recordatorios) {
		this.recordatorios = recordatorios;
	}
	

}
