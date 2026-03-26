package com.ipartek.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ipartek.servicio.RolServicio;
import com.ipartek.servicio.RolServicioImp;
import com.ipartek.servicio.UsuarioServicio;
import com.ipartek.auxiliar.Auxiliar;
import com.ipartek.componente.JwtUtil;
import com.ipartek.modelo.Usuario;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioControlador {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private  UsuarioServicio usuarioServ;

	@Autowired
	private RolServicio rolServ;

	@PostMapping("/RegistrarUsuario")
	public String registrarUsuario(
			RedirectAttributes flash, 
			HttpSession session, 
			@ModelAttribute Usuario obj_usuario) {
		if (obj_usuario != null) {
			String token = "";
			if ((String) session.getAttribute("s_token") != null) {
				token = (String) session.getAttribute("s_token");
			}
			if(jwtUtil.isTokenValid(token)) {
				Claims claims = jwtUtil.extractClaims(token);
				if(claims.get("rol").equals("ADMIN")) {
					try {
						obj_usuario.setSalt(Auxiliar.generarSal(16));
						obj_usuario.setPass(Auxiliar.hashear(obj_usuario.getPass() + obj_usuario.getSalt()));
						Usuario usuarioExistente = usuarioServ.obtenerUsuarioPorNombre(obj_usuario.getUser());
						if(usuarioExistente!=null) {
							return "redirect:/MenuAdministracion";
						}
						Usuario usuarioTemp = usuarioServ.guardarUsuario(obj_usuario);
						return "redirect:/MenuAdministracion";
					} catch (Exception e) {
						e.printStackTrace();
						return "redirect:/MenuAdministracion";
					} 
				}	
			}
		}
		return "redirect:/";
	}

	@GetMapping("/ModificarUsuario")
	public String frmModificarUsuario(
			Model model, 
			HttpSession session, 
			@RequestParam Integer id) {
		Usuario usu = (Usuario) session.getAttribute("s_usu");
		String token = "";
		if((String)session.getAttribute("s_token")!=null) {
			token = (String)session.getAttribute("s_token");
		}
		if(jwtUtil.isTokenValid(token)) {
			Claims claims = jwtUtil.extractClaims(token);
			if(claims.get("rol").equals("ADMIN")) {
					Usuario usuTemp = usuarioServ.obtenerUsuarioPorId(id);
					usuTemp.setPass("");
					model.addAttribute("obj_usuario", usuTemp);
					model.addAttribute("listaRoles", rolServ.obtenerTodosLosRoles());
					model.addAttribute("s_usu", usu);
					return "frm_modificar_usuario";	
			}		
		}
		return "redirect:/";
	}

	@PostMapping("/ModificarUsuario")
	public String modificarUsuario(
			RedirectAttributes flash,
			Model model,
			HttpSession session, 
			@ModelAttribute Usuario obj_usuario) {
		String token = "";
		 if((String)session.getAttribute("s_token")!=null) {
			token = (String)session.getAttribute("s_token"); 
		 }
		 if(jwtUtil.isTokenValid(token)) {
			 Claims claims = jwtUtil.extractClaims(token);
			 if(claims.get("rol").equals("ADMIN")) {
				 try {
						Usuario usu = (Usuario) session.getAttribute("s_usu");
						Usuario usuTemp = usuarioServ.obtenerUsuarioPorId(obj_usuario.getId());
						Usuario usuarioExistente = usuarioServ.obtenerUsuarioPorNombre(obj_usuario.getUser());
						if(usuarioExistente!=null && usuarioExistente!=usuTemp) {
							model.addAttribute("obj_usuario", usuTemp);
							model.addAttribute("listaRoles", rolServ.obtenerTodosLosRoles());
							model.addAttribute("s_usu", usu);
							return "frm_modificar_usuario";
						}
						System.out.println("===============================");
						System.out.println("DATOS ANTES DE MODIFICAR: " + usuTemp);
						usuTemp.setUser(obj_usuario.getUser());
						usuTemp.setRole(obj_usuario.getRole());
						System.out.println("===============================");
						System.out.println("DATOS DEL FORMULARIO ANTES DE ENTRAR AL IF DE MOD PASS: " + obj_usuario);
						if(obj_usuario.getPass() != null && !obj_usuario.getPass().trim().isEmpty()) {
							System.out.println("ENTRO PARA MODIFICAR CONTRASEÑA");
							usuTemp.setPass(Auxiliar.hashear(obj_usuario.getPass() + usuTemp.getSalt()));
						}
						if (obj_usuario.getId() == usuTemp.getId()) {
							System.out.println("===============================");
							System.out.println("DATOS ANTES DE GUARDAR: " + usuTemp);
						usuarioServ.modificarUsuario(usuTemp);
						}
						return "redirect:/MenuAdministracion";			
					} catch (Exception e) {
						e.printStackTrace();
						return "redirect:/MenuAdministracion";
					}
			 	}
		 	}
		 	return "redirect:/";
	}

	@GetMapping("/BorrarUsuario")
	public String borrarUsuario(
			RedirectAttributes flash,
			HttpSession session, 
			@RequestParam Integer id) {
		String token = "";
		if((String)session.getAttribute("s_token")!=null) {
			token = (String)session.getAttribute("s_token");
		}
		if(jwtUtil.isTokenValid(token)) {
			Claims claims = jwtUtil.extractClaims(token);
			if(claims.get("rol").equals("ADMIN")) {
				try {
					usuarioServ.borrarUsuario(id);
					return "redirect:/MenuAdministracion";
				} catch (Exception e) {
					e.printStackTrace();
					return "redirect:/MenuAdministracion";
				}
			}
		}
		return "redirect:/";
	}
}
