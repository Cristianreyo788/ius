package com.bbva.libranza.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bbva.libranza.model.Files;
@Repository
public interface IFilesRepository extends CrudRepository<Files, Long>{
	Files findByFileName(String fileName);
}
