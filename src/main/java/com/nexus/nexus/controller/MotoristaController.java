package com.nexus.nexus.controller;

import com.nexus.nexus.dto.MotoristaDTO;
import com.nexus.nexus.model.Motorista;
import com.nexus.nexus.service.MotoristaService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/motoristas")
public class MotoristaController {

    @Autowired
    private MotoristaService motoristaService;

    // 1. POST - Criar Novo Motorista
    // O @RequestBody deve receber o DTO, que contém apenas o idVeiculo
    @PostMapping("/")
    public ResponseEntity<MotoristaDTO> criar(@RequestBody @Valid MotoristaDTO motoristaDto) { 
        
        // 1. Converte DTO para Entidade (Service busca o Veiculo pelo ID)
        Motorista motoristaParaSalvar = motoristaService.convertToEntity(motoristaDto);
        
        // 2. Salva a nova Entidade no banco
        Motorista novoMotorista = motoristaService.criar(motoristaParaSalvar);
        
        // Constrói o URI de resposta (Location Header)
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoMotorista.getId()).toUri();
        
        // Retorna 201 Created com o DTO do motorista recém-criado
        return ResponseEntity.created(location).body(convertToDto(novoMotorista));
    }
    
    // 2. GET - Buscar Motorista por ID
    @GetMapping("/{id}")
    public ResponseEntity<MotoristaDTO> buscarPorId(@PathVariable Long id) {
        Motorista motorista = motoristaService.buscarPorId(id);
        return ResponseEntity.ok(convertToDto(motorista)); // Retorna 200 OK
    }
    
    // 3. GET - Listar Todos os Motoristas
    @GetMapping
    public ResponseEntity<List<MotoristaDTO>> listarTodos() {
        List<MotoristaDTO> motoristasDto = motoristaService.listarTodos().stream()
                .map(this::convertToDto) // Mapeia cada Entidade para DTO
                .collect(Collectors.toList());
                
        return ResponseEntity.ok(motoristasDto); // Retorna 200 OK
    }

    // 4. PUT - Atualizar Motorista
    @PutMapping("/{id}")
    public ResponseEntity<MotoristaDTO> atualizar(@PathVariable Long id, @RequestBody @Valid MotoristaDTO motoristaDto) {
        // Converte o DTO de entrada para uma entidade para facilitar a passagem de dados
        Motorista motoristaAtualizado = motoristaService.convertToEntity(motoristaDto);
        
        // Atualiza a entidade existente no banco
        Motorista motoristaSalvo = motoristaService.atualizar(id, motoristaAtualizado);
        
        // Retorna 200 OK com o DTO da entidade atualizada
        return ResponseEntity.ok(convertToDto(motoristaSalvo));
    }

    // 5. DELETE - Deletar Motorista
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        motoristaService.deletar(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }


    // --- Mapper para DTO (Ajustado) ---
    private MotoristaDTO convertToDto(Motorista motorista) {
        MotoristaDTO dto = new MotoristaDTO();
        dto.setId(motorista.getId());
        dto.setNomeMotorista(motorista.getNomeMotorista());
        dto.setCpf(motorista.getCpf());
        dto.setCnh(motorista.getCnh());
        dto.setTelefone(motorista.getTelefone());
        dto.setStatus(motorista.getStatus());
        
        // Mapear o ID do Veículo para o DTO (se o Veículo não for nulo)
        if (motorista.getIdVeiculo() != null) {
            dto.setIdVeiculo(motorista.getIdVeiculo().getId()); 
        }
        
        return dto;
    }
}