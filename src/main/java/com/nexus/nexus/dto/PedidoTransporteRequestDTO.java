package com.nexus.nexus.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PedidoTransporteRequestDTO {
	
	private Long veiculoId;
	private Long rotaId;
	private LocalDate dataInicio;
	private LocalDate dataFim;
	
}
