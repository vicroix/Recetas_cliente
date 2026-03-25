package com.ipartek.pojo;

public class Dificultad {

	private int id;
	private String dificultad;

	public Dificultad(int id, String dificultad) {
		super();
		this.id = id;
		this.dificultad = dificultad;
	}

	public Dificultad(String dificultad) {
		super();
		this.dificultad = dificultad;
	}
	
	public Dificultad() {
		super();
		this.id = 0;
		this.dificultad = "";
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

	@Override
	public String toString() {
		return "Dificultad [id=" + id + ", dificultad=" + dificultad + "]";
	}
	
}
