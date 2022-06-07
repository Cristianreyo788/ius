package com.bbva.libranza.service;

import java.util.List;

import com.bbva.libranza.model.ConvenioLibranza;

public interface ConvenioService {
		 	
	public List<ConvenioLibranza> findAllConvenio() throws Exception;

}


