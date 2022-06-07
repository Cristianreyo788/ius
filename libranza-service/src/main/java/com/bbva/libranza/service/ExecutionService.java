package com.bbva.libranza.service;

import java.io.File;
import java.util.List;

import com.bbva.libranza.model.Case;
import com.bbva.libranza.model.Execution;
import com.google.gson.JsonObject;

public interface ExecutionService {	
	
	public Execution addExecution(File file);
	
	public Case createCase(JsonObject caso, Execution ejecuta, Integer lineNumber) throws Exception;
	
	public List<Case> getCasesByExecution(long executionId );
	
	public Case updateCase(Case caso ) throws Exception;
	
	public Case createFailedCase(JsonObject caso, Execution execute, Integer lineNumber) throws Exception ;
	
	public Execution updateExecution(Execution execution);
}