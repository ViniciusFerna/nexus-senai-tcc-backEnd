package com.nexus.nexus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexus.nexus.model.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long>{

	Optional<Veiculo> findById(Long id);
	
}
