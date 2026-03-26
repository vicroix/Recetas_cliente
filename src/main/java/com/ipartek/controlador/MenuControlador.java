package com.ipartek.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ipartek.componente.JwtUtil;
import com.ipartek.modelo.Usuario;
import com.ipartek.pojo.Dificultad;
import com.ipartek.pojo.Receta;
import com.ipartek.servicio.RolServicio;
import com.ipartek.servicio.UsuarioServicio;
import com.ipartek.servicioREST.DificultadServicio;
import com.ipartek.servicioREST.RecetasServicio;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpSession;

@Controller
public class MenuControlador {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UsuarioServicio usuarioServ;
	
	@Autowired
	private RolServicio rolServ;
	
	@Autowired
	private DificultadServicio dificultadServ;
	
	@Autowired
	private RecetasServicio recetaServ;
	
	@GetMapping("/MenuInicio")
	public String menuInicio(
			Model model,
			HttpSession session) {
		String token = "";
		Usuario usu = (Usuario) session.getAttribute("s_usu");
		if ((String) session.getAttribute("s_token") != null) {
			token = (String) session.getAttribute("s_token");
		}
		if (jwtUtil.isTokenValid(token)) {
			Claims claims = jwtUtil.extractClaims(token);
			if (claims.get("rol").equals("ADMIN") || claims.get("rol").equals("USUARIO") || claims.get("rol").equals("BLOQUEADO")) {
				model.addAttribute("obj_receta", new Receta());
				model.addAttribute("listaRecetas", recetaServ.obtenerTodasRecetas(token));
				model.addAttribute("listaDificultades", dificultadServ.obtenerTodasDificultades(token));
				model.addAttribute("s_usu", usu);
				System.out.println("DATOS USU: " + usu);
				return "inicio";
			}
		}
		return "redirect:/";
	}
	
	@GetMapping("/MenuDificultades")
	public String menuMarcas(
			Model model,
			HttpSession session) {
		String token = "";
		Usuario usu = (Usuario) session.getAttribute("s_usu");
		if ((String) session.getAttribute("s_token") != null) {
			token = (String) session.getAttribute("s_token");
		}
		if (jwtUtil.isTokenValid(token)) {
			Claims claims = jwtUtil.extractClaims(token);
			if (claims.get("rol").equals("ADMIN") || claims.get("rol").equals("USUARIO") || claims.get("rol").equals("BLOQUEADO")) {
				model.addAttribute("obj_dificultad", new Dificultad());
				model.addAttribute("listaDificultades", dificultadServ.obtenerTodasDificultades(token));
				model.addAttribute("s_usu", (Usuario)session.getAttribute("s_usu"));
				return "dificultad";
			}
		}
		return "redirect:/";
	}
	
	@GetMapping("/MenuAdministracion")
	public String menuAdmin(
			Model model,
			HttpSession session) {
		String token = "";
		if ((String) session.getAttribute("s_token") != null) {
			token = (String) session.getAttribute("s_token");
		}
		if(jwtUtil.isTokenValid(token)) {
			Claims claims = jwtUtil.extractClaims(token);
			if(claims.get("rol").equals("ADMIN")) {
				Usuario usu = (Usuario) session.getAttribute("s_usu");
				model.addAttribute("obj_usuario", new Usuario());
				model.addAttribute("listaUsuarios", usuarioServ.obtenerTodosUsuarios());
				model.addAttribute("listaRoles", rolServ.obtenerTodosLosRoles());
				model.addAttribute("s_usu", (Usuario)session.getAttribute("s_usu"));
				return "administracion";				
			}
		}
		return "redirect:/";
	}
}
