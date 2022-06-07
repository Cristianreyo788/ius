package com.bbva.libranza.service;

import java.io.File;
import java.util.List;

import com.bbva.libranza.model.Files;

public interface FilesService {
	public boolean isFileRegistred(File directory, File file);
	public boolean registerFile(File directory, File file);
	public Files getFiles(File directory, File file);
	public List<Files> findAllUnprocessedFiles();
	public boolean save(Files files);
	
}
