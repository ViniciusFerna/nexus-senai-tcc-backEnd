package com.nexus.nexus.model;


import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "pedidoTransporte")
public class pedidoTransporte {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "veiculo_id", referencedColumnName = "id", nullable = false)
	private Veiculo veiculo;
	
	@ManyToOne
	@JoinColumn(name = "rota_id", referencedColumnName = "id", nullable = false)
	private Rotas rota;
	
	private LocalDate dataInicio;
	
	private LocalDate dataFim;
	
	private String status;
	
	private double custoTotal;
	
}
