package com.ipartek.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ipartek.auxiliar.Auxiliar;
import com.ipartek.componente.JwtUtil;
import com.ipartek.modelo.Usuario;
import com.ipartek.servicio.UsuarioServicio;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginControlador {
	
	@Autowired
	private UsuarioServicio usuarioServ;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/ValidarUsuario")
	public String inicio(Model model, @ModelAttribute Usuario usu, HttpSession session) {
		Usuario usuTemp= usuarioServ.obtenerUsuarioPorNombre(usu.getUser().trim());
		if (usuTemp!=null ) {
			//usuario si encontrado en la BD 
			String passTemp=usu.getPass()+usuTemp.getSalt();
			String passTempHasheado=Auxiliar.hashear(passTemp);
			System.out.println("========================");
			System.out.println("Datos de usuario introducido: " + usu);
			System.out.println("Datos obtenidos de BBDD por nombre: " + usuTemp);
			if (passTempHasheado.equalsIgnoreCase(usuTemp.getPass())  
					&& (usuTemp.getRole().getId()==1 || usuTemp.getRole().getId()==2) || usuTemp.getRole().getId()==3) {
				String token=jwtUtil.generateToken(usuTemp.getUser(), usuTemp.getRole().getNombre());
				session.setAttribute("s_token", token);
				session.setAttribute("s_usu", usuTemp);
				System.out.println("=======================");
				System.out.println("USUARIO INICIADO EN LOGIN: " + session.getAttribute("s_usu"));
				return "redirect:/MenuInicio";
			}
			return "redirect:/";
		}else {
			//usuario no encontrado en la BD, vamos a login de inicio
			return "redirect:/";
		}
	}
	
	@GetMapping("/CerrarSesion")
	public String cerrarSesion(
			HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
