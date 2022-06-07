package com.bbva.libranza.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bbva.libranza.model.ConvenioLibranza;

@Repository
public interface IConvenioRepository extends CrudRepository<ConvenioLibranza, Integer> {
	List<ConvenioLibranza> findById(long id);
	List<ConvenioLibranza> findByCod(String cod);
	ConvenioLibranza findByNumeroConvenio(Integer numeroConvenio);
	
	
}
