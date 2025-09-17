package com.nexus.nexus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexus.nexus.model.Viagem;
import com.nexus.nexus.repository.ViagemRepository;

@RestController
@RequestMapping("/viagem")
public class VIagemController {
	
	@Autowired
	private ViagemRepository viagemRepo;
	
	@PostMapping("/")
	public ResponseEntity<?> criarViagem(@RequestBody Viagem viagem) {
		try {
			if(viagem.getDataInicio() == null || viagem.getDataFim() == null) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Os campos de data de início e fim devem estar preenchidos");
			}
			
			if(viagem.getStatus().isEmpty() && viagem.getStatus() == null) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("O campo de status deve estar selecionado");
			}
			
			Viagem novaViagem = viagemRepo.save(viagem);
			
			return ResponseEntity.ok(novaViagem);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no servidor");
		}
	}
	
}
