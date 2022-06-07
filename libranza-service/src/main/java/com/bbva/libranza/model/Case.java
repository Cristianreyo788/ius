package com.bbva.libranza.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "LIBRANZA_CASE")
public class Case {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "libranza_caso_seq")
	@SequenceGenerator(name = "libranza_caso_seq", sequenceName = "LIBRANZA_CASO_SEQ", allocationSize=1)
	private Long id;

	
    @Column(name = "FECHASOLICITUD")
	private String fechaSolicitud;
	
	@Column(name = "TIPOCLIENTE")
	private String tipoCliente;

	@Column(name = "TIPODOCUMENTO")
	private String tipoDocumento;

	@Column(name = "IDENTIFICACION")
	private String identificacion;

	@Column(name = "NOMBRECLIENTE")
	private String nombreCliente;

	@Column(name = "CUENTAABONAR")
	private String cuentaAbonar;

	@Column(name = "CORREOCLIENTE")
	private String correoCliente;

	@Column(name = "CELULARCLIENTE")
	private String celularCliente;

	@Column(name = "CODIGO")
	private String codigo;

	@Column(name = "CONSECUTIVO")
	private String consecutivo;

	@Column(name = "SUBPRODUCTO")
	private String subproducto;

	@Column(name = "CONVENIOLIBRANZA")
	private String convenioLibranza;

	@Column(name = "DESTINOPRINCIPAL")
	private String destinoPrincipal;

	@Column(name = "SCORINAPROBADO")
	private String scoringAprobado;

	@Column(name = "MONTOAPROBADO")
	private String montoAprobado;

	@Column(name = "PLAZO")
	private String plazo;

	@Column(name = "VALORCUOTA")
	private String valorCuota;
	
	@Column(name = "VALORSEGURO")
	private String valorSeguro;
    
	@Column(name = "VALORCUOTASEGURO")
	private String valorCuotaSeguro;

	@Column(name = "TASAEA")
	private String tasaEA;

	@Column(name = "TIPOCONVENIO")
	private String tipoConvenio;

	@Column(name = "OFICINASEGMENTAR")
	private String oficinaSegmentar;

	@Column(name = "GARANTIA")
	private String garantia;

	@Column(name = "CODIGOALTAMIRACLIENTE")
	private String codigoAltamiraCliente;

	@Column(name = "TECHOGLOBAL")
	private String techoGlobal;

	@Column(name = "BURO")
	private String buro;

	@Column(name = "DICTAMEN")
	private String dictamen;
	
	@Column(name = "DIRECCIONIP")
	private String direccionIP;
	
	@Column(name = "TIPOGESTION")
	private String tipoGestion;
	
	@Column(name = "GRUPOCONVENIO")
	private String grupoConvenio;
	
	@Column(name = "VENTAASISTIDA")
	private String ventaAsistida;

	@Column(name = "CANALSOLICITUD")
	private String canalSolicitud;

	@Column(name = "CANALVENTASELECCIONADO")
	private String canalVentaSeleccionado;
	
	@Column(name = "CEDULACOORDINADOR")
	private String cedulaCoordinador;
                               
	@Column(name = "NOMBRECOORDINADOR")
	private String nombreCoordinador;
	
	@Column(name = "REGIONAL")
	private String regional;
	
	@Column(name = "CODIGOASESOR")
	private String codigoAsesor;
	
	@Column(name = "IDENTIFICACIONASESOR")
	private String identificacionAsesor;
	
	@Column(name = "NOMBREASESOR")
	private String nombreAsesor;
	
	@Column(name = "OFICINAASESOR")
	private String oficinaAsesor;
	
	@Column(name = "CIUDADASESOR")
	private String ciudadAsesor;
	
	@Column(name = "TERRITORIALASESOR")
	private String territorialAsesor;
	
	@Column(name = "ZONARED")
	private String zonaRed;
                      
	@Column(name = "CONTRATORETANQUEAR")
	private String contratoRetanquear;

	@Column(name = "VALORRETANQUEO")
	private String valorRetanqueo;

	@Column(name = "CUOTARECOGER")
	private String cuotaRecoger;
	
	@Lob
	@Column(name = "OBLIGACIONES", columnDefinition="TEXT")
	private String obligaciones;

	@Column(name = "LINEA")
	private String line;

	@Lob
	@Column(name = "JSON", columnDefinition="TEXT")
	private String json;

	@Column(name = "ESTADO")
	private String estado;

	@Column(name = "NROCASO")
	private String nroCaso;
	
	@Column(name = "DESCRIPCION")
	private String descripcion;
	

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EJECUCION_FK", 
				referencedColumnName = "ID", 
				nullable = false)
	private Execution ejecucion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public String getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public String getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getCuentaAbonar() {
		return cuentaAbonar;
	}

	public void setCuentaAbonar(String cuentaAbonar) {
		this.cuentaAbonar = cuentaAbonar;
	}

	public String getCorreoCliente() {
		return correoCliente;
	}

	public void setCorreoCliente(String correoCliente) {
		this.correoCliente = correoCliente;
	}

	public String getCelularCliente() {
		return celularCliente;
	}

	public void setCelularCliente(String celularCliente) {
		this.celularCliente = celularCliente;
	}


	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getConsecutivo() {
		return consecutivo;
	}

	public void setConsecutivo(String consecutivo) {
		this.consecutivo = consecutivo;
	}

	public String getSubproducto() {
		return subproducto;
	}

	public void setSubproducto(String subproducto) {
		this.subproducto = subproducto;
	}

	public String getConvenioLibranza() {
		return convenioLibranza;
	}

	public void setConvenioLibranza(String convenioLibranza) {
		this.convenioLibranza = convenioLibranza;
	}

	public String getDestinoPrincipal() {
		return destinoPrincipal;
	}

	public void setDestinoPrincipal(String destinoPrincipal) {
		this.destinoPrincipal = destinoPrincipal;
	}

	public String getScoringAprobado() {
		return scoringAprobado;
	}

	public void setScoringAprobado(String scoringAprobado) {
		this.scoringAprobado = scoringAprobado;
	}

	public String getMontoAprobado() {
		return montoAprobado;
	}

	public void setMontoAprobado(String montoAprobado) {
		this.montoAprobado = montoAprobado;
	}

	public String getPlazo() {
		return plazo;
	}

	public void setPlazo(String plazo) {
		this.plazo = plazo;
	}

	public String getValorCuota() {
		return valorCuota;
	}

	public void setValorCuota(String valorCuota) {
		this.valorCuota = valorCuota;
	}
	
	
	
	public String getValorSeguro() {
		return valorSeguro;
	}

	public void setValorSeguro(String valorSeguro) {
		this.valorSeguro = valorSeguro;
	}

	public String getValorCuotaSeguro() {
		return valorCuotaSeguro;
	}

	public void setValorCuotaSeguro(String valorCuotaSeguro) {
		this.valorCuotaSeguro = valorCuotaSeguro;
	}

	public String getTasaEA() {
		return tasaEA;
	}

	public void setTasaEA(String tasaEA) {
		this.tasaEA = tasaEA;
	}

	public String getTipoConvenio() {
		return tipoConvenio;
	}

	public void setTipoConvenio(String tipoConvenio) {
		this.tipoConvenio = tipoConvenio;
	}

	public String getOficinaSegmentar() {
		return oficinaSegmentar;
	}

	public void setOficinaSegmentar(String oficinaSegmentar) {
		this.oficinaSegmentar = oficinaSegmentar;
	}

	public String getGarantia() {
		return garantia;
	}

	public void setGarantia(String garantia) {
		this.garantia = garantia;
	}

	public String getCodigoAltamiraCliente() {
		return codigoAltamiraCliente;
	}

	public void setCodigoAltamiraCliente(String codigoAltamiraCliente) {
		this.codigoAltamiraCliente = codigoAltamiraCliente;
	}

	public String getTechoGlobal() {
		return techoGlobal;
	}

	public void setTechoGlobal(String techoGlobal) {
		this.techoGlobal = techoGlobal;
	}

	public String getBuro() {
		return buro;
	}

	public void setBuro(String buro) {
		this.buro = buro;
	}

	public String getDictamen() {
		return dictamen;
	}

	public void setDictamen(String dictamen) {
		this.dictamen = dictamen;
	}

	public String getDireccionIP() {
		return direccionIP;
	}

	public void setDireccionIP(String direccionIP) {
		this.direccionIP = direccionIP;
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

	public String getVentaAsistida() {
		return ventaAsistida;
	}

	public void setVentaAsistida(String ventaAsistida) {
		this.ventaAsistida = ventaAsistida;
	}

	public String getCanalSolicitud() {
		return canalSolicitud;
	}

	public void setCanalSolicitud(String canalSolicitud) {
		this.canalSolicitud = canalSolicitud;
	}

	public String getCanalVentaSeleccionado() {
		return canalVentaSeleccionado;
	}

	public void setCanalVentaSeleccionado(String canalVentaSeleccionado) {
		this.canalVentaSeleccionado = canalVentaSeleccionado;
	}

	public String getCedulaCoordinador() {
		return cedulaCoordinador;
	}

	public void setCedulaCoordinador(String cedulaCoordinador) {
		this.cedulaCoordinador = cedulaCoordinador;
	}

	public String getNombreCoordinador() {
		return nombreCoordinador;
	}

	public void setNombreCoordinador(String nombreCoordinador) {
		this.nombreCoordinador = nombreCoordinador;
	}

	public String getRegional() {
		return regional;
	}

	public void setRegional(String regional) {
		this.regional = regional;
	}

	public String getCodigoAsesor() {
		return codigoAsesor;
	}

	public void setCodigoAsesor(String codigoAsesor) {
		this.codigoAsesor = codigoAsesor;
	}

	public String getIdentificacionAsesor() {
		return identificacionAsesor;
	}

	public void setIdentificacionAsesor(String identificacionAsesor) {
		this.identificacionAsesor = identificacionAsesor;
	}

	public String getNombreAsesor() {
		return nombreAsesor;
	}

	public void setNombreAsesor(String nombreAsesor) {
		this.nombreAsesor = nombreAsesor;
	}

	public String getOficinaAsesor() {
		return oficinaAsesor;
	}

	public void setOficinaAsesor(String oficinaAsesor) {
		this.oficinaAsesor = oficinaAsesor;
	}

	public String getCiudadAsesor() {
		return ciudadAsesor;
	}

	public void setCiudadAsesor(String ciudadAsesor) {
		this.ciudadAsesor = ciudadAsesor;
	}

	public String getTerritorialAsesor() {
		return territorialAsesor;
	}

	public void setTerritorialAsesor(String territorialAsesor) {
		this.territorialAsesor = territorialAsesor;
	}

	public String getZonaRed() {
		return zonaRed;
	}

	public void setZonaRed(String zonaRed) {
		this.zonaRed = zonaRed;
	}

	public String getContratoRetanquear() {
		return contratoRetanquear;
	}

	public void setContratoRetanquear(String contratoRetanquear) {
		this.contratoRetanquear = contratoRetanquear;
	}

	public String getValorRetanqueo() {
		return valorRetanqueo;
	}

	public void setValorRetanqueo(String valorRetanqueo) {
		this.valorRetanqueo = valorRetanqueo;
	}

	public String getCuotaRecoger() {
		return cuotaRecoger;
	}

	public void setCuotaRecoger(String cuotaRecoger) {
		this.cuotaRecoger = cuotaRecoger;
	}

	public String getObligaciones() {
		return obligaciones;
	}

	public void setObligaciones(String obligaciones) {
		this.obligaciones = obligaciones;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNroCaso() {
		return nroCaso;
	}

	public void setNroCaso(String nroCaso) {
		this.nroCaso = nroCaso;
	}

	public Execution getEjecucion() {
		return ejecucion;
	}

	public void setEjecucion(Execution ejecucion) {
		this.ejecucion = ejecucion;
	}

}
