package com.ipartek.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ipartek.auxiliar.Auxiliar;
import com.ipartek.modelo.Rol;
import com.ipartek.modelo.Usuario;
import com.ipartek.repositorio.RolRepositorio;
import com.ipartek.repositorio.UsuarioRepositorio;

@Component
public class CargarDatosIniciales implements CommandLineRunner {

	@Autowired
	private RolRepositorio rolRepo;

	@Autowired
	private UsuarioRepositorio usuRepo;

	@Override
	public void run(String... args) throws Exception {
		if (rolRepo.count() == 0) {
			Rol admin = new Rol("ADMIN");
			Rol usuario = new Rol("USUARIO");
			Rol bloqueado = new Rol("BLOQUEADO");
			Rol baneado = new Rol("BANEADO");
			rolRepo.saveAll(List.of(admin, usuario, bloqueado, baneado));
		}
		
//		PASS de inicio con usuarios iniciales: 1234
		if (usuRepo.count() == 0) {
			String passConPimienta = "1234Kolitza";
			String passHasheadaTemp = Auxiliar.hashear(passConPimienta);
			
			Rol rolAdmin = rolRepo.findByNombre("ADMIN");
			Rol rolUsuario = rolRepo.findByNombre("USUARIO");
			Rol rolBloqueado = rolRepo.findByNombre("BLOQUEADO");
			Rol rolBaneado = rolRepo.findByNombre("BANEADO");
			
			Usuario admin = new Usuario("admin", "ba7446f87e8c9b009a91380584216bf7cc555dcd894d78386efc0687ebd20fd6",
					Auxiliar.generarSal(16), rolAdmin);
			admin.setPass(Auxiliar.hashear(passHasheadaTemp + admin.getSalt()));
			
			Usuario usuario = new Usuario("usuario", "ddb0b2a926335731516a96fcee21a25254898068c2721a7e2bcd99499e296246",
					Auxiliar.generarSal(16), rolUsuario);
			usuario.setPass(Auxiliar.hashear(passHasheadaTemp + usuario.getSalt()));
			
			Usuario bloqueado = new Usuario("baneado",
					"a85a88ab2b0dafb126a937585b8128423ff9b6d5d27698ab1b46b9a858fffb97", Auxiliar.generarSal(16), rolBaneado);
			bloqueado.setPass(Auxiliar.hashear(passHasheadaTemp + bloqueado.getSalt()));
			
			Usuario baneado = new Usuario("bloqueado",
					"6053f91036cbc5e6e80f63d3734e81e70e095dc26f06d2a7ddd169ad871a8205", Auxiliar.generarSal(16), rolBloqueado);
			baneado.setPass(Auxiliar.hashear(passHasheadaTemp + baneado.getSalt()));

			usuRepo.saveAll(List.of(admin, usuario, baneado, bloqueado));
		}
	}
}
