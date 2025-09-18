package com.nexus.nexus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexus.nexus.model.Rotas;
import com.nexus.nexus.repository.RotasRepository;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/rotas")
public class RotasController {
	
	@Autowired
	private RotasRepository rotaRepo;

	@PostMapping("/")
	public ResponseEntity<?> criarViagem(@Valid @RequestBody Rotas rota) {
		try {
			
			Rotas novaRota = rotaRepo.save(rota);
			
			return ResponseEntity.ok(novaRota);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no servidor");
		}
	}
	
}
