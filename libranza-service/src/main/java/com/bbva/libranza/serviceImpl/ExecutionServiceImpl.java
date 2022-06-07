package com.bbva.libranza.serviceImpl;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import com.bbva.libranza.model.Case;
import com.bbva.libranza.model.Execution;
import com.bbva.libranza.repository.ICaseRepository;
import com.bbva.libranza.repository.IExecutionRepository;
import com.bbva.libranza.service.ExecutionService;
import com.google.gson.JsonObject;

@SpringBootApplication(scanBasePackages = { "com.bbva.libranza.repository" })
@Transactional
@Service
public class ExecutionServiceImpl implements ExecutionService {
	private static final Logger LOG = LogManager.getLogger(ExecutionServiceImpl.class);
	
	@Autowired
	private IExecutionRepository executionRepository;

	@Autowired
	private ICaseRepository caseRepository;

	public Execution addExecution(File file) {

		Execution ejecucion = new Execution();
		ejecucion.setFechaInicio(new Date().getTime());
		ejecucion.setEstado("PROCESANDO");
		ejecucion.setArchivo(file.getName());
		LOG.info("Agregando registro de ejecucion con Fecha inicio "+ ejecucion.getFechaInicio() + " para el Archivo "+ ejecucion.getArchivo());
		return executionRepository.save(ejecucion);
	}

	public Execution updateExecution(Execution execution) {		
		return executionRepository.save(execution);
	}
	public List<Case> getCasesByExecution(long executionId) {
		LOG.info("Consultando Casos para Ejecucion  " + executionId);
		return caseRepository.findByEjecucionId(executionId);
	}

	public Case updateCase(Case caso) throws Exception {
		LOG.info("Actualizando Caso : " + caso.getNroCaso() + "correspondiente a Ejecucion "+ caso.getEjecucion().getId());
		return caseRepository.save(caso);
	}

