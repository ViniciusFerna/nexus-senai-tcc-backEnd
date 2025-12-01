package com.nexus.nexus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nexus.nexus.dto.CargaDto;
import com.nexus.nexus.service.CargaService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/cargas")
public class CargaController {

    @Autowired
    private CargaService cargaService;

    @GetMapping("/")
    public ResponseEntity<List<CargaDto>> listarTodas() {
        return ResponseEntity.ok(cargaService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CargaDto> listarPorId(@PathVariable Long id) {
        return cargaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<CargaDto> criar(@RequestBody CargaDto cargaDto) {
        CargaDto nova = cargaService.criar(cargaDto);
        return ResponseEntity.ok(nova);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CargaDto> atualizar(@PathVariable Long id, @RequestBody CargaDto dto) {
        Optional<CargaDto> atualizado = cargaService.atualizar(id, dto);

        return atualizado
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (cargaService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
