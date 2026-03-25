package com.ipartek.servicioREST;

import java.util.List;

import com.ipartek.pojo.Receta;

public interface RecetasServicio {
	List<Receta> obtenerTodasRecetas(String token);
	Receta insertarReceta(String token, Receta rece);
	Receta obtenerRecetaPorId(String token, Integer id);
	Receta modificarReceta(String token, Receta rece);
	Boolean borrarReceta(String token, Integer id);
}