	public Case createCase(JsonObject caso, Execution execute, Integer lineNumber) throws Exception {
		Case cs = new Case();
		
		
		
		JsonObject contrato = caso.get("contrato").getAsJsonObject();
		cs.setJson(contrato.toString());
		
		cs.setFechaSolicitud(contrato.get("datosCliente").getAsJsonObject().get("fechaSolicitud").getAsString());
		cs.setTipoCliente(contrato.get("datosCliente").getAsJsonObject().get("tipoCliente").getAsString());
		cs.setTipoDocumento(contrato.get("datosCliente").getAsJsonObject().get("tipoDocumento").getAsString());
		cs.setIdentificacion(contrato.get("datosCliente").getAsJsonObject().get("identificacion").getAsString());
		cs.setNombreCliente(contrato.get("datosCliente").getAsJsonObject().get("nombreCliente").getAsString());
		cs.setCuentaAbonar(contrato.get("datosCliente").getAsJsonObject().get("cuentaAbonar").getAsString());
		cs.setCorreoCliente(contrato.get("datosCliente").getAsJsonObject().get("correoCliente").getAsString());
		cs.setCelularCliente(contrato.get("datosCliente").getAsJsonObject().get("celularCliente").getAsString());
		
		cs.setCodigo(contrato.get("datosCredito").getAsJsonObject().get("codigo").getAsString());
		cs.setConsecutivo(contrato.get("datosCredito").getAsJsonObject().get("consecutivo").getAsString());
		cs.setSubproducto(contrato.get("datosCredito").getAsJsonObject().get("subproducto").getAsString());
		cs.setConvenioLibranza(contrato.get("datosCredito").getAsJsonObject().get("convenioLibranza").getAsString());
		cs.setDestinoPrincipal(contrato.get("datosCredito").getAsJsonObject().get("destinoPrincipal").getAsString());
		cs.setScoringAprobado(contrato.get("datosCredito").getAsJsonObject().get("scoringAprobado").getAsString());
		cs.setDestinoPrincipal(contrato.get("datosCredito").getAsJsonObject().get("destinoPrincipal").getAsString());
		cs.setMontoAprobado(contrato.get("datosCredito").getAsJsonObject().get("montoAprobado").getAsString());
		cs.setPlazo(contrato.get("datosCredito").getAsJsonObject().get("plazo").getAsString());
		cs.setValorCuota(contrato.get("datosCredito").getAsJsonObject().get("valorCuota").getAsString());
		cs.setValorSeguro(contrato.get("datosCredito").getAsJsonObject().get("valorSeguro").getAsString());
		cs.setValorCuotaSeguro(contrato.get("datosCredito").getAsJsonObject().get("valorCuotaSeguro").getAsString());
		cs.setTasaEA(contrato.get("datosCredito").getAsJsonObject().get("tasaEA").getAsString());
		cs.setTipoConvenio(contrato.get("datosCredito").getAsJsonObject().get("tipoConvenio").getAsString());
		cs.setOficinaSegmentar(contrato.get("datosCredito").getAsJsonObject().get("oficinaSegmentar").getAsString());
		cs.setGarantia(contrato.get("datosCredito").getAsJsonObject().get("garantia").getAsString());
		cs.setCodigoAltamiraCliente(contrato.get("datosCredito").getAsJsonObject().get("codigoAltamiraCliente").getAsString());
		cs.setTechoGlobal(contrato.get("datosCredito").getAsJsonObject().get("techoGlobal").getAsString());
		cs.setBuro(contrato.get("datosCredito").getAsJsonObject().get("buro").getAsString());
		cs.setDictamen(contrato.get("datosCredito").getAsJsonObject().get("dictamen").getAsString());
		cs.setDireccionIP(contrato.get("datosCredito").getAsJsonObject().get("direccionIP").getAsString());
		cs.setDireccionIP(contrato.get("datosCredito").getAsJsonObject().get("tipoGestion").getAsString());
		cs.setDireccionIP(contrato.get("datosCredito").getAsJsonObject().get("grupoConvenio").getAsString());
		
		
		cs.setVentaAsistida(contrato.get("datosComerciales").getAsJsonObject().get("ventaAsistida").getAsString());
		cs.setCanalSolicitud(contrato.get("datosComerciales").getAsJsonObject().get("canalSolicitud").getAsString());
		cs.setCanalVentaSeleccionado(contrato.get("datosComerciales").getAsJsonObject().get("canalVentaSeleccionado").getAsString());
		cs.setCedulaCoordinador(contrato.get("datosComerciales").getAsJsonObject().get("cedulaCoordinador").getAsString());
		cs.setNombreCoordinador(contrato.get("datosComerciales").getAsJsonObject().get("nombreCoordinador").getAsString());
		cs.setRegional(contrato.get("datosComerciales").getAsJsonObject().get("regional").getAsString());
		cs.setCodigoAsesor(contrato.get("datosComerciales").getAsJsonObject().get("codigoAsesor").getAsString());
		cs.setIdentificacionAsesor(contrato.get("datosComerciales").getAsJsonObject().get("identificacionAsesor").getAsString());
		cs.setNombreAsesor(contrato.get("datosComerciales").getAsJsonObject().get("nombreAsesor").getAsString());
		cs.setOficinaAsesor(contrato.get("datosComerciales").getAsJsonObject().get("oficinaAsesor").getAsString());
		cs.setCiudadAsesor(contrato.get("datosComerciales").getAsJsonObject().get("ciudadAsesor").getAsString());
		cs.setTerritorialAsesor(contrato.get("datosComerciales").getAsJsonObject().get("territorialAsesor").getAsString());
		cs.setZonaRed(contrato.get("datosComerciales").getAsJsonObject().get("zonaRed").getAsString());

		cs.setContratoRetanquear(contrato.get("obligacionesRecoger").getAsJsonObject().get("contratoRetanquear").getAsString());
		cs.setValorRetanqueo(contrato.get("obligacionesRecoger").getAsJsonObject().get("valorRetanqueo").getAsString());
		cs.setCuotaRecoger(contrato.get("obligacionesRecoger").getAsJsonObject().get("cuotaRecoger").getAsString());
		cs.setObligaciones(contrato.get("obligacionesRecoger").getAsJsonObject().get("obligaciones").getAsJsonArray().toString());
		
		cs.setEstado(contrato.get("datosCredito").getAsJsonObject().get("dictamen").getAsString());
		cs.setEjecucion(execute);
		cs.setLine(lineNumber.toString());
		//cs.setDescripcion(contrato.get("descripcion").getAsString());
		LOG.info("Creando Caso con Consecutivo : "+cs.getFechaSolicitud()+ "correspondiente a Ejecucion : "+cs.getEjecucion().getId());
		return caseRepository.save(cs);
	}
	
	
	public Case createFailedCase(JsonObject caso, Execution execute, Integer lineNumber) throws Exception {
		Case cs = new Case();
		JsonObject contrato = caso.get("contrato").getAsJsonObject();
		cs.setDescripcion(contrato.get("descripcion").getAsJsonObject().get("valor").getAsString());
		cs.setEstado(contrato.get("estado").getAsJsonObject().get("valor").getAsString());
		cs.setEjecucion(execute);
		cs.setLine(lineNumber.toString());
		LOG.info("Creando Caso con Consecutivo : "+cs.getFechaSolicitud()+ "correspondiente a Ejecucion : "+cs.getEjecucion().getId());
		return caseRepository.save(cs);
	}
}