package com.nexus.nexus.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.nexus.nexus.dto.VeiculoDto;
import com.nexus.nexus.service.VeiculoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@CrossOrigin
@RequestMapping("/veiculos")
@Tag(name = "Veículos", description = "Endpoints para gerenciamento de veículos. (Requer autenticação)")
public class VeiculoController {
	
	@Autowired
	private VeiculoService veiculoService;

	@GetMapping("/")
	@Operation(
			summary = "Retorna os dados de todos os veículos do sistema",
			description = "Retorna o id, placa, modelo, tipo de veículo, capacidade peso, custo por KM e o status atual"
	)
    public ResponseEntity<List<VeiculoDto>> listarTodas() {
        return ResponseEntity.ok(veiculoService.buscarTodos());
    }
	
	@GetMapping("/{id}")
	@Operation(
			summary = "Retorna os dados do veículo especificado pelo id",
			description = "Retorna o id, placa, modelo, tipo de veículo, capacidade peso, custo por KM e o status atual"
	)
	public ResponseEntity<VeiculoDto> listaPorId(@PathVariable Long id) {
		return veiculoService.buscarPorId(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/")
	@Operation(
			summary = "Registro de um novo veículo",
			description = "Permite que o usuário cadastre um novo veículo no sistema"
	)
    public ResponseEntity<?> criarVeiculo(@RequestBody VeiculoDto veiculo) {
        VeiculoDto novoVeiculo = veiculoService.criar(veiculo);
        return ResponseEntity.ok(novoVeiculo);
    }

	@PutMapping("/{id}")
	@Operation(
			summary = "Atualiza o veículo especificado pelo id",
			description = "Permite que o usuário atualizem os dados do veículo especificado pelo id"
	)
    public ResponseEntity<VeiculoDto> atualizarVeiculo(@PathVariable Long id, @RequestBody VeiculoDto veiculoDto) {
        // O serviço retorna Optional<VeiculoDto>
        Optional<VeiculoDto> veiculoAtualizadoOpt = veiculoService.atualizar(id, veiculoDto);

        // Verifica se o Optional contém um valor
        if (veiculoAtualizadoOpt.isPresent()) {
            return ResponseEntity.ok(veiculoAtualizadoOpt.get());
        } else {
            // Se não, o veículo com o ID fornecido não foi encontrado
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found
        }
    }
	
	@DeleteMapping("/{id}")
	@Operation(
			summary = "Deleta o veículo especificado pelo id",
			description = "Deleta completamente os dados do veículo especificado pelo id"
	)
    public ResponseEntity<Void> deletarVeiculo(@PathVariable Long id) {
        try {
            veiculoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}