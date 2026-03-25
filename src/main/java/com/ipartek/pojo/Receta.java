package com.ipartek.pojo;

public class Receta {
	
	private int id;
	private String nombre;
	private Dificultad dificultad;

	public Receta(int id, String nombre, Dificultad dificultad) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.dificultad = dificultad;
	}
	public Receta(String nombre, Dificultad dificultad) {
		super();
		this.nombre = nombre;
		this.dificultad = dificultad;
	}

	public Receta() {
		super();
		this.id = 0;
		this.nombre = "";
		this.dificultad = new Dificultad();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Dificultad getDificultad() {
		return dificultad;
	}

	public void setDificultad(Dificultad dificultad) {
		this.dificultad = dificultad;
	}

	@Override
	public String toString() {
		return "Recetas [id=" + id + ", nombre=" + nombre + ", dificultad=" + dificultad + "]";
	}
	
}
