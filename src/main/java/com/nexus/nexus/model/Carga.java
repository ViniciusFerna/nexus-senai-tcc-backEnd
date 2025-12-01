package com.nexus.nexus.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Carga {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	private double peso;

	private double valor;

	private String tipo;

	private String status;

	private String descricao;

	@OneToOne(mappedBy = "carga")
	private pedidoTransporte pedido;

}
