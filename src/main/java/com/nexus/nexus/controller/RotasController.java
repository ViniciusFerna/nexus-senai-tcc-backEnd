package com.nexus.nexus.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nexus.nexus.model.Rotas;
import com.nexus.nexus.service.RotasService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/rotas")
@Tag(name = "Rotas", description = "Endpoints para gerenciamento de rotas")
public class RotasController {

    @Autowired
    private RotasService rotasService;

    @PostMapping("/")
    @Operation(
    		summary = "Registro de uma nova rota",
    		description = "Permite que o usuário registre uma nova rota"
    )
    public ResponseEntity<Rotas> criarRota(@RequestBody Rotas rota) {
        Rotas novaRota = rotasService.criarRota(rota);
        return new ResponseEntity<>(novaRota, HttpStatus.CREATED);
    }
    
    @Operation(
    		summary = "Retorna os dados de todas as rotas do sistema",
    		description = "Retorna o id, origem, destino, distancia e tempo estimado em horas"
    )
    @GetMapping("/")
    public ResponseEntity<List<Rotas>> listarTodas() {
        return ResponseEntity.ok(rotasService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(
    		summary = "Retorna os dados da rota especificada pelo id",
    		description = "Retorna o id, origem, destino, distancia e tempo estimado em horas"
    )
    public ResponseEntity<Rotas> buscarPorId(@PathVariable Long id) {
        return rotasService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(
    		summary = "Atualiza as rota especificada pelo id",
    		description = "Permite que os usuário atualizem a rota especificada pelo id"
    )
    public ResponseEntity<Rotas> atualizarRota(@PathVariable Long id, @RequestBody Rotas rotaDetails) {
        try {
            Rotas rotaAtualizada = rotasService.atualizarRota(id, rotaDetails);
            return ResponseEntity.ok(rotaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
    		summary = "Deleta a rota especificada pelo id",
    		description = "Deleta completamente os dados da rota especificada pelo id"
    )
    public ResponseEntity<Void> deletarRota(@PathVariable Long id) {
        try {
            rotasService.deletarRota(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}