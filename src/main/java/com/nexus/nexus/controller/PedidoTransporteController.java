package com.nexus.nexus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexus.nexus.model.pedidoTransporte;
import com.nexus.nexus.service.PedidoTransporteService;

@RestController
@CrossOrigin
@RequestMapping("/pedido")
public class PedidoTransporteController {

	@Autowired
	private PedidoTransporteService pedidoService;
	
	@PostMapping
	public ResponseEntity<pedidoTransporte> criarPedido(@RequestBody pedidoTransporte pedido) {
		
		// Pegando o id e colocando na variável para que possa receber como Long no parâmentro do novoPedido
		Long veiculoId = pedido.getVeiculo().getId();
		Long rotasId = pedido.getRota().getId();
		
		pedidoTransporte novoPedido = pedidoService.pedido(
				veiculoId,
				rotasId,
				pedido.getDataInicio(),
				pedido.getDataFim());
		
		return new ResponseEntity<>(novoPedido, HttpStatus.CREATED);
		
	}
	
}
