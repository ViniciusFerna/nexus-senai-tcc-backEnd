package com.nexus.nexus.model;


import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "pedidoTransporte")
public class pedidoTransporte {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Date dataCriacao;
	
	private String status;
	
	private String origem;
	
	private String destino;
	
	@OneToOne
	@JoinColumn(name = "fatura_id", referencedColumnName = "id", nullable = true)
	private Fatura idFatura;
	
}
