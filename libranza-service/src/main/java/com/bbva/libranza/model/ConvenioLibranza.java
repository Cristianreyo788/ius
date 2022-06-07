package com.bbva.libranza.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "CONVENIO_LIBRANZA")
public class ConvenioLibranza {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "CODIGOSUBPRODUCTO")
	private int codigoSubproducto;
	
	@Column(name = "NUMEROCONVENIO")
	private int numeroConvenio;
	
	@Column(name = "CODIGOCONVENIO")
	private String codigoConvenio;
	
	@Column(name = "COD")
	private String cod;
	
	@Column(name = "NOMBRECONVENIO")
	private String nombreConvenio;
	
	@Column(name = "TIPOGESTION")
	private String tipoGestion;
	
	@Column(name = "GRUPOCONVENIO")
	private String grupoConvenio;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getCodigoSubproducto() {
		return codigoSubproducto;
	}

	public void setCodigoSubproducto(int codigoSubproducto) {
		this.codigoSubproducto = codigoSubproducto;
	}

	public int getNumeroConvenio() {
		return numeroConvenio;
	}

	public void setNumeroConvenio(int numeroConvenio) {
		this.numeroConvenio = numeroConvenio;
	}

	public String getCodigoConvenio() {
		return codigoConvenio;
	}

	public void setCodigoConvenio(String codigoConvenio) {
		this.codigoConvenio = codigoConvenio;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public String getNombreConvenio() {
		return nombreConvenio;
	}

	public void setNombreConvenio(String nombreConvenio) {
		this.nombreConvenio = nombreConvenio;
	}

	public String getTipoGestion() {
		return tipoGestion;
	}

	public void setTipoGestion(String tipoGestion) {
		this.tipoGestion = tipoGestion;
	}

	public String getGrupoConvenio() {
		return grupoConvenio;
	}

	public void setGrupoConvenio(String grupoConvenio) {
		this.grupoConvenio = grupoConvenio;
	}

}
