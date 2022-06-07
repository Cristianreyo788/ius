package com.bbva.libranza.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "LIBRANZA_EXECUTION")
public class Execution {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "libranza_ejecucion_seq")
	@SequenceGenerator(name = "libranza_ejecucion_seq", sequenceName = "LIBRANZA_EJECUCION_SEQ", allocationSize=1)
	private Long id;

	@Column(name = "FECHA_INICIO")
	private Long fechaInicio;

	@Column(name = "FECHA_FIN")
	private Long fechaFin;
	
	/*@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "filecase_id", nullable = false)
	private FileCase archivo;*/
	
	@Column(name = "ARCHIVO")
	private String nombreArchivo;

	@Column(name = "NRO_REGISTROS")
	private int nroRegistros;

	@Column(name = "NRO_REGISTROS_OK")
	private int nroRegistrosOk;

	@Column(name = "NRO_REGISTROS_FALLIDOS")
	private int nroRegistrosFallidos;

	@Column(name = "ESTADO")
	private String estado;

	@OneToMany(cascade = CascadeType.ALL,
			//orphanRemoval = true,
			fetch = FetchType.LAZY, 
			mappedBy = "ejecucion")
	private List<Case> casos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFechaInicio() {
		return fechaInicio;
	}

	public List<Case> getCasos() {
		return casos;
	}

	public void setCasos(List<Case> casos) {
		this.casos = casos;
	}

	public void setFechaInicio(Long fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Long getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Long fechaFin) {
		this.fechaFin = fechaFin;
	}

	public int getNroRegistros() {
		return nroRegistros;
	}

	public void setNroRegistros(int nroRegistros) {
		this.nroRegistros = nroRegistros;
	}

	public int getNroRegistrosOk() {
		return nroRegistrosOk;
	}

	public void setNroRegistrosOk(int nroRegistrosOk) {
		this.nroRegistrosOk = nroRegistrosOk;
	}

	public int getNroRegistrosFallidos() {
		return nroRegistrosFallidos;
	}

	public void setNroRegistrosFallidos(int nroRegistrosFallidos) {
		this.nroRegistrosFallidos = nroRegistrosFallidos;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getArchivo() {
		return nombreArchivo;
	}

	public void setArchivo(String archivo) {
		this.nombreArchivo = archivo;
	}

}
