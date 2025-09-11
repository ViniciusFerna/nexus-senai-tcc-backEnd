package com.nexus.nexus.model;

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
@Table(name = "Calculos")

public class Calculos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "custoKm", referencedColumnName = "custoKm", nullable = false)
	private double custoKm;
	
	@OneToOne
	@JoinColumn(name = "distanciaTotal", referencedColumnName = "distanciaTotal", nullable = false)
	private double distanciaTotal;
	
	private double custoTotal;

}
