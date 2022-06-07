package com.bbva.libranza.serviceImpl;

import java.io.File;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bbva.libranza.model.Files;
import com.bbva.libranza.repository.IFilesRepository;
import com.bbva.libranza.service.FilesService;

@SpringBootApplication(scanBasePackages = { "com.bbva.libranza.repository" })

@Service
public class FilesServiceImpl implements FilesService {
	
	private static final Logger LOG = LogManager.getLogger(FilesServiceImpl.class);
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private IFilesRepository filesRepository;
	
	@Transactional(readOnly=true)
	@Override
	public boolean isFileRegistred(File directory, File file) {
		Files myFiles = new Files();
		try {
			myFiles = filesRepository.findByFileName(file.getName());
		} catch (Exception e) {
			LOG.error("Error en isFileRegistred:  " + FilesServiceImpl.class.getName() + " " + e.getMessage());
			// TODO Agregar exception si es necesario.
		}
		return !(myFiles == null);
	}
	
	
	
	public boolean registerFile(File directory, File file) {
		Files myFile = new Files();
		myFile.setFilePath(directory.getPath());
		myFile.setFileName(file.getName());
		myFile.setStatus(false);
		LOG.info("Agregando registro para el Archivo "+ myFile.getFileName());
		try {	
			filesRepository.save(myFile);
		}
		catch(Exception ex)
        {
			LOG.error("Error en registerFile:  "+  FileCaseImpl.class.getName()+ " " + ex.getMessage());
			return false;
    	}		
		return true;
	}

	@Override
	public Files getFiles(File directory, File file) {
		Files myFiles = new Files();
		try {
			myFiles = (Files) em.createQuery(
					"from Files where filePath = :filePath and fileName = :fileName")
					.setParameter("filePath", directory.getPath())
					.setParameter("fileName", file.getName())
					.getSingleResult();
		} catch (Exception e) {
			LOG.error("Error en getFiles:  " + FilesServiceImpl.class.getName() + " " + e.getMessage());
			return null;
		}
		return myFiles;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Files> findAllUnprocessedFiles() {
		try {
			return em.createQuery(
					"from Files where status = false")
					.getResultList();
		} catch (Exception e) {
			LOG.error("Error en findAllUnprocessedFiles:  " + FilesServiceImpl.class.getName() + " " + e.getMessage());
			// TODO Lanzar exception si es necesario
		}
		return null;
	}


	@Transactional
	@Override
	public boolean save(Files files) {
		try {	
			if(files.getId() != null && files.getId() > 0) {
				em.merge(files);
			} else {
				em.persist(files);
			}	
		}catch(Exception ex)
        {
			LOG.error("Error en saveFileCase:  "+  FileCaseImpl.class.getName()+ " " + ex.getMessage());                
			//TODO validar si es bueno lanzar una exception
			return false;
        }
		return true;
	}
}
