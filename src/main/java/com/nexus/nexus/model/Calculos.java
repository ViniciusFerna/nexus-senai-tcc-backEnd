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
	
	@OneToOne(targetEntity = Calculos.class)
	@JoinColumn(name = "custoKm", referencedColumnName = "custoKmMotorista", nullable = false)
	private double custoKm;
	
	@OneToOne(targetEntity = Calculos.class)
	@JoinColumn(name = "distanciaTotal", referencedColumnName = "distanciaTotalRota", nullable = false)
	private double distanciaTotal;
	
	private double custoTotal;

}
