package com.nexus.nexus.model;

import com.nexus.nexus.util.HashUtil;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
	
	private String senha;
	
	private boolean isCliente;
	
	private String role;
	
	@OneToOne
	@JoinColumn(name = "cliente_id", referencedColumnName = "id", nullable = true)
	private Cliente idCliente;
	
	public void setSenha(String senha) {
		this.senha = HashUtil.hash(senha);
	}
	
}	
