package com.bbva.libranza.task;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bbva.libranza.model.ConvenioLibranza;
import com.bbva.libranza.repository.IConvenioRepository;
import com.bbva.libranza.repository.IWSParameterRepository;
import com.bbva.libranza.service.IProcessCase;
@Component("ScheduledTasks")
public class ScheduledTasks {

	private static final Logger LOG = LogManager.getLogger(ScheduledTasks.class);
	@Autowired
	private IProcessCase executeBatchService;

	@Value("${com.bbva.libranza.node}")
	private String stfNode;

	@Value("${bonita.nodeNameParameter}")
	private String nodeNameParameter;

	@Value("${bonita.cronLibranza}")
	private String cronLibranza;
	
	@Autowired
	IWSParameterRepository wsParameterR;
	
	@Autowired
	IConvenioRepository convenioRepository;
	
	@Bean
	public String getCronValue()
	{
		try {
			return wsParameterR.findByKey(cronLibranza).getValue();
		}catch(Exception e){
			LOG.info("No se encuentra la propiedad Cron, se coloca por default");
			return "0 0/1 9-23 * * ?";
		}
	    
	}
	
	@Scheduled(cron = "#{@getCronValue}")
	public void executeProcessCurrentTime() throws Throwable {
		LOG.info("Ejecutando Tarea Programada");
		LOG.info("Esto trajo: "+wsParameterR.findByKey(nodeNameParameter).getValue());
		if(convenioRepository.findByCod("AUQ").size()==0) {
			ConvenioLibranza convenioLibranza=new ConvenioLibranza();
			convenioLibranza.setCodigoSubproducto(4124);
			convenioLibranza.setNumeroConvenio(8);
			convenioLibranza.setCodigoConvenio("AUQ01");
			convenioLibranza.setCod("AUQ");
			convenioLibranza.setNombreConvenio("CORPORACION AUTONOMA REGIONAL DEL QUINDÍO");
			convenioLibranza.setTipoGestion("Riesgos");
			convenioLibranza.setGrupoConvenio("Grupo 1-Libranza Digital");
			convenioRepository.save(convenioLibranza);
			ConvenioLibranza convenioLibranza2=new ConvenioLibranza();
			convenioLibranza2.setCodigoSubproducto(4124);
			convenioLibranza2.setNumeroConvenio(9);
			convenioLibranza2.setCodigoConvenio("AUQ01");
			convenioLibranza2.setCod("AUQ");
			convenioLibranza2.setNombreConvenio("CORPORACION AUTONOMA REGIONAL DEL QUINDÍO");
			convenioLibranza2.setTipoGestion("Operaciones");
			convenioLibranza2.setGrupoConvenio("Grupo 2-Libranza Fisica");
			convenioRepository.save(convenioLibranza2);
		}
		
		
		
		if (wsParameterR.findByKey(nodeNameParameter).getValue().equals(stfNode)) {
			executeBatchService.executeProcessCreateCaseBonita();
		} else {
			LOG.info("No esta habilitado la Ejecucion del Proceso para este nodo: " + stfNode);
		}
	}

}
