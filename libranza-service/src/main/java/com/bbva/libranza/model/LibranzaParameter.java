package com.bbva.libranza.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "WS_PARAMETER")
public class LibranzaParameter implements Serializable {

	private static final long serialVersionUID = 4542578713512446475L;
	
	@Id
	@Column(name = "PARAM_KEY")
	private String key;
	
	@Column(name = "PARAM_VALUE")
	private String value;

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}