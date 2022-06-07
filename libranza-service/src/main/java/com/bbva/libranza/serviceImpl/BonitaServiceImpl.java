package com.bbva.libranza.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbva.libranza.client.BonitaAPIRestClient;
import com.bbva.libranza.dto.bonita.*;
import com.bbva.libranza.service.BonitaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BonitaServiceImpl implements BonitaService {

	@Autowired
	BonitaAPIRestClient bonitaAPIRestClient;

	@Override
	public BonitaUserDTO createUser(BonitaUserDTO bonitaUser) throws Exception {
		return bonitaAPIRestClient.createUser(bonitaUser);
	}

	@Override
	public void updateUser(BonitaUserDTO bonitaUser) throws Exception {
		bonitaUser.setIcon(null);
		bonitaAPIRestClient.updateUser(bonitaUser);
	}

	@Override
	public BonitaUserDTO findUserByUserName(String userName) throws Exception {
		return bonitaAPIRestClient.findUserByUserName(userName);
	}

	@Override
	public CustomUserInfoDefinitionDTO createCustomUserInfoDefinition(
			CustomUserInfoDefinitionDTO customUserInfoDefinition) throws Exception {
		return bonitaAPIRestClient.createCustomUserInfoDefinition(customUserInfoDefinition);
	}

	@Override
	public List<processDefinitionDTO> getProcessByName(String name) throws Exception {

		return bonitaAPIRestClient.getProcessByName(name);

	}

	@Override
	public Map<String, CustomUserInfoDefinitionDTO> getCustomUserInfoDefinitionMap() throws Exception {
		Map<String, CustomUserInfoDefinitionDTO> customUserInfoDefinitionMap = new HashMap<String, CustomUserInfoDefinitionDTO>();

		List<CustomUserInfoDefinitionDTO> customUserInfoDefinitionList = bonitaAPIRestClient
				.getCustomUserInfoDefinitions();

		if (customUserInfoDefinitionList != null) {

			for (CustomUserInfoDefinitionDTO customInfoDefinition : customUserInfoDefinitionList) {
				customUserInfoDefinitionMap.put(customInfoDefinition.getName(), customInfoDefinition);
			}
		}

		return customUserInfoDefinitionMap;
	}

	@Override
	public void associateCustomUserInfo(String userId, String customUserInfoDefinitionId, String value)
			throws Exception {
		bonitaAPIRestClient.associateCustomUserInfo(userId, customUserInfoDefinitionId, value);
	}

	@Override
	public GroupDTO findGroupByName(String name) throws Exception {
		List<GroupDTO> groups = bonitaAPIRestClient.findGroupByName(name);

		if (groups != null && !groups.isEmpty()) {
			return groups.get(0);
		}

		return null;
	}

	@Override
	public RoleDTO findRoleByName(String name) throws Exception {
		List<RoleDTO> roles = bonitaAPIRestClient.findRoleByName(name);

		if (roles != null && !roles.isEmpty()) {
			return roles.get(0);
		}

		return null;
	}

	@Override
	public MembershipDTO findMembershipByUserGroupAndRole(String userId, String groupId, String roleId)
			throws Exception {
		List<MembershipDTO> memberships = bonitaAPIRestClient.findMembershipOfUser(userId);

		if (memberships != null && !memberships.isEmpty()) {
			for (MembershipDTO membership : memberships) {
				if (membership.getGroup_id().equals(groupId) && membership.getRole_id().equals(roleId)) {
					return membership;
				}
			}
		}

		return null;
	}

	@Override
	public GroupDTO createGroup(GroupDTO group) throws Exception {
		return bonitaAPIRestClient.createGroup(group);
	}

	@Override
	public RoleDTO createRole(RoleDTO role) throws Exception {
		return bonitaAPIRestClient.createRole(role);
	}

	@Override
	public MembershipDTO createMembership(MembershipDTO membership) throws Exception {
		return bonitaAPIRestClient.createMembership(membership);
	}
	
	@Override 
	 public String instanciateProcess(String jsonContract ,String processDefinitionId) throws Exception{
		return bonitaAPIRestClient.instanciateProcess(jsonContract, processDefinitionId); 
	}
}