package com.bbva.libranza.util;



import com.bbva.libranza.dto.bonita.BonitaUserDTO;

public class BonitaUtil {

    public static BonitaUserDTO buildBonitaUserFromExecutionUser() {

        BonitaUserDTO bonitaUser = new BonitaUserDTO();

        bonitaUser.setUserName("");

        bonitaUser.setPassword("");

        bonitaUser.setFirstname("");

        bonitaUser.setLastname("");

        bonitaUser.setEnabled(Boolean.toString(true));
        
        return bonitaUser;
    }

}
