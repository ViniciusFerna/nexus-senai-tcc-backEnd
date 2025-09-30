package com.nexus.nexus.model;

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
@Table(name = "Motorista")
public class Motorista {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nomeMotorista;
	
	private String cpf;
	
	private String cnh;
	
	private String telefone;
	
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "veiculo_id", referencedColumnName = "id", nullable = false)
	private Veiculo idVeiculo;
	
}
