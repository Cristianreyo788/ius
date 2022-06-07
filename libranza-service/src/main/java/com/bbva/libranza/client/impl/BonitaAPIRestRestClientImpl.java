package com.bbva.libranza.client.impl;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.bbva.libranza.client.BonitaAPIRestClient;
import com.bbva.libranza.dto.bonita.BonitaUserDTO;
import com.bbva.libranza.dto.bonita.CustomUserInfoDefinitionDTO;
import com.bbva.libranza.dto.bonita.GroupDTO;
import com.bbva.libranza.dto.bonita.MembershipDTO;
import com.bbva.libranza.dto.bonita.RoleDTO;
import com.bbva.libranza.dto.bonita.processDefinitionDTO;
import com.bbva.libranza.util.RestUtil;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Component
public class BonitaAPIRestRestClientImpl implements BonitaAPIRestClient {

	private static final Logger LOG = LogManager.getLogger(BonitaAPIRestRestClientImpl.class);
	private static final String X_BONITA_API_TOKEN = "X-Bonita-API-Token";

	@Value("${bonita.base.url}")
	private String bonitaBaseURL;

	@Value("${bonita.username}")
	private String bonitaUserName;

	@Value("${bonita.password}")
	private String bonitaPassword;

	@Value("${bonita.process}")
	private String bonitaProcess;

	@Value("${bonita.version}")
	private String bonitaVersion;

