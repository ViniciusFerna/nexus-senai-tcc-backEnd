package com.nexus.nexus.service;

import com.nexus.nexus.dto.ClienteDTO;
import com.nexus.nexus.model.Cliente;
import com.nexus.nexus.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteDTO> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ClienteDTO buscarPorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.map(this::toDTO).orElse(null);
    }

    public ClienteDTO criar(ClienteDTO clienteDTO) {
        Cliente cliente = toEntity(clienteDTO);
        Cliente salvo = clienteRepository.save(cliente);
        return toDTO(salvo);
    }

    public ClienteDTO atualizar(Long id, ClienteDTO clienteDTO) {
        Optional<Cliente> optional = clienteRepository.findById(id);
        if (optional.isPresent()) {
            Cliente cliente = optional.get();
            cliente.setCnpjCpf(clienteDTO.getCnpjCpf());
            cliente.setEndereco(clienteDTO.getEndereco());
            cliente.setTelefone(clienteDTO.getTelefone());
            Cliente atualizado = clienteRepository.save(cliente);
            return toDTO(atualizado);
        }
        return null;
    }

    public boolean deletar(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ClienteDTO toDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setCnpjCpf(cliente.getCnpjCpf());
        dto.setEndereco(cliente.getEndereco());
        dto.setTelefone(cliente.getTelefone());
        return dto;
    }

    private Cliente toEntity(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setCnpjCpf(dto.getCnpjCpf());
        cliente.setEndereco(dto.getEndereco());
        cliente.setTelefone(dto.getTelefone());
        return cliente;
    }
}
