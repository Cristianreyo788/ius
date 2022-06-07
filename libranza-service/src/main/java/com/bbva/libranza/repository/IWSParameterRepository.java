package com.bbva.libranza.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bbva.libranza.model.LibranzaParameter;

@Repository
public interface IWSParameterRepository extends CrudRepository<LibranzaParameter, Long> {

	LibranzaParameter findByKey(String key);
}
