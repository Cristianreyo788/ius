package com.bbva.libranza.client;

import java.util.List;

import com.bbva.libranza.dto.bonita.BonitaUserDTO;
import com.bbva.libranza.dto.bonita.CustomUserInfoDefinitionDTO;
import com.bbva.libranza.dto.bonita.GroupDTO;
import com.bbva.libranza.dto.bonita.MembershipDTO;
import com.bbva.libranza.dto.bonita.RoleDTO;
import com.bbva.libranza.dto.bonita.processDefinitionDTO;

public interface BonitaAPIRestClient {
	public BonitaUserDTO createUser(BonitaUserDTO userBonita) throws Exception;

	public void updateUser(BonitaUserDTO userBonita) throws Exception;

	public BonitaUserDTO findUserByUserName(String username) throws Exception;

	public CustomUserInfoDefinitionDTO createCustomUserInfoDefinition(
			CustomUserInfoDefinitionDTO customUserInfoDefinition) throws Exception;

	public List<CustomUserInfoDefinitionDTO> getCustomUserInfoDefinitions() throws Exception;

	public void associateCustomUserInfo(String userId, String customUserInfoDefinitionId, String value)
			throws Exception;

	public List<GroupDTO> findGroupByName(String name) throws Exception;

	public List<RoleDTO> findRoleByName(String name) throws Exception;

	public List<MembershipDTO> findMembershipOfUser(String userId) throws Exception;

	public GroupDTO createGroup(GroupDTO group) throws Exception;

	public RoleDTO createRole(RoleDTO role) throws Exception;

	public MembershipDTO createMembership(MembershipDTO membership) throws Exception;

	public List<processDefinitionDTO> getProcessByName(String name) throws Exception;

	public String instanciateProcess(String jsonContract, String processDefinitionId) throws Exception;

}