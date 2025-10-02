package com.nexus.nexus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexus.nexus.dto.PedidoTransporteRequestDTO;
import com.nexus.nexus.dto.PedidoUpdateDto;
import com.nexus.nexus.model.pedidoTransporte;
import com.nexus.nexus.service.PedidoTransporteService;

@RestController
@CrossOrigin
@RequestMapping("/pedido")
public class PedidoTransporteController {

	@Autowired
	private PedidoTransporteService pedidoService;
	
	@PostMapping("/")
	public ResponseEntity<pedidoTransporte> criarPedido(@RequestBody PedidoTransporteRequestDTO pedido) {
		
		pedidoTransporte novoPedido = pedidoService.addPedido(
				pedido.getVeiculoId(),
				pedido.getRotaId(),
				pedido.getDataInicio(),
				pedido.getDataFim());
		
		return new ResponseEntity<>(novoPedido, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/")
	public ResponseEntity<pedidoTransporte> atualizarPedido(@PathVariable Long id, @RequestBody PedidoUpdateDto pedido) {
		try {
		pedidoTransporte pedidoAtualizado = pedidoService.updatePedido(id, pedido);
		return ResponseEntity.ok(pedidoAtualizado);
		} catch(RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	
	
	
	
}
