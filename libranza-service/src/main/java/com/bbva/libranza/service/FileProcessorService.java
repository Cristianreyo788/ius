package com.bbva.libranza.service;

import com.bbva.libranza.model.Execution;

public interface FileProcessorService {
	public Execution updateExecutionBash(String file) throws Exception;
	
	public StringBuffer getTxtToXLSMLine(String columnas[], int nLinea) throws Exception;
}
