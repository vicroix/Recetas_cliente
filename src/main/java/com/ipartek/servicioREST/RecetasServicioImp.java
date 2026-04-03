package com.ipartek.servicioREST;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipartek.pojo.MsgError;
import com.ipartek.pojo.Receta;

@Service
public class RecetasServicioImp implements RecetasServicio{

	private RestTemplate restTemp = new RestTemplate();
	private final String URL = "http://localhost:9090/api/v1/receta/";
	private String mensajeParaLanzar;
	private ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public List<Receta> obtenerTodasRecetas(String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(token);
			HttpEntity<String> entity = new HttpEntity<>(headers);
			ResponseEntity<Receta[]> response = restTemp.exchange(URL, HttpMethod.GET, entity, Receta[].class);
			return Arrays.asList(response.getBody());
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
		return new ArrayList<Receta>();
	}

	@Override
	public List<Receta> obtenerRecetaPorNombre(String token, String nombre) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(token);
			HttpEntity<String> entity = new HttpEntity<>(headers);
			ResponseEntity<Receta[]> response = restTemp.exchange(URL  + "buscar-receta-nombre/" + nombre, HttpMethod.GET, entity, Receta[].class);
			return Arrays.asList(response.getBody());
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
		return new ArrayList<Receta>();
	}
	
	
	@Override
	public List<Receta> obtenerRecetaPorDificultad(String token, String nombre) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(token);
			HttpEntity<String> entity = new HttpEntity<>(headers);
			ResponseEntity<Receta[]> response = restTemp.exchange(URL  + "buscar-receta-dificultad/" + nombre, HttpMethod.GET, entity, Receta[].class);
			return Arrays.asList(response.getBody());
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
		return new ArrayList<Receta>();
	}
	
	
	@Override
	public Receta insertarReceta(String token, Receta rece) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(token);
			HttpEntity<Receta> entity = new HttpEntity<>(rece, headers);
			ResponseEntity<Receta> response = restTemp.exchange(URL, HttpMethod.POST, entity, Receta.class);
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
		return new Receta();
	}

	@Override
	public Receta obtenerRecetaPorId(String token, Integer id) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(token);
			HttpEntity<String> entity = new HttpEntity<>(headers);
			ResponseEntity<Receta> response = restTemp.exchange(
					URL + id,
					HttpMethod.GET,
					entity,
					Receta.class
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
		return new Receta();
	}

	@Override
	public Receta modificarReceta(String token, Receta rece) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(token);
			HttpEntity<Receta> entity = new HttpEntity<>(rece, headers);
			ResponseEntity<Receta> response = restTemp.exchange(
					URL,
					HttpMethod.PUT,
					entity,
					Receta.class
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
		return new Receta();
	}

	@Override
	public Boolean borrarReceta(String token, Integer id) {
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
