package modelo.entidades;

import java.io.Serializable;
import java.sql.Date;
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
@Table(name = "Ejecucion")
public class Ejecucion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idEjecucion")
	private int idEjecucion;

	@ManyToOne(cascade = {CascadeType.REMOVE})
	@JoinColumn(name = "idUsuario", nullable = false)
	private Usuario usuario;

	@ManyToOne(cascade = {CascadeType.REMOVE})
	@JoinColumn(name = "idHabito", nullable = false)
	private Habito habito;

	@ManyToOne(cascade = {CascadeType.REMOVE})
	@JoinColumn(name = "idRecordatorio", nullable = false)
	private Recordatorio recordatorio;

	@Column(name = "fecha")
	private Date fecha;

	@Column(name = "hora")
	private Time hora;

	@Column(name = "estado")
	private boolean estado;

	@Column(name = "cantidadTotal")
	private int cantidadTotal;

	@Column(name = "cantidadCompleta")
	private int cantidadCompleta;

	@Column(name = "tiempoTotal")
	private Time tiempoTotal;

	@Column(name = "tiempoCompletado")
	private Time tiempoCompletado;

	public Ejecucion() {
	}

	public Ejecucion(int idEjecucion, Usuario usuario, Habito habito, Recordatorio recordatorio, Date fecha, Time hora,
			boolean estado, int cantidadTotal, int cantidadCompleta, Time tiempoTotal, Time tiempoCompletado) {
		super();
		this.idEjecucion = idEjecucion;
		this.usuario = usuario;
		this.habito = habito;
		this.recordatorio = recordatorio;
		this.fecha = fecha;
		this.hora = hora;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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

	public Time getHora() {
		return hora;
	}

	public void setHora(Time hora) {
		this.hora = hora;
	}

	public Recordatorio getRecordatorio() {
		return recordatorio;
	}

	public void setRecordatorio(Recordatorio recordatorio) {
		this.recordatorio = recordatorio;
	}

}
