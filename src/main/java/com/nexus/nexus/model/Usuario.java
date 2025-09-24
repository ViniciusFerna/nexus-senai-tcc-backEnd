package com.nexus.nexus.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.nexus.nexus.util.HashUtil;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "Usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private String email;
	
	@JsonProperty(access = Access.READ_WRITE)
	private String senha;
	
	private boolean isCliente;
	
	private String role;
	
	public void setSenha(String senha) {
		this.senha = HashUtil.hash(senha);
	}
	
}	
