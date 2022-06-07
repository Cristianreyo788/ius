package com.bbva.libranza.repository;

import java.util.List;

import com.bbva.libranza.exception.FileCaseException;
import com.bbva.libranza.model.FileCase;

public interface IFileCaseRepository {
	
	public List<FileCase> findAllFileCase() throws FileCaseException ;
	
	public FileCase findWithFileName(String fileName) throws FileCaseException;
	
	public void saveFileCase(FileCase fileCase) throws FileCaseException;
	
	public FileCase findOneFileCase(Long id) throws FileCaseException;
}
