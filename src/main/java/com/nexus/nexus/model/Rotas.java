package com.nexus.nexus.model;

<<<<<<< HEAD
=======
import java.util.List;
import jakarta.persistence.ElementCollection; 
>>>>>>> origin/main
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
<<<<<<< HEAD
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String origem;
	
	private String destino;
	
	private double distancia;
	
	private int tempoEstimado;
	
}
=======
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ElementCollection 
    private List<String> pontosPassagem;
    
    private double distanciaTotalRota;
    
    private double tempoEstimado;
    
}
>>>>>>> origin/main
