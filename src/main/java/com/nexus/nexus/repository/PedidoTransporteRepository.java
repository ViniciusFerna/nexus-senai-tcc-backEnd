package com.nexus.nexus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexus.nexus.model.pedidoTransporte;

public interface PedidoTransporteRepository extends JpaRepository<pedidoTransporte, Long>{

	Optional<pedidoTransporte> findById(Long id);
	
}
