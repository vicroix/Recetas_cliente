package com.ipartek.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ipartek.modelo.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer>{
	@Query(value = "SELECT id, user,'' as pass ,'' as salt, role_id FROM usuarios;", nativeQuery = true)
	List<Usuario> findAllSaniticed();
	
	@Query(value="SELECT u FROM Usuario u WHERE u.user LIKE %:texto%")
	List<Usuario> findByUsers(@Param("texto") String texto);
	
	Usuario findByUser(String nombre);
	
	List<Usuario> findByRoleNombre(String rol);
}
