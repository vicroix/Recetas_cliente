package com.ipartek.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ipartek.componente.JwtUtil;
import com.ipartek.modelo.Usuario;
import com.ipartek.pojo.Receta;
import com.ipartek.servicio.RolServicio;
import com.ipartek.servicio.UsuarioServicio;
import com.ipartek.servicioREST.DificultadServicio;
import com.ipartek.servicioREST.RecetasServicio;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpSession;

@Controller
public class filtroBusqueda {

	@Autowired
	private UsuarioServicio usuarioServ;

	@Autowired
	private RolServicio rolServ;

	@Autowired
	private RecetasServicio recetaServ;

	@Autowired
	private DificultadServicio dificultadServ;

	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping("/filtrarTablaUsuarios")
	public String filtrarBusquedaUsuarios(Model model, HttpSession session, @RequestParam String usuario,
			@RequestParam String rol_nombre) {

		try {
			String token = "";
			if(session.getAttribute("s_token")!=null) {
				token = (String)session.getAttribute("s_token");
			}
			if(jwtUtil.isTokenValid(token)) {
				Claims claims = jwtUtil.extractClaims(token);
				if(claims.get("rol").equals("ADMIN") || claims.get("rol").equals("USUARIO")) {
					if (usuario != null && !usuario.isEmpty() && !"".equals(usuario)) {
						List<Usuario> usuarioEncontrado = usuarioServ.obtenerUsuariosPorNombre(usuario);
						if (usuarioEncontrado != null) {
							model.addAttribute("listaUsuarios", usuarioEncontrado);
						} else {
							model.addAttribute("listaUsuarios", usuarioServ.obtenerTodosUsuarios());
						}
					} else {
						model.addAttribute("listaUsuarios", usuarioServ.obtenerTodosUsuarios());
					}
					
					if (rol_nombre != null && !rol_nombre.isEmpty() && !"".equals(rol_nombre)) {
						model.addAttribute("listaUsuarios", usuarioServ.obtenerUsuariosRolNombre(rol_nombre));
					}
					
					model.addAttribute("obj_usuario", new Usuario());
					model.addAttribute("listaRoles", rolServ.obtenerTodosLosRoles());
					model.addAttribute("s_usu", (Usuario) session.getAttribute("s_usu"));
					return "administracion";
				}
			}
			return "redirect:/";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/MenuAdministracion";
		}
	}

	@GetMapping("/filtrarTablaRecetas")
	public String filtrarBusquedaRecetas(Model model, HttpSession session, @RequestParam String nombre,
			@RequestParam String dificultad) {
		try {
			String token = "";
			if ((String) session.getAttribute("s_token") != null) {
				token = (String) session.getAttribute("s_token");
			}
			if (jwtUtil.isTokenValid(token)) {
				Claims claims = jwtUtil.extractClaims(token);
				if (claims.get("rol").equals("ADMIN") || claims.get("rol").equals("USUARIO")) {
					if (nombre != null && !nombre.isEmpty() && !"".equals(nombre)) {
						List<Receta> recetasEncontradas = recetaServ.obtenerRecetaPorNombre(token, nombre);
						if (recetasEncontradas != null) {
							model.addAttribute("listaRecetas", recetasEncontradas);
						} else {
							model.addAttribute("listaRecetas", recetaServ.obtenerTodasRecetas(token));
						}
					} else {
						model.addAttribute("listaRecetas", recetaServ.obtenerTodasRecetas(token));
					}

					if (dificultad != null && !dificultad.isEmpty() && !"".equals(dificultad)) {
						model.addAttribute("listaRecetas", recetaServ.obtenerRecetaPorDificultad(token, dificultad));
					}
					
					model.addAttribute("obj_receta", new Receta());
					model.addAttribute("listaDificultades", dificultadServ.obtenerTodasDificultades(token));
					model.addAttribute("s_usu", (Usuario) session.getAttribute("s_usu"));
					return "inicio";
				} else {
					return "redirect:/MenuInicio";					
				}
			}
			return "redirect:/";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/MenuAdministracion";
		}
	}
}
