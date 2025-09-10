package com.nexus.nexus.model;


import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;



@Data
@Entity
@Table(name = "Fatura")
public class Fatura {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private double valorTotal;
	
	private Date dataEmissao;
	
	private Date dataVencimento;
	
	private String statusPagamento;
	
}
