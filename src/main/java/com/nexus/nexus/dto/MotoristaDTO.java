package com.nexus.nexus.dto;
/* DTO significa 
(Data Transfer Object) */

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF; // Exemplo de validador específico

@Data
public class MotoristaDTO {

    private Long id;

    @NotBlank(message = "O nome do motorista é obrigatório.")
    private String nomeMotorista;

    @NotBlank(message = "O CPF é obrigatório.")
    @CPF(message = "CPF inválido.") // Usa um validador de CPF
    private String cpf;

    @NotBlank(message = "A CNH é obrigatória.")
    @Pattern(regexp = "[0-9]{11}", message = "A CNH deve conter 11 dígitos.")
    private String cnh;

    private String telefone;
    private String status;

    @NotNull(message = "O custo por KM é obrigatório.")
    @PositiveOrZero(message = "O custo por KM não pode ser negativo.")
    private float custoKm;

    // Apenas o ID da entidade relacionada é exposto.
    private Long veiculoId;
}