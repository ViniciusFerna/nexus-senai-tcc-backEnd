package com.nexus.nexus.service;

import com.nexus.nexus.dto.MotoristaDTO;
import com.nexus.nexus.exception.ResourceNotFoundException;
import com.nexus.nexus.model.Motorista;
import com.nexus.nexus.model.Veiculo;
import com.nexus.nexus.repository.MotoristaRepository;
import com.nexus.nexus.repository.VeiculoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importe Transactional

import java.util.List;
import java.util.Optional;

@Service
@Transactional // Boa prática para métodos que modificam o estado do banco
public class MotoristaService {

    @Autowired
    private MotoristaRepository motoristaRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    // 1. Criar Novo Motorista
    public Motorista criar(Motorista motorista) {
        // Lógica de negócio (ex: verificar CPF único, etc.)
        return motoristaRepository.save(motorista);
    }

    // 2. Buscar Motorista por ID
    @Transactional(readOnly = true) // Apenas leitura
    public Motorista buscarPorId(Long id) {
        return motoristaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Motorista não encontrado com o ID: " + id));
    }
    
    // 3. Listar Todos
    @Transactional(readOnly = true) // Apenas leitura
    public List<Motorista> listarTodos() {
        return motoristaRepository.findAll();
    }

    // 4. Atualizar Motorista
    public Motorista atualizar(Long id, Motorista motoristaAtualizado) {
        Motorista motoristaExistente = buscarPorId(id); // Usa o método de busca que lança exceção

        // Atualiza todos os campos de interesse
        motoristaExistente.setNomeMotorista(motoristaAtualizado.getNomeMotorista());
        motoristaExistente.setCpf(motoristaAtualizado.getCpf());
        motoristaExistente.setCnh(motoristaAtualizado.getCnh());
        motoristaExistente.setTelefone(motoristaAtualizado.getTelefone());
        motoristaExistente.setStatus(motoristaAtualizado.getStatus());
        
        // **CORREÇÃO CRÍTICA:** Evite chamar 'setId(id)' na entidade existente.
        // motoristaExistente.setId(motoristaAtualizado.getId()); // Comentado, pois motoristaExistente JÁ tem o ID.

        // Lidar com a atualização do relacionamento Veículo:
        // Assume que motoristaAtualizado (a entidade convertida do DTO) já tem o objeto Veiculo anexado,
        // ou seja, o campo motoristaAtualizado.getIdVeiculo() (que é um objeto Veiculo)
        // tem o objeto Veiculo já buscado e atribuído pelo convertToEntity.
        motoristaExistente.setIdVeiculo(motoristaAtualizado.getIdVeiculo());

        return motoristaRepository.save(motoristaExistente);
    }

    // 5. Deletar Motorista
    public void deletar(Long id) {
        Motorista motorista = buscarPorId(id); // Usa o método de busca para garantir que exista
        motoristaRepository.delete(motorista);
    }
    
    // Método auxiliar para converter DTO para Entidade
    public Motorista convertToEntity(MotoristaDTO dto) {
        Motorista motorista = new Motorista();
        
        // **IMPORTANTE:** Se for atualização, o DTO pode ter um ID.
        // O ID do DTO de entrada (motorista que será criado/atualizado) não é relevante aqui,
        // pois a busca por ID é feita no método 'atualizar'.
        
        motorista.setNomeMotorista(dto.getNomeMotorista());
        motorista.setCpf(dto.getCpf());
        motorista.setCnh(dto.getCnh());
        motorista.setTelefone(dto.getTelefone());
        motorista.setStatus(dto.getStatus());

        // Busca a entidade Veiculo real no banco usando o ID do DTO
        if (dto.getIdVeiculo() != null) {
            Veiculo veiculo = veiculoRepository.findById(dto.getIdVeiculo())
                    .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado com o ID: " + dto.getIdVeiculo()));
            
            // Atribui o objeto Veiculo real à entidade Motorista
            motorista.setIdVeiculo(veiculo);
        } else {
             // Se o ID do Veículo for nulo no DTO, o relacionamento é desvinculado (se for opcional)
             motorista.setIdVeiculo(null);
        }
        
        return motorista;
    }
}