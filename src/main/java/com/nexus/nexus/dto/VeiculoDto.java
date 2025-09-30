package com.nexus.nexus.dto;

import lombok.Data;

@Data
public class VeiculoDto {
    private Long id;
    private String placa;
    private String modelo;
    private String tipoVeiculo;
    private double capacidadePeso;
    private String status;
}
