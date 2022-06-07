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
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "LIBRANZA_FILE_CASE")
public class FileCase {
	@Id
	@SequenceGenerator(name = "libranza_filecase_seq", sequenceName = "LIBRANZA_FILECASE_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "libranza_filecase_seq")
	private Long id;
	
	@Column(name = "FILE_NAME", unique = true)
	@NotNull
	@Size(max = 100)
	private String fileName;
	
	/*@OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "filecase")
	Execution execution;*/
	
	@Column(name = "MIGRATED")
	private Boolean migrated;
	
	@Column(name = "ENABLED")
	private Boolean enabled;
	
	@Column(name = "CREATED_AT")
	@CreatedDate
	private Date createdAt;
	
	@Column(name = "UPDATED_AT")
	@LastModifiedDate
	private Date updatedAt;

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

	/*public Execution getExecution() {
		return execution;
	}

	public void setExecution(Execution execution) {
		this.execution = execution;
	}*/

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Boolean getMigrated() {
		return migrated;
	}

	public void setMigrated(Boolean migrated) {
		this.migrated = migrated;
	}	
}
