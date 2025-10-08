package com.nexus.nexus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

/* DTO significa 
(Data Transfer Object) */


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

    // Apenas o ID da entidade relacionada é exposto.
    private Long idVeiculo;
}