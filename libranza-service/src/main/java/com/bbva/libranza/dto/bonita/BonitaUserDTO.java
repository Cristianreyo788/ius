package com.bbva.libranza.dto.bonita;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BonitaUserDTO implements Serializable
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;

	private String userName;
	
	private String password;
	
	private String password_confirm;
	
	private String icon;
	
	private String firstname;
	
	private String lastname;
	
	private String title;
	
	private String job_title;
	
	private String manager_id;
	
	private String creation_date;
	
	private String created_by_user_id;
	
	private String last_connection;
	
	private String last_update_date;
	
	private String enabled;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getPassword_confirm()
	{
		return password_confirm;
	}

	public void setPassword_confirm(String password_confirm)
	{
		this.password_confirm = password_confirm;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public String getFirstname()
	{
		return firstname;
	}

	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	public String getLastname()
	{
		return lastname;
	}

	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getJob_title()
	{
		return job_title;
	}

	public void setJob_title(String job_title)
	{
		this.job_title = job_title;
	}

	public String getManager_id()
	{
		return manager_id;
	}

	public void setManager_id(String manager_id)
	{
		this.manager_id = manager_id;
	}

	public String getCreation_date()
	{
		return creation_date;
	}

	public void setCreation_date(String creation_date)
	{
		this.creation_date = creation_date;
	}

	public String getCreated_by_user_id()
	{
		return created_by_user_id;
	}

	public void setCreated_by_user_id(String created_by_user_id)
	{
		this.created_by_user_id = created_by_user_id;
	}

	public String getLast_connection()
	{
		return last_connection;
	}

	public void setLast_connection(String last_connection)
	{
		this.last_connection = last_connection;
	}

	public String getLast_update_date()
	{
		return last_update_date;
	}

	public void setLast_update_date(String last_update_date)
	{
		this.last_update_date = last_update_date;
	}

	public String getEnabled()
	{
		return enabled;
	}

	public void setEnabled(String enabled)
	{
		this.enabled = enabled;
	}
}