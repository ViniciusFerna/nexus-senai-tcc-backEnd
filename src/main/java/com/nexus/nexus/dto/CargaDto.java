package com.nexus.nexus.dto;

import lombok.Data;

@Data
public class CargaDto {

    private Long id;
    
    private String nome;
    
    private double peso;
    
    private double valor;
    
    private String tipo;
    
    private String status;
    
    private String descricao;

}
