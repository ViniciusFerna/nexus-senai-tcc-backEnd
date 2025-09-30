package com.nexus.nexus.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
	
	public pedidoTransporte addPedido(Long VeiculoId, Long RotaId, LocalDate dataInicio, LocalDate dataFim) {
		Veiculo veiculo = veiculoRepo.findById(VeiculoId)
				.orElseThrow(() -> new ResourceNotFoundException("Veiculo não encontrado"));
		Rotas rota = rotaRepo.findById(RotaId)
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
	
	
	
	
	
	
	
	
}
