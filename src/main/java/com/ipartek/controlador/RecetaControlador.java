package com.ipartek.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ipartek.componente.JwtUtil;
import com.ipartek.pojo.Receta;
import com.ipartek.servicioREST.RecetasServicio;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpSession;

@Controller
public class RecetaControlador {

	@Autowired
	private RecetasServicio recetaServ;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/InsertarReceta")
	public String insertarReceta(
			HttpSession session, 
			@RequestBody Receta obj_receta) {
		String token = "";
		if((String)session.getAttribute("s_token")!=null) {
			token = (String)session.getAttribute("s_token");
		}
		if(jwtUtil.isTokenValid(token)) {
			Claims claims = jwtUtil.extractClaims(token);
			if(claims.get("rol").equals("ADMIN")) {
				recetaServ.insertarReceta(token, obj_receta);
				return "redirect:/MenuInicio";
			}
		}
		return "redirect:/";
	}
	
	@GetMapping("/BorrarReceta")
	public String borrarReceta(
			HttpSession session, 
			@RequestParam Integer id) {
		String token = "";
		if((String)session.getAttribute("s_token")!=null) {
			token = (String)session.getAttribute("s_token");
		}
		if(jwtUtil.isTokenValid(token)) {
			Claims claims = jwtUtil.extractClaims(token);
			if(claims.get("rol").equals("ADMIN")) {
				recetaServ.borrarReceta(token, id);
				return "redirect:/MenuInicio";
			}
		}
		return "redirect:/";
	}
}
