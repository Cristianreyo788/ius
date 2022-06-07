package com.bbva.libranza.dto.bonita;

import java.io.Serializable;

public class GroupDTO implements Serializable{

    private static final long serialVersionUID = 6774091983070342322L;

	private String id;

    private String name;

    private String displayName;

    private String parent_path;

    private String path;

    private String description;

    private String creation_date;

    private String created_by_user_id;

    private String last_update_date;

    private String icon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getParent_path() {
        return parent_path;
    }

    public void setParent_path(String parent_path) {
        this.parent_path = parent_path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getCreated_by_user_id() {
        return created_by_user_id;
    }

    public void setCreated_by_user_id(String created_by_user_id) {
        this.created_by_user_id = created_by_user_id;
    }

    public String getLast_update_date() {
        return last_update_date;
    }

    public void setLast_update_date(String last_update_date) {
        this.last_update_date = last_update_date;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
