package com.ipartek.controlador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ipartek.modelo.Usuario;
import com.ipartek.servicio.RolServicio;
import com.ipartek.servicio.UsuarioServicio;

import jakarta.servlet.http.HttpSession;

@Controller
public class filtroBusqueda {

	@Autowired
	private UsuarioServicio usuarioServ;
	
	@Autowired
	private RolServicio rolServ;
	
	@GetMapping("/filtrarTabla")
	public String filtrarBusqueda(
			Model model,
			HttpSession session,
			@RequestParam String usuario,
			@RequestParam String rol_nombre) {
		
		try {
			System.out.println("=================");
			System.out.println("USU DEL FORM: " + usuario);
			if(usuario!=null && !usuario.isEmpty() && !"".equals(usuario)) {
				Usuario usuarioEncontrado = usuarioServ.obtenerUsuarioPorNombre(usuario);
				System.out.println("=================");
				System.out.println("USU ENCONTRADO EN BBDD: " + usuarioEncontrado);
				if(usuarioEncontrado!=null && usuarioEncontrado.getId()>0) {
					List<Usuario> listaUsuarioEncontrado = new ArrayList<Usuario>();
					listaUsuarioEncontrado.add(usuarioEncontrado);
					model.addAttribute("listaUsuarios", listaUsuarioEncontrado);
					System.out.println("=================");
					System.out.println("LISTA DE USUARIOS: " + listaUsuarioEncontrado);
				} else {
					model.addAttribute("listaUsuarios", usuarioServ.obtenerTodosUsuarios());
				}
			} else {
				model.addAttribute("listaUsuarios", usuarioServ.obtenerTodosUsuarios());
			}
			
			if(rol_nombre!=null && !rol_nombre.isEmpty() && !"".equals(rol_nombre)) {
				model.addAttribute("listaUsuarios", usuarioServ.obtenerUsuarioRolNombre(rol_nombre));
			}
			
			model.addAttribute("obj_usuario", new Usuario());
			model.addAttribute("listaRoles", rolServ.obtenerTodosLosRoles());
			model.addAttribute("s_usu", (Usuario)session.getAttribute("s_usu"));
			return "administracion";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/MenuAdministracion";
		}
	}
}
