package com.nexus.nexus.model;
 
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
 
 
@Data
@Entity
@Table(name = "Rotas")
public class Rotas {
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String origem;
	
	private String destino;
	
	private double distancia;
	
	private int tempoEstimado;
	
}
 
