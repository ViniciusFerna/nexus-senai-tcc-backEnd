package com.nexus.nexus.util;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

public class HashUtil {

	// assinatura sha256 para criptografia
	public static String hash(String palavra) {
		String salt = "projetotmsdahora";
		
		palavra = salt + palavra;
		
		String hash = Hashing.sha256().hashString(palavra, StandardCharsets.UTF_8).toString();
		
		return hash;
	}
	
	// Verificação da senha diretamente já com hash por questões de segurança
	public static boolean verify(String rawPassword, String hashedPassword) {
		return hash(rawPassword).equals(hashedPassword);
	}
	
}