	@Override
	public List<processDefinitionDTO> getProcessByName(String name) {
		try {
			String jsonResponse = makeRequest("/API/bpm/process?p=0&c=10&o=displayName ASC&f=name=" + name,
					HttpMethod.GET, null);

			ObjectMapper mapper = new ObjectMapper();

			CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class,
					processDefinitionDTO.class);

			return mapper.readValue(jsonResponse, collectionType);

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(
					"Se ha presentado un error en getProcessByName con descripcion: " + e.getMessage(), e);
		}
	}

	@Override
	public BonitaUserDTO createUser(BonitaUserDTO bonitaUser) throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		mapper.setSerializationInclusion(Include.NON_NULL);
		String jsonBonitaUser = mapper.writeValueAsString(bonitaUser);
		String jsonResponse = makeRequest("/API/identity/user", HttpMethod.POST, jsonBonitaUser);

		return mapper.readValue(jsonResponse, BonitaUserDTO.class);
	}

	@Override
	public void updateUser(BonitaUserDTO bonitaUser) throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		mapper.setSerializationInclusion(Include.NON_NULL);
		String jsonBonitaUser = mapper.writeValueAsString(bonitaUser);
		makeRequest("/API/identity/user/" + bonitaUser.getId(), HttpMethod.PUT, jsonBonitaUser);
	}

	@Override
	public String instanciateProcess(String jsonContract, String processDefinitionId) throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		LOG.info("Definicion de Proceso  " + processDefinitionId + " a aplicar al Contrato " + jsonContract);

		mapper.setSerializationInclusion(Include.NON_NULL);

		String jsonResponse = makeRequest("/API/bpm/process/" + processDefinitionId + "/instantiation", HttpMethod.POST,
				jsonContract);

		JsonParser jsonParser = new JsonParser();
		JsonObject gsonResponse = (JsonObject) jsonParser.parse(jsonResponse);
		LOG.info("Invocacion API Bonita para Definicion de Proceso " + processDefinitionId + " con Respuesta "
				+ jsonParser.parse(jsonResponse));

		return gsonResponse.get("caseId").getAsString();
	}

	@Override
	public BonitaUserDTO findUserByUserName(String username) throws Exception {
		String jsonResponse = makeRequest("/API/identity/user?p=0&c=1&o=userName&s=" + username, HttpMethod.GET, null);
		try {
			jsonResponse = jsonResponse.replace("[", "").replace("]", "");

			if (jsonResponse == null || jsonResponse.isEmpty()) {
				LOG.error("Error en findUserByUserName - Usuario " + username + "No Encontrado");
				return null;
			}

		} catch (HttpStatusCodeException e) {
			if (e.getStatusCode().value() == 403) {
				LOG.error("Se ha presentado un error en  findUserByUserName. Accion No Permitida - Codigo error "
						+ e.getStatusCode().value() + " con Descripcion" + e.getMessage(), e);
			} else {
				LOG.error("Se ha presentado un error en  findUserByUserName con descripcion: " + e.getMessage(), e);
				throw new RuntimeException(
						"Se ha presentado un error en  findUserByUserName con descripcion: " + e.getMessage(), e);
			}
		} catch (Exception e) {
			LOG.error("Se ha presentado un error en  findUserByUserName con descripcion: " + e.getMessage(), e);
			throw new RuntimeException(
					"Se ha presentado un error en  findUserByUserName con descripcion: " + e.getMessage(), e);
		}
		return new ObjectMapper().readValue(jsonResponse, BonitaUserDTO.class);
	}

	@Override
	public CustomUserInfoDefinitionDTO createCustomUserInfoDefinition(
			CustomUserInfoDefinitionDTO customUserInfoDefinition) throws Exception {

		ObjectMapper mapper = new ObjectMapper();

		mapper.setSerializationInclusion(Include.NON_NULL);

		String jsonCustomUserInfoDefinition = mapper.writeValueAsString(customUserInfoDefinition);

		String jsonResponse = makeRequest("/API/customuserinfo/definition", HttpMethod.POST,
				jsonCustomUserInfoDefinition);

		return mapper.readValue(jsonResponse, CustomUserInfoDefinitionDTO.class);
	}

	@Override
	public List<CustomUserInfoDefinitionDTO> getCustomUserInfoDefinitions() throws Exception {

		String jsonResponse = makeRequest("/API/customuserinfo/definition?p=0&c=999999999", HttpMethod.GET, null);

		ObjectMapper mapper = new ObjectMapper();

		CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class,
				CustomUserInfoDefinitionDTO.class);

		return mapper.readValue(jsonResponse, collectionType);
	}

	@Override
	public void associateCustomUserInfo(String userId, String customUserInfoDefinitionId, String value)
			throws Exception {

		Map<String, String> bodyMap = new HashMap<>();

		bodyMap.put("value", value);

		String jsonBody = new ObjectMapper().writeValueAsString(bodyMap);

		makeRequest("/API/customuserinfo/value/" + userId + "/" + customUserInfoDefinitionId, HttpMethod.PUT, jsonBody);
	}

	@Override
	public List<GroupDTO> findGroupByName(String name) throws Exception {

		ObjectMapper mapper = new ObjectMapper();

		CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, GroupDTO.class);

		String jsonResponse = makeRequest("/API/identity/group?p=0&c=999999999&f=name=" + name, HttpMethod.GET, null);

		return new ObjectMapper().readValue(jsonResponse, collectionType);
	}

	@Override
	public List<RoleDTO> findRoleByName(String name) throws Exception {

		ObjectMapper mapper = new ObjectMapper();

		CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, RoleDTO.class);

		String jsonResponse = makeRequest("/API/identity/role?p=0&c=999999999&f=name=" + name, HttpMethod.GET, null);

		return new ObjectMapper().readValue(jsonResponse, collectionType);
	}

	@Override
	public List<MembershipDTO> findMembershipOfUser(String userId) throws Exception {

		String uri = "/API/identity/membership?p=0&c=999999999&f=user_id=" + userId;

		ObjectMapper mapper = new ObjectMapper();

		CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class,
				MembershipDTO.class);

		String jsonResponse = makeRequest(uri.toString(), HttpMethod.GET, null);

		return new ObjectMapper().readValue(jsonResponse, collectionType);
	}

	@Override
	public GroupDTO createGroup(GroupDTO group) throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		mapper.setSerializationInclusion(Include.NON_NULL);

		String jsonGroup = mapper.writeValueAsString(group);

		String jsonResponse = makeRequest("/API/identity/group", HttpMethod.POST, jsonGroup);

		return mapper.readValue(jsonResponse, GroupDTO.class);
	}

	@Override
	public RoleDTO createRole(RoleDTO role) throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		mapper.setSerializationInclusion(Include.NON_NULL);

		String jsonRole = mapper.writeValueAsString(role);

		String jsonResponse = makeRequest("/API/identity/role", HttpMethod.POST, jsonRole);

		return mapper.readValue(jsonResponse, RoleDTO.class);
	}

	@Override
	public MembershipDTO createMembership(MembershipDTO membership) throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		mapper.setSerializationInclusion(Include.NON_NULL);

		String jsonMembership = mapper.writeValueAsString(membership);

		String jsonResponse = makeRequest("/API/identity/membership", HttpMethod.POST, jsonMembership);

		return mapper.readValue(jsonResponse, MembershipDTO.class);
	}

	private String makeRequest(String endpointURL, HttpMethod method, String body) throws Exception {

		List<String> loginCookies = login();

		URI url = new URI(bonitaBaseURL + endpointURL.replace(" ", "%20"));

		RestTemplate restTemplate = RestUtil.buildRestTemplate(bonitaBaseURL.toLowerCase().contains("https"));
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

		HttpHeaders headers = new HttpHeaders();
		headers.put("Cookie", loginCookies);
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

		String bonitaToken = getBonitaTokenFromCookies(loginCookies);

		if (bonitaToken != null && !bonitaToken.isEmpty()) {
			headers.set(X_BONITA_API_TOKEN, bonitaToken);
		}

		HttpEntity<String> entity;

		if (body != null) {
			entity = new HttpEntity<String>(body, headers);
		} else {
			entity = new HttpEntity<String>(headers);
		}

		ResponseEntity<String> response = restTemplate.exchange(url, method, entity, String.class);

		if (response != null && response.getBody() != null) {
			return response.getBody();
		}

		return null;
	}

	private List<String> login() throws Exception {
		RestTemplate restTemplate = RestUtil.buildRestTemplate(bonitaBaseURL.contains("https"));

		HttpHeaders headers = new HttpHeaders();

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("username", bonitaUserName);
		map.add("password", bonitaPassword);
		map.add("redirect", "false");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(bonitaBaseURL + "/loginservice", request,
				String.class);

		List<String> cookies = response.getHeaders().get("Set-Cookie");

		return cookies;
	}

	private String getBonitaTokenFromCookies(List<String> cookies) {
		if (cookies != null) {
			for (String cookie : cookies) {
				String[] splittedCookie = cookie.split("=");
				if (splittedCookie[0].equals(X_BONITA_API_TOKEN)) {
					return splittedCookie[1].substring(0, splittedCookie[1].indexOf(";"));
				}
			}
		}

		return null;
	}

}