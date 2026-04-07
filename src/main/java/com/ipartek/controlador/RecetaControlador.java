package com.ipartek.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ipartek.componente.JwtUtil;
import com.ipartek.modelo.Usuario;
import com.ipartek.pojo.Receta;
import com.ipartek.servicioREST.DificultadServicio;
import com.ipartek.servicioREST.RecetasServicio;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpSession;

@Controller
public class RecetaControlador {

	@Autowired
	private DificultadServicio dificultadServ;
	
	@Autowired
	private RecetasServicio recetaServ;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/InsertarReceta")
	public String insertarReceta(
			RedirectAttributes flash,
			HttpSession session, 
			@ModelAttribute Receta obj_receta) {
		try {
			String token = "";
			if ((String) session.getAttribute("s_token") != null) {
				token = (String) session.getAttribute("s_token");
			}
			if (jwtUtil.isTokenValid(token)) {
				Claims claims = jwtUtil.extractClaims(token);
				if (claims.get("rol").equals("ADMIN") || claims.get("rol").equals("USUARIO")) {
					Receta recetaTemp = recetaServ.insertarReceta(token, obj_receta);
					if(recetaTemp!=null) {
						flash.addFlashAttribute("success", "Receta " + obj_receta.getNombre() + " añadida correctamente");
					}
					return "redirect:/MenuInicio";
				}
			}
			return "redirect:/MenuInicio";
		} catch (Exception e) {
			e.printStackTrace();
			flash.addFlashAttribute("MsgError", e.getMessage());
			return "redirect:/MenuInicio";
		}
	
	}

	@GetMapping("/ModificarReceta")
	public String frmModificarReceta(Model model, HttpSession session, @RequestParam Integer id) {
		String token = "";
		Usuario usu = (Usuario) session.getAttribute("s_usu");
		if ((String) session.getAttribute("s_token") != null) {
			token = (String) session.getAttribute("s_token");
		}
		if (jwtUtil.isTokenValid(token)) {
			Claims claims = jwtUtil.extractClaims(token);
			if (claims.get("rol").equals("ADMIN")) {
				Receta recetaTemp = recetaServ.obtenerRecetaPorId(token, id);
				if (recetaTemp != null) {
					model.addAttribute("obj_receta", recetaTemp);
					model.addAttribute("listaDificultades", dificultadServ.obtenerTodasDificultades(token));
					model.addAttribute("s_usu", usu);
					return "frm_modificar_receta";
				}
			}
		}
		return "redirect:/";
	}

	@PostMapping("/ModificarReceta")
	public String modificarReceta(
			RedirectAttributes flash,
			Model model,
			HttpSession session, 
			@ModelAttribute Receta obj_receta) {
		try {
			String token = "";
			if ((String) session.getAttribute("s_token") != null) {
				token = (String) session.getAttribute("s_token");
			}
			if (jwtUtil.isTokenValid(token)) {
				Claims claims = jwtUtil.extractClaims(token);
				if (claims.get("rol").equals("ADMIN")) {
					recetaServ.modificarReceta(token, obj_receta);
					flash.addFlashAttribute("success", "Receta " + obj_receta.getNombre() + " modificada correctamente");
				}
			}
			return "redirect:/MenuInicio";
		} catch (Exception e) {
			e.printStackTrace();
			String token = (String) session.getAttribute("s_token");
			Usuario usu = (Usuario) session.getAttribute("s_usu");
			Receta recetaTemp = recetaServ.obtenerRecetaPorId(token, obj_receta.getId());
			if(jwtUtil.isTokenValid(token)) {
				Claims claims = jwtUtil.extractClaims(token);
				if(claims.get("rol").equals("ADMIN")) {
					model.addAttribute("obj_receta", recetaTemp);
					model.addAttribute("MsgError", e.getMessage());
					model.addAttribute("s_usu", usu);
				}
			}
			return "redirect:/MenuInicio";
		}
	}
	
	
	@GetMapping("/BorrarReceta")
	public String borrarReceta(
			RedirectAttributes flash,
			HttpSession session, 
			@RequestParam Integer id) {
		try {
			String token = "";
			if ((String) session.getAttribute("s_token") != null) {
				token = (String) session.getAttribute("s_token");
			}
			if (jwtUtil.isTokenValid(token)) {
				Claims claims = jwtUtil.extractClaims(token);
				if (claims.get("rol").equals("ADMIN")) {
					Receta recetaTemp = recetaServ.obtenerRecetaPorId(token, id);
					recetaServ.borrarReceta(token, id);
					flash.addFlashAttribute("success", "Receta " + recetaTemp.getNombre() + " eliminada correctamente");
				}
			}
			return "redirect:/MenuInicio";
		} catch (Exception e) {
			e.printStackTrace();
			flash.addFlashAttribute("MsgError", e.getMessage());
			return "redirect:/MenuInicio";
		}
	}
}
