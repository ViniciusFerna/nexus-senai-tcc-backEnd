package com.nexus.nexus.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Viagem")
public class Viagem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Date dataInicio;
	
	private Date dataFim;
	
	private String status;
	
	@OneToMany
	@JoinColumn(name = "ocorrencia_id", referencedColumnName = "id", nullable = true)
	private Ocorrencia idOcorrencia;
	
	@OneToOne
	@JoinColumn(name = "rotas_id", referencedColumnName = "id", nullable = false)
	private Rotas idRotas;
	
}
