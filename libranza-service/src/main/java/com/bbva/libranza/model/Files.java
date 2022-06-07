package com.bbva.libranza.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "LIBRANZA_FILES")
public class Files{
	@Id
	@SequenceGenerator(name = "libranza_files_seq", sequenceName = "LIBRANZA_FILES_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "libranza_files_seq")
	private Long id;
	
	
	@Column(name = "FILE_NAME", unique = true)
	@NotNull
	@Size(max = 100)
	private String fileName;

	
	@Column(name = "FILE_PATH")
	@NotNull
	@Size(max = 100)
	private String filePath;
	
	
	@Column(name = "STATUS")
	@NotNull
	private Boolean status;
	
	
	@Column(name = "EXECUTION_DATE")
	@CreatedDate
	private Date executionDate;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Date getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(Date executionDate) {
		this.executionDate = executionDate;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}	
}
