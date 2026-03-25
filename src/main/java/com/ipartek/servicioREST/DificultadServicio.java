package com.ipartek.servicioREST;

import java.util.List;

import com.ipartek.pojo.Dificultad;

public interface DificultadServicio{
	List<Dificultad> obtenerTodasDificultades(String token);
	Dificultad insertarDificultad(String token, Dificultad difi);
	Dificultad obtenerDificultadPorId(String token, Integer id);
	Dificultad modificarDificultad(String token, Dificultad difi);
	Boolean borrarDificultad(String token, Integer id);
}
