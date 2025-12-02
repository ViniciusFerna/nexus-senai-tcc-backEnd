package com.nexus.nexus.controller;

import com.nexus.nexus.dto.SimulacaoDto;
import com.nexus.nexus.service.SimulacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/simulacao")
public class SimulacaoController {

    @Autowired
    private SimulacaoService simulacaoService;

    @GetMapping("/")
    public ResponseEntity<List<SimulacaoDto>> listarTodas() {
        return ResponseEntity.ok(simulacaoService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SimulacaoDto> listarPorId(@PathVariable Long id) {
        return simulacaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<SimulacaoDto> criar(@RequestBody SimulacaoDto dto) {
        SimulacaoDto nova = simulacaoService.criar(dto);
        return ResponseEntity.ok(nova);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SimulacaoDto> atualizar(@PathVariable Long id, @RequestBody SimulacaoDto dto) {
        Optional<SimulacaoDto> atualizado = simulacaoService.atualizar(id, dto);
        return atualizado
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (simulacaoService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
