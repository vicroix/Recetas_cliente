package com.ipartek.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ipartek.componente.JwtUtil;
import com.ipartek.modelo.Usuario;
import com.ipartek.pojo.Dificultad;
import com.ipartek.servicioREST.DificultadServicio;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpSession;

@Controller
public class DificultadControlador {

	@Autowired
	private DificultadServicio dificultadServ;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/InsertarDificultad")
	public String insertarDificultad(
			HttpSession session,
			@ModelAttribute Dificultad obj_dificultad) {
		String token = "";
		if ((String) session.getAttribute("s_token") != null) {
			token = (String) session.getAttribute("s_token");
		}
		if (jwtUtil.isTokenValid(token)) {
			Claims claims = jwtUtil.extractClaims(token);
			if (claims.get("rol").equals("ADMIN")) {
				dificultadServ.insertarDificultad(token, obj_dificultad);
				return "redirect:/MenuDificultades";
			}
		}
		return "redirect:/";
	}
	
	@GetMapping("/ModificarDificultad")
	public String frmModificarDificultad(
			Model model,
			HttpSession session,
			@RequestParam Integer id) {
		String token = "";
		Usuario usu = (Usuario) session.getAttribute("s_usu");
		if ((String) session.getAttribute("s_token") != null) {
			token = (String) session.getAttribute("s_token");
		}
		if (jwtUtil.isTokenValid(token)) {
			Claims claims = jwtUtil.extractClaims(token);
			if (claims.get("rol").equals("ADMIN")) {
				Dificultad difiTemp = dificultadServ.obtenerDificultadPorId(token, id);
				model.addAttribute("obj_dificultad", difiTemp);
				model.addAttribute("s_usu", usu);
				return "frm_modificar_dificultad";
			}
		}
		return "redirect:/";
	}
	
	@PostMapping("/ModificarDificultad")
	public String modificarDificultad(
			Model model,
			HttpSession session,
			@ModelAttribute Dificultad obj_dificultad) {
		String token = "";
		Usuario usu = (Usuario) session.getAttribute("s_usu");
		if ((String) session.getAttribute("s_token") != null) {
			token = (String) session.getAttribute("s_token");
		}
		if (jwtUtil.isTokenValid(token)) {
			Claims claims = jwtUtil.extractClaims(token);
			if (claims.get("rol").equals("ADMIN")) {
				dificultadServ.modificarDificultad(token, obj_dificultad);
				return "redirect:/MenuDificultades";
			}
		}
		return "redirect:/";
	}
	
	@GetMapping("/BorrarDificultad")
	public String borrarDificultad(
			HttpSession session,
			@RequestParam Integer id) {
		String token = "";
		if ((String) session.getAttribute("s_token") != null) {
			token = (String) session.getAttribute("s_token");
		}
		if (jwtUtil.isTokenValid(token)) {
			Claims claims = jwtUtil.extractClaims(token);
			if (claims.get("rol").equals("ADMIN")) {
				dificultadServ.borrarDificultad(token, id);
				return "redirect:/MenuDificultades";
			}
		}
		return "redirect:/";
	}
}
