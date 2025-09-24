package com.nexus.nexus.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nexus.nexus.model.Rotas;
import com.nexus.nexus.service.RotasService;

@RestController
@RequestMapping("/api/rotas")
public class RotasController {

    @Autowired
    private RotasService rotasService;

    @PostMapping
    public ResponseEntity<Rotas> criarRota(@RequestBody Rotas rota) {
        Rotas novaRota = rotasService.criarRota(rota);
        return new ResponseEntity<>(novaRota, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Rotas>> listarTodas() {
        return ResponseEntity.ok(rotasService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rotas> buscarPorId(@PathVariable Long id) {
        return rotasService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rotas> atualizarRota(@PathVariable Long id, @RequestBody Rotas rotaDetails) {
        try {
            Rotas rotaAtualizada = rotasService.atualizarRota(id, rotaDetails);
            return ResponseEntity.ok(rotaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarRota(@PathVariable Long id) {
        try {
            rotasService.deletarRota(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}