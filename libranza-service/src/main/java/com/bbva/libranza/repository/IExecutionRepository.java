package com.bbva.libranza.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bbva.libranza.model.Execution;

@Repository
public interface IExecutionRepository extends CrudRepository<Execution, Long> {
	Execution findByCasos(Long caseId);

}
