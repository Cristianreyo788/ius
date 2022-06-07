package com.bbva.libranza.service;


import java.util.List;
import java.util.Map;

import com.bbva.libranza.dto.bonita.*;

public interface BonitaService {

    public BonitaUserDTO createUser(BonitaUserDTO bonitaUser) throws Exception;

    public void updateUser(BonitaUserDTO bonitaUser) throws Exception;

    public BonitaUserDTO findUserByUserName(String userName) throws Exception;

    public CustomUserInfoDefinitionDTO createCustomUserInfoDefinition(CustomUserInfoDefinitionDTO customUserInfoDefinition) throws Exception;

    public Map<String, CustomUserInfoDefinitionDTO> getCustomUserInfoDefinitionMap() throws Exception;

    public void associateCustomUserInfo(String userId, String customUserInfoDefinitionId, String value) throws Exception;

    public GroupDTO findGroupByName(String name) throws Exception;

    public RoleDTO findRoleByName(String name) throws Exception;

    public MembershipDTO findMembershipByUserGroupAndRole(String userId, String groupId, String roleId) throws Exception;

    public GroupDTO createGroup(GroupDTO group) throws Exception;

    public RoleDTO createRole(RoleDTO role) throws Exception;

    public MembershipDTO createMembership(MembershipDTO membership) throws Exception;
    
    public List<processDefinitionDTO> getProcessByName(String name) throws Exception;
    
    public String instanciateProcess(String jsonContract ,String processDefinitionId)throws Exception;
}
