package com.ipartek.servicioREST;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import org.springframework.boot.context.properties.PropertyMapper.Source.Always.Mapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipartek.pojo.Dificultad;
import com.ipartek.pojo.MsgError;

@Service
public class DificultadServicioImp implements DificultadServicio{

	private RestTemplate restTemp = new RestTemplate();
	private final String URL = "http://localhost:9090/api/v1/dificultad/";
	private String mensajeParaLanzar;
	private ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public List<Dificultad> obtenerTodasDificultades(String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
			headers.setBearerAuth(token);
			HttpEntity<String> entity = new HttpEntity<>(headers);
			ResponseEntity<Dificultad[]> response = restTemp.exchange(URL, HttpMethod.GET, entity, Dificultad[].class);
			return Arrays.asList(response.getBody());
		} catch (HttpClientErrorException e) {
			try {
				MsgError miError = mapper.readValue(e.getResponseBodyAsString(), MsgError.class);
			} catch (Exception jsonError) {
				mensajeParaLanzar = "Error de formato";
			}
		}
		if(mensajeParaLanzar != null) {
			throw new RuntimeException(mensajeParaLanzar);
		}
		return new ArrayList<Dificultad>();
	}
	
	@Override
	public Dificultad insertarDificultad(String token, Dificultad difi) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(token);
			HttpEntity<Dificultad> entity = new HttpEntity<>(difi, headers);
			ResponseEntity<Dificultad> response = restTemp.exchange(URL, HttpMethod.POST, entity, Dificultad.class);
			return response.getBody();
		} catch (HttpClientErrorException e) {
			try {
				MsgError miError = mapper.readValue(e.getResponseBodyAsString(), MsgError.class);
				mensajeParaLanzar = miError.getMensaje();
			} catch (Exception jsonError) {
				mensajeParaLanzar = "Error de formato";
			}
		}
		if (mensajeParaLanzar != null) {
			throw new RuntimeException(mensajeParaLanzar);
		}
		return new Dificultad();
	}
	
	@Override
	public Dificultad obtenerDificultadPorId(String token, Integer id) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(token);
			HttpEntity<String> entity = new HttpEntity<>(headers);
			ResponseEntity<Dificultad> response = restTemp.exchange(
					URL + id,
					HttpMethod.GET,
					entity,
					Dificultad.class
					);
			return response.getBody();
					
		} catch (HttpClientErrorException e) {
			try {
				MsgError miError = mapper.readValue(e.getResponseBodyAsString(), MsgError.class);
				mensajeParaLanzar = miError.getMensaje();
			} catch (Exception jsonError) {
				mensajeParaLanzar = "Error de formato";
			}
		}
		if (mensajeParaLanzar != null) {
			throw new RuntimeException(mensajeParaLanzar);
		}
		return new Dificultad();
	}
	
	@Override
	public Dificultad modificarDificultad(String token, Dificultad difi) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(token);
			HttpEntity<Dificultad> entity = new HttpEntity<>(difi, headers);
			ResponseEntity<Dificultad> response = restTemp.exchange(
					URL,
					HttpMethod.PUT,
					entity,
					Dificultad.class
					);
			return response.getBody();
		} catch (HttpClientErrorException e) {
			try {
				MsgError miError = mapper.readValue(e.getResponseBodyAsString(), MsgError.class);
				mensajeParaLanzar = miError.getMensaje();
			} catch (Exception jsonError) {
				mensajeParaLanzar = "Error de formato";
			}
		}
		if (mensajeParaLanzar != null) {
			throw new RuntimeException(mensajeParaLanzar);
		}
		return new Dificultad();
	}
	
	@Override
	public Boolean borrarDificultad(String token, Integer id) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(token);
			HttpEntity<String> entity = new HttpEntity<>(headers);
			ResponseEntity<Boolean> response = restTemp.exchange(
					URL + id,
					HttpMethod.DELETE,
					entity,
					Boolean.class
					);
			return response.getBody();
		} catch (HttpClientErrorException e) {
			try {
				MsgError miError = mapper.readValue(e.getResponseBodyAsString(), MsgError.class);
				mensajeParaLanzar = miError.getMensaje();
			} catch (Exception jsonError) {
				mensajeParaLanzar = "Error de formato";
			}
		}
		if (mensajeParaLanzar != null) {
			throw new RuntimeException(mensajeParaLanzar);
		}
		return false;
	}
	


}
