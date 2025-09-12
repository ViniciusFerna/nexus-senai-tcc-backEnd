package com.nexus.nexus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexus.nexus.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	public Usuario findByEmail(String email);
	
	Boolean existsByEmail(String email);
	
}
