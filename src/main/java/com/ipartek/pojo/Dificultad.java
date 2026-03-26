package com.ipartek.pojo;

import java.util.ArrayList;
import java.util.List;

public class Dificultad {

	private int id;
	private String dificultad;
	private List<Receta> recetas;

	public Dificultad(int id, String nombreDificultad, List<Receta> recetas) {
		super();
		this.id = id;
		this.dificultad = nombreDificultad;
		this.recetas = recetas;
	}

	public Dificultad(String dificultad) {
		super();
		this.dificultad = dificultad;
	}

	public Dificultad() {
		super();
		this.id = 0;
		this.dificultad = "";
		this.recetas = new ArrayList<Receta>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDificultad() {
		return dificultad;
	}

	public void setDificultad(String dificultad) {
		this.dificultad = dificultad;
	}

	public List<Receta> getRecetas() {
		return recetas;
	}

	public void setRecetas(List<Receta> recetas) {
		this.recetas = recetas;
	}

	@Override
	public String toString() {
		return "Dificultad [id=" + id + ", dificultad=" + dificultad + ", recetas=" + recetas + "]";
	}
	
}
