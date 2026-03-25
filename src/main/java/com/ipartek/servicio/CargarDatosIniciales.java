package com.ipartek.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

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
		if (usuRepo.count() == 0) {
			Rol rolAdmin = rolRepo.findAll(Sort.by(Sort.Direction.ASC, "id")).get(0);
			Rol rolUsuario = rolRepo.findAll(Sort.by(Sort.Direction.ASC, "id")).get(1);
			Rol rolBloqueado = rolRepo.findAll(Sort.by(Sort.Direction.ASC, "id")).get(2);
			Rol rolBaneado = rolRepo.findAll(Sort.by(Sort.Direction.ASC, "id")).get(3);
			Usuario admin = new Usuario("admin", "ba7446f87e8c9b009a91380584216bf7cc555dcd894d78386efc0687ebd20fd6",
					"rojo", rolAdmin);
			Usuario usuario = new Usuario("usuario", "ddb0b2a926335731516a96fcee21a25254898068c2721a7e2bcd99499e296246",
					"verde", rolUsuario);
			Usuario baneado = new Usuario("bloqueado",
					"6053f91036cbc5e6e80f63d3734e81e70e095dc26f06d2a7ddd169ad871a8205", "azul", rolBloqueado);
			Usuario bloqueado = new Usuario("baneado",
					"a85a88ab2b0dafb126a937585b8128423ff9b6d5d27698ab1b46b9a858fffb97", "negro", rolBaneado);
			usuRepo.saveAll(List.of(admin, usuario, baneado, bloqueado));
		}
	}
}
