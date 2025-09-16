package com.nexus.nexus.dto;

import lombok.Data;

@Data
public class ClienteDTO {
    private Long id;
    private String cnpjCpf;
    private String endereco;
    private String telefone;
}
