package com.nexus.nexus.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RotaDTO {

    @NotEmpty(message = "A lista de pontos de passagem não pode ser vazia.")
    private List<String> pontosPassagem;
    
    @Positive(message = "A distância total da rota deve ser um valor positivo.")
    private double distanciaTotalRota;
    
    @Positive(message = "O tempo estimado deve ser um valor positivo.")
    private double tempoEstimado;
}