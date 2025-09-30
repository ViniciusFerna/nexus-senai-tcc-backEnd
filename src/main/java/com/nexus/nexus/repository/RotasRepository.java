package com.nexus.nexus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexus.nexus.model.Rotas;

public interface RotasRepository extends JpaRepository<Rotas, Long>{

	Optional<Rotas> findById(Long id);
	
}
