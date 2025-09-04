package com.nexus.nexus.auth;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nexus.nexus.model.Usuario;
import com.nexus.nexus.repository.UsuarioRepository;
import com.nexus.nexus.util.HashUtil;

public class AuthenticateUserCase {

	private final UsuarioRepository userRep;
	private final String secretKey = "projetotmsdahora";
	private final long expiration = 604800000;
	
	public AuthenticateUserCase(UsuarioRepository userRep) {
		this.userRep = userRep;
	}
	
	public String execute(String email, String rawPassword) {
		Usuario user = userRep.findByEmail(email);
		
		// Validando a senha fora do banco e comparando a senha escrita com a criptografada por motivos de segurança
		if (!HashUtil.verify(rawPassword, user.getSenha())) {
			throw new RuntimeException("Credenciais Incorretas");
		}
		
		return generateToken(user);
		
	}
	// gerar token
	public String generateToken(Usuario user) {
		// Criando algoritmo com a secret
		Algorithm algorithm = Algorithm.HMAC256(secretKey);
		
		// Gerando token JWT
		return JWT.create()
				.withSubject(user.getId().toString())
				.withClaim("email", user.getEmail())
				.withClaim("roles", user.getRole())
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + expiration))
				.sign(algorithm);
	}
	
}
	

