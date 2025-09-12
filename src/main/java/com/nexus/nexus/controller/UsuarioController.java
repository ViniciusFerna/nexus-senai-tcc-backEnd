package com.nexus.nexus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexus.nexus.model.Usuario;
import com.nexus.nexus.repository.UsuarioRepository;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository userRepo;
	
	@PostMapping("/registrar")
	public ResponseEntity<?> createUser(@RequestBody Usuario user) {
		try {
			
			if(user.getNome().isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Digite o nome de usuario para criar um usuario");
			} else if(user.getSenha().isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Digite uma senha para criar um usuario");
			} 
			
			if(userRepo.existsByEmail(user.getEmail())) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já cadastrado");
			}
			
			if (user.getRole() == null || user.getRole().isEmpty()) {
				user.setRole("USER");
			}
			
			Usuario newUser = userRepo.save(user);
			
			return ResponseEntity.ok(newUser);
				
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no servidor");
		}
	}
	
}
