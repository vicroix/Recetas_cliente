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
			RedirectAttributes flash,
			HttpSession session,
			@ModelAttribute Dificultad obj_dificultad) {
		try {
			String token = "";
			if ((String) session.getAttribute("s_token") != null) {
				token = (String) session.getAttribute("s_token");
			}
			if (jwtUtil.isTokenValid(token)) {
				Claims claims = jwtUtil.extractClaims(token);
				if (claims.get("rol").equals("ADMIN")) {
					Dificultad difiTemp = dificultadServ.insertarDificultad(token, obj_dificultad);
					flash.addFlashAttribute("success", "Dificultad " + obj_dificultad.getDificultad() + " añadida correctamente");					
				}
			}
			return "redirect:/MenuDificultades";
		} catch (Exception e) {
			e.printStackTrace();
			flash.addFlashAttribute("MsgError", e.getMessage());
			return "redirect:/MenuDificultades";
		}
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
			RedirectAttributes flash,
			Model model,
			HttpSession session,
			@ModelAttribute Dificultad obj_dificultad) {
		try {
			String token = "";
			Usuario usu = (Usuario) session.getAttribute("s_usu");
			if ((String) session.getAttribute("s_token") != null) {
				token = (String) session.getAttribute("s_token");
			}
			if (jwtUtil.isTokenValid(token)) {
				Claims claims = jwtUtil.extractClaims(token);
				if (claims.get("rol").equals("ADMIN")) {
					dificultadServ.modificarDificultad(token, obj_dificultad);
					flash.addFlashAttribute("success", "Dificultad " + obj_dificultad.getDificultad() + " modificada correctamente");
				}
			}
			return "redirect:/MenuDificultades";
		} catch (Exception e) {
			e.printStackTrace();
			String token = (String) session.getAttribute("s_token");
			Usuario usu = (Usuario) session.getAttribute("s_usu");
			Dificultad difiTemp = dificultadServ.obtenerDificultadPorId(token, obj_dificultad.getId());
			if(jwtUtil.isTokenValid(token)) {
				Claims claims = jwtUtil.extractClaims(token);
				if(claims.get("rol").equals("ADMIN")) {
					model.addAttribute("obj_dificultad", difiTemp);
					model.addAttribute("MsgError", e.getMessage());
					model.addAttribute("s_usu", usu);
				}
			}
			return "redirect:/MenuDificultades";
		}
	}
	@GetMapping("/BorrarDificultad")
	public String borrarDificultad(
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
					Dificultad difiTemp = dificultadServ.obtenerDificultadPorId(token, id);
					dificultadServ.borrarDificultad(token, id);
					flash.addFlashAttribute("success", "Dificultad " + difiTemp.getDificultad() + " eliminada correctamente");
				}
			}
			return "redirect:/MenuDificultades";
		} catch (Exception e) {
			e.printStackTrace();
			flash.addFlashAttribute("MsgError", e.getMessage());
			return "redirect:/MenuDificultades";
		}
	}
}
