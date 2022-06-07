package com.bbva.libranza.service;

import com.bbva.libranza.model.ArchivoCargue;

public interface IProcessCase {
	public String executeProcessCreateCaseBonita() throws Exception;

	public Boolean executeUploadOnClickFile(ArchivoCargue archivoCargue) throws Exception;
}
