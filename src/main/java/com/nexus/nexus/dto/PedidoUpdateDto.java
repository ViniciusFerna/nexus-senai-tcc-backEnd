package com.nexus.nexus.dto;

import java.time.LocalDate;

import com.nexus.nexus.model.Rotas;
import com.nexus.nexus.model.Veiculo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PedidoUpdateDto {

	private Long id;
	
	@NotNull(message = "Escolha um veículo para esse pedido")
	private Veiculo veiculo;
	
	@NotNull(message = "Escolha uma rota para esse pedido")
	private Rotas rota;
	
	private  LocalDate dataInicio;
	
	private LocalDate dataFim;
	
	private String status;
	
}
