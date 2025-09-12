package com.nexus.nexus.service;

import com.nexus.nexus.dto.MotoristaDTO;

/* (Camada de Serviço)
Contém a lógica de negócio. Onde no criar e atualizar, estou colocando para buscar a entidade Veiculo a partir do veiculoId recebido no DTO. 
*/

import com.nexus.nexus.exception.ResourceNotFoundException;
import com.nexus.nexus.model.Motorista;
import com.nexus.nexus.model.Veiculo;
import com.nexus.nexus.repository.MotoristaRepository;
import com.nexus.nexus.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotoristaService {

    @Autowired
    private MotoristaRepository motoristaRepository;

    @Autowired
    private VeiculoRepository veiculoRepository; // Injetado para buscar o veículo

    public Motorista criar(Motorista motorista) {
        // Podemos adicionar lógica de negício aqui, como validar se o CPF já existe.
        return motoristaRepository.save(motorista);
    }

    public Motorista buscarPorId(Long id) {
        return motoristaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Motorista não encontrado com o ID: " + id));
    }
    
    public List<Motorista> listarTodos() {
        return motoristaRepository.findAll();
    }

    public Motorista atualizar(Long id, Motorista motoristaAtualizado) {
        Motorista motoristaExistente = buscarPorId(id);
        
        motoristaExistente.setNomeMotorista(motoristaAtualizado.getNomeMotorista());
        motoristaExistente.setCpf(motoristaAtualizado.getCpf());
        // ... (atualizar outros campos)
        motoristaExistente.setId(motoristaAtualizado.getId());

        return motoristaRepository.save(motoristaExistente);
    }

    public void deletar(Long id) {
        Motorista motorista = buscarPorId(id);
        motoristaRepository.delete(motorista);
    }
    
    // Método auxiliar para converter DTO para Entidade, lidando com o relacionamento
    public Motorista convertToEntity(MotoristaDTO dto) {
        Motorista motorista = new Motorista();
        motorista.setNomeMotorista(dto.getNomeMotorista());
        motorista.setCpf(dto.getCpf());
        motorista.setCnh(dto.getCnh());
        motorista.setTelefone(dto.getTelefone());
        motorista.setStatus(dto.getStatus());
        motorista.setCustoKmMotorista(dto.getCustoKmMotorista());

        if (dto.getIdVeiculo() != null) {
            Veiculo veiculo = veiculoRepository.findById(dto.getIdVeiculo())
                    .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado com o ID: " + dto.getIdVeiculo()));
            motorista.setIdVeiculo(veiculo);
        }
        
        return motorista;
    }
}