package modelo.entidades;

import java.io.Serializable;
import java.sql.Date;
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
@Table(name="recordatorio")
public class Recordatorio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idRecordatorio")
	private int idRecordatorio;
	
	@Column(name="mensaje")
	private String mensaje;
	
	@Column(name="hora")
	private Time hora;
	
	@Column(name="estado")
	private boolean estado;
	
	@ManyToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "idHabito", nullable = false)
	private Habito habitoAsociado;
	
	@Column(name="repetir")
	private boolean repetir;
	
	@Column(name="fechaInicio")
	private Date fechaInicio;
	
	@Column(name="fechaFin")
	private Date fechaFin;
	
	@OneToMany(mappedBy = "recordatorio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ejecucion> ejecuciones;

	public Recordatorio() {
	}
	

	public Recordatorio(int idRecordatorio, String mensaje, Time hora, boolean estado, Habito habitoAsociado,
			boolean repetir, Date fechaInicio, Date fechaFin, List<Ejecucion> ejecuciones) {
		super();
		this.idRecordatorio = idRecordatorio;
		this.mensaje = mensaje;
		this.hora = hora;
		this.estado = estado;
		this.habitoAsociado = habitoAsociado;
		this.repetir = repetir;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.ejecuciones = ejecuciones;
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

	public Time getHora() {
		return hora;
	}

	public void setHora(Time hora) {
		this.hora = hora;
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


	public List<Ejecucion> getEjecuciones() {
		return ejecuciones;
	}


	public void setEjecuciones(List<Ejecucion> ejecuciones) {
		this.ejecuciones = ejecuciones;
	}
	
}
