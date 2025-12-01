package com.nexus.nexus.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexus.nexus.dto.PedidoUpdateDto;
import com.nexus.nexus.exception.ResourceNotFoundException;
import com.nexus.nexus.model.Carga;
import com.nexus.nexus.model.Rotas;
import com.nexus.nexus.model.Veiculo;
import com.nexus.nexus.model.pedidoTransporte;
import com.nexus.nexus.repository.CargaRepository;
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
	
	@Autowired
	private CargaRepository cargaRepo;
	
	public pedidoTransporte addPedido(Long veiculoId, Long rotaId, Long cargaId,
            LocalDate dataInicio, LocalDate dataFim) {

		Veiculo veiculo = veiculoRepo.findById(veiculoId)
		.orElseThrow(() -> new ResourceNotFoundException("Veiculo não encontrado"));
		
		Rotas rota = rotaRepo.findById(rotaId)
		.orElseThrow(() -> new ResourceNotFoundException("Rota não encontrada"));
		
		Carga carga = cargaRepo.findById(cargaId)
		.orElseThrow(() -> new ResourceNotFoundException("Carga não encontrada"));
		
		// evitar usar mesma carga para dois pedidos
		if (carga.getPedido() != null) {
		throw new RuntimeException("Essa carga já está vinculada a outro pedido!");
		}
		
		double custoTotal = veiculo.getCustoPorKm() * rota.getDistancia();
		
		pedidoTransporte pedido = new pedidoTransporte();
		pedido.setVeiculo(veiculo);
		pedido.setRota(rota);
		pedido.setCarga(carga);
		pedido.setDataInicio(dataInicio);
		pedido.setDataFim(dataFim);
		pedido.setStatus("PLANEJADA");
		pedido.setCustoTotal(custoTotal);
		
		veiculo.setStatus("EM USO");
		veiculoRepo.save(veiculo);
		
		return pedidoRepo.save(pedido);
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
