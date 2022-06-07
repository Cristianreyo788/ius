package com.bbva.libranza.dto.bonita;

import java.io.Serializable;

public class MembershipDTO implements Serializable{

    private static final long serialVersionUID = -9057971454171534022L;

	private String role_id;

    private String group_id;

    private String user_id;

    private String assigned_date;

    private String assigned_by_user_id;

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAssigned_date() {
        return assigned_date;
    }

    public void setAssigned_date(String assigned_date) {
        this.assigned_date = assigned_date;
    }

    public String getAssigned_by_user_id() {
        return assigned_by_user_id;
    }

    public void setAssigned_by_user_id(String assigned_by_user_id) {
        this.assigned_by_user_id = assigned_by_user_id;
    }
}
