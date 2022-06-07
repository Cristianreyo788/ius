package com.bbva.libranza.api;


import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bbva.libranza.service.IProcessCase;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class ProcessBonitaController {

	private static final Logger LOG = LogManager.getLogger(ProcessBonitaController.class);
	@Autowired
	private IProcessCase executeBatchService;

	@GetMapping(value = "/crearCasosLibranza")
	@ResponseBody
	public String executeProcess(HttpServletResponse response) throws Throwable {
		LOG.info("Ejecutando Procesos Libranzas");
		return executeBatchService.executeProcessCreateCaseBonita();
	}

}