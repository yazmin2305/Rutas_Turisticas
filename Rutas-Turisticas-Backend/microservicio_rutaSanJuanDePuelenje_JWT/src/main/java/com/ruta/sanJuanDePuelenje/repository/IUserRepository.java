package com.ruta.sanJuanDePuelenje.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ruta.sanJuanDePuelenje.models.User;

public interface IUserRepository extends JpaRepository<User, Integer> {

	// Query para listar los usuarios por su estado, ya sea activado o desactivado
	@Query(value = "SELECT * FROM user WHERE state=?1", nativeQuery = true)
	Page<User> LstUserByState(boolean state, Pageable pageable);

	// Query que obtiene el usuario a partir de un correo electronico
	public User findByEmail(String email);

	// Query para encontrar un usuario a partir del token generado cuando se requiere cambiar la contraseña
	public Optional<User> findByTokenPassword(String tokenPassword);

	// Query que obtiene todos los usuarios que han hecho reservas en una determinada ruta	
	@Query(value = "SELECT DISTINCT r.user FROM Reserve r WHERE r.ruta.id = ?1")
	Page<User> LstUserByRuta(Integer idRuta, Pageable pageable);
}
