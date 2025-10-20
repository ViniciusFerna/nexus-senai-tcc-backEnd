package com.nexus.nexus.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;	
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexus.nexus.auth.AuthenticateUserCase;
import com.nexus.nexus.dto.UserProfileDto;
import com.nexus.nexus.dto.UserProfileUpdateDto;
import com.nexus.nexus.dto.loginDto;
import com.nexus.nexus.exception.ResourceNotFoundException;
import com.nexus.nexus.model.Usuario;
import com.nexus.nexus.repository.UsuarioRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

//http://localhost:8080/swagger-ui/index.html#/

@RestController
@CrossOrigin
@RequestMapping("/user")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository userRepo;
	
	@Operation(
		summary = "Faz o login do usuário",
		description = "Ao fazer o login é gerado o token do usuário que é usado para a maioria dos EndPoints"
	)
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody loginDto user) {
		try {
			AuthenticateUserCase auth = new AuthenticateUserCase(userRepo);
			
			String token = auth.execute(user.getEmail(), user.getSenha());
			
			// com Map e possivel retornar um body em JSON, ja que o springboot usa json para serializar o map
			Map<String, String> response = new HashMap<>();
			response.put("token", token);
			
			return ResponseEntity.status(HttpStatus.OK).body(response);
			
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha incorreto");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no servidor");
		}
	}
	
	@Operation(
		summary = "Registra um novo usuário",
		description = "Permite que o usuário se cadastre usando o nome, email e senha"
	)
	@PostMapping("/registrar")
	public ResponseEntity<?> createUser(@Valid @RequestBody Usuario user) {
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
	
	@Operation(
		summary = "Retorna os dados de todos os usuários cadastrados no sistema",
		description = "Retorna o id, nome, email, senha(criptograda) e role de todos os usuários"
	)
	@GetMapping("/")
	public ResponseEntity<?> getUsers() {
		try {
			List<Usuario> users = userRepo.findAll();
			
			if(users.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Não há nenhum usuário");
			}
			
			return ResponseEntity.ok(users);
			
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no servidor");
		}
	}
	
	@Operation(
		summary = "Retorna os dados do usuário especificado pelo id",
		description = "Retorna o id, nome, email, senha(criptograda) e role do usuário"
	)
	@GetMapping("/{id}")
	public ResponseEntity<?> getUser(@PathVariable Long id, Authentication authentication) {
		// O Authentication e um objeto que vai conter os dados do usuario logado no momento por meio do token passado no header
		try {
			// Aqui o authentication.getName() esta pegando o id do user logado e o comparando com o id passado na requisicao
			Long userId = Long.parseLong(authentication.getName());
	        if (!id.equals(userId)) {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                   .body("Você só pode visualizar seus próprios dados");
	        }

	        // Busca o usuário
	        Usuario user = userRepo.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

	        // Retorna DTO (não retorna a entidade diretamente)
	        UserProfileDto response = new UserProfileDto(
	            user.getNome(),
	            user.getEmail()
	        );
	        
	        return ResponseEntity.ok(response);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no servidor");
		}
		
	}
	
	@Operation(
		summary = "Deleta o usuário específicado pelo id",
		description = "Deleta completamente os dados do usuário especificado pelo id"
	)
	@DeleteMapping("/deletarUser")
	public ResponseEntity<?> deleteUser(Authentication authentication) {
		try {
			
			Long userId = Long.parseLong(authentication.getName());
			
			userRepo.deleteById(userId);
			
			return ResponseEntity.status(HttpStatus.OK).body("Seu usuário foi deletado com sucesso");
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no servidor");
		}
	}
	
	@Operation(
		summary = "Atualiza o perfil do usuário logado",
		description = "Permite que o usuário modifique seu nome e email. Requer autenticação"
	)
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody @Valid UserProfileUpdateDto userUpdateDto, Authentication authentication) {
		try {
			
			Long userId = Long.parseLong(authentication.getName());
			if (!id.equals(userId)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
		                   .body("Você só pode atualizar os seus próprios dados");
			}
			
			Usuario user = userRepo.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
			
			
					user.setNome(userUpdateDto.getNome());
					user.setEmail(userUpdateDto.getEmail());
			
			userRepo.save(user);
			
			return ResponseEntity.status(HttpStatus.OK).body("Usuário atualizado com sucesso");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no servidor");
		}
	}

	
}
