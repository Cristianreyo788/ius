package com.bbva.libranza.serviceImpl;

import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bbva.libranza.exception.FileCaseException;
import com.bbva.libranza.model.FileCase;
import com.bbva.libranza.repository.IFileCaseRepository;

@Repository
public class FileCaseImpl implements IFileCaseRepository {

private static final Logger LOG = LogManager.getLogger(FileCaseImpl.class);
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<FileCase> findAllFileCase() throws FileCaseException {
		try {	
			return em.createQuery("from FileCase").getResultList();
		}
		catch(Exception ex)
	    {
	      LOG.error("Error en findAllFileCase:  "+  FileCaseImpl.class.getName()+ " " + ex.getMessage());                
	      throw new FileCaseException(ex);
	    }
	}

	@Transactional(readOnly=true)
	@Override
	public FileCase findWithFileName(String fileName) throws FileCaseException {
		try {
			return (FileCase) em.createQuery(
					"from FileCase where fileName = :fileName and enabled = 1")
					.setParameter("fileName",fileName)
					.getSingleResult();
		}
		catch(Exception ex)
	    {
	      LOG.error("Error en findWithFileName :  "+  FileCaseImpl.class.getName()+ " " + ex.getMessage());                
	      throw new FileCaseException(ex);
	    }
	}

	@Override
	public void saveFileCase(FileCase fileCase) throws FileCaseException {
		try {	
			if(fileCase.getId() != null && fileCase.getId() > 0) {
				em.merge(fileCase);
			} else {
				em.persist(fileCase);
			}		
		}
		catch (EntityExistsException ex) {
			LOG.info("Error en saveFileCase :  "+  FileCaseImpl.class.getName()+" No es posible agregar el Registro de Reporte" );
			throw new FileCaseException("Duplicidad en el Registro: "
					+ fileCase.getId());
		}
		catch(Exception ex)
        {
			LOG.error("Error en saveFileCase:  "+  FileCaseImpl.class.getName()+ " " + ex.getMessage());                
          throw new FileCaseException(ex);
        }
	}

	@Transactional(readOnly=true)
	@Override
	public FileCase findOneFileCase(Long id) throws FileCaseException {
		try {			
			return em.find(FileCase.class, id);
		}
		catch(Exception ex)
	    {
	      LOG.error("Error en findOneFileCase :  "+  FileCaseImpl.class.getName()+ " " + ex.getMessage());                
	      throw new FileCaseException(ex);
	    }
	}
}
