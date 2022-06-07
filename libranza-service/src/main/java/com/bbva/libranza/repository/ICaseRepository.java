package com.bbva.libranza.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bbva.libranza.model.Case;

@Repository
public interface ICaseRepository extends CrudRepository<Case, Long> {
	List<Case> findByEjecucionId(long ejecucionId);    
}
