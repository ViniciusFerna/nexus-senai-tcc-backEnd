package com.nexus.nexus.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
	            pedido.getCargaId(),
	            pedido.getDataInicio(),
	            pedido.getDataFim()
	    );

	    return new ResponseEntity<>(novoPedido, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<pedidoTransporte> atualizarPedido(@PathVariable Long id, @RequestBody PedidoUpdateDto pedido) {
		try {
		pedidoTransporte pedidoAtualizado = pedidoService.updatePedido(id, pedido);
		return ResponseEntity.ok(pedidoAtualizado);
		} catch(RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<pedidoTransporte> deletarPedido(@PathVariable Long id) {
		try {
			pedidoService.deletarPedido(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/")
	public ResponseEntity<List<pedidoTransporte>> buscarPedido() {
		try {
			return ResponseEntity.ok(pedidoService.getPedidos());
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<pedidoTransporte>> buscarPedidoId(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(pedidoService.buscarPedidoId(id));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	
	
	
}
