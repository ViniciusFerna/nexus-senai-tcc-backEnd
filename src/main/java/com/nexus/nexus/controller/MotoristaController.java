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

@RestController
@RequestMapping("/motoristas")
public class MotoristaController {

    @Autowired
    private MotoristaService motoristaService;

    @PostMapping
    public ResponseEntity<MotoristaDTO> criar(@RequestBody @Valid MotoristaDTO motoristaDto) {
        Motorista motorista = motoristaService.convertToEntity(motoristaDto);
        Motorista novoMotorista = motoristaService.criar(motorista);
        
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoMotorista.getId()).toUri();
        
        return ResponseEntity.created(location).body(convertToDto(novoMotorista));
    }
    
    // GET (todos), GET (por id), PUT, DELETE...

    // --- Mapper para DTO ---
    private MotoristaDTO convertToDto(Motorista motorista) {
        MotoristaDTO dto = new MotoristaDTO();
        dto.setId(motorista.getId());
        dto.setNomeMotorista(motorista.getNomeMotorista());
        dto.setCpf(motorista.getCpf());
        dto.setCnh(motorista.getCnh());
        dto.setTelefone(motorista.getTelefone());
        dto.setStatus(motorista.getStatus());
        dto.setCustoKmMotorista(motorista.getCustoKmMotorista());
        if (motorista.getId() != null) {
            dto.setIdVeiculo(motorista.getId());
        }
        return dto;
    }
}