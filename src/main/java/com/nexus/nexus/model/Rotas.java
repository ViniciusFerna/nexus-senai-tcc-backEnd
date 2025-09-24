package com.nexus.nexus.model;

import java.util.List;
import jakarta.persistence.ElementCollection; 
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
    
    @ElementCollection 
    private List<String> pontosPassagem;
    
    private double distanciaTotalRota;
    
    private double tempoEstimado;
    
}