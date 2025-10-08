package com.nexus.nexus.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexus.nexus.dto.PedidoUpdateDto;
import com.nexus.nexus.exception.ResourceNotFoundException;
import com.nexus.nexus.model.Rotas;
import com.nexus.nexus.model.Veiculo;
import com.nexus.nexus.model.pedidoTransporte;
import com.nexus.nexus.repository.PedidoTransporteRepository;
import com.nexus.nexus.repository.RotasRepository;
import com.nexus.nexus.repository.VeiculoRepository;

@Service
public class PedidoTransporteService {

	@Autowired
	private VeiculoRepository veiculoRepo;
	
	@Autowired
	private RotasRepository rotaRepo;
	
	@Autowired
	private PedidoTransporteRepository pedidoRepo;
	
	public pedidoTransporte addPedido(Long veiculoId, Long rotaId, LocalDate dataInicio, LocalDate dataFim) {
		Veiculo veiculo = veiculoRepo.findById(veiculoId)
				.orElseThrow(() -> new ResourceNotFoundException("Veiculo não encontrado"));
		Rotas rota = rotaRepo.findById(rotaId)
				.orElseThrow(() -> new ResourceNotFoundException("Rota não encontrada"));
		
		Double custoPorKm = veiculo.getCustoPorKm();
		Double distancia = rota.getDistancia();
		Double calculoCustoTotal = custoPorKm * distancia;
		
		pedidoTransporte pedidoTransporte = new pedidoTransporte();
		pedidoTransporte.setVeiculo(veiculo);
		pedidoTransporte.setRota(rota);
		pedidoTransporte.setDataInicio(dataInicio);
		pedidoTransporte.setDataFim(dataFim);
		pedidoTransporte.setStatus("PLANEJADA");
		pedidoTransporte.setCustoTotal(calculoCustoTotal);
		
		veiculo.setStatus("EM USO");
		veiculoRepo.save(veiculo);
		
		return pedidoRepo.save(pedidoTransporte);
		
	}
	
	public pedidoTransporte updatePedido(Long Id, PedidoUpdateDto pedidoDto) {
		pedidoTransporte pedido = pedidoRepo.findById(Id)
				.orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
		
		pedido.setVeiculo(pedidoDto.getVeiculo());
		pedido.setRota(pedidoDto.getRota());
		pedido.setDataInicio(pedidoDto.getDataInicio());
		pedido.setDataFim(pedidoDto.getDataFim());
		pedido.setStatus(pedidoDto.getStatus());
		
		return pedidoRepo.save(pedido);
		
	}
	
	public void deletarPedido(Long id) {
		if(pedidoRepo.findById(id).isEmpty()) {
			throw new RuntimeException("Rota não encontrada");
		}
		pedidoRepo.deleteById(id);
	}
	
	public List<pedidoTransporte> getPedidos() {
		return pedidoRepo.findAll();
	}
	
	public Optional<pedidoTransporte> buscarPedidoId(Long id) {
		return pedidoRepo.findById(id);
	}
	
	
	
	
	
	
	
	
}
