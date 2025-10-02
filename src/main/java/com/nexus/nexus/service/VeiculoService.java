package com.nexus.nexus.service;

import com.nexus.nexus.dto.VeiculoDto;
import com.nexus.nexus.model.Veiculo;
import com.nexus.nexus.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    public List<VeiculoDto> buscarTodos() {
        List<Veiculo> veiculos = veiculoRepository.findAll();
        return veiculos.stream().map(this::toDto).collect(Collectors.toList());
    }

    public Optional<VeiculoDto> buscarPorId(Long id) {
        return veiculoRepository.findById(id).map(this::toDto);
    }

    public VeiculoDto criar(VeiculoDto dto) {
        Veiculo veiculo = toEntity(dto);
        Veiculo salvo = veiculoRepository.save(veiculo);
        return toDto(salvo);
    }

    public Optional<VeiculoDto> atualizar(Long id, VeiculoDto dto) {
        Optional<Veiculo> veiculoOpt = veiculoRepository.findById(id);
        if (veiculoOpt.isPresent()) {
            Veiculo veiculo = veiculoOpt.get();
            veiculo.setPlaca(dto.getPlaca());
            veiculo.setModelo(dto.getModelo());
            veiculo.setTipoVeiculo(dto.getTipoVeiculo());
            veiculo.setCapacidadePeso(dto.getCapacidadePeso());
            veiculo.setCustoPorKm(dto.getCustoPorKm());
            veiculo.setStatus(dto.getStatus());
            Veiculo atualizado = veiculoRepository.save(veiculo);
            return Optional.of(toDto(atualizado));
        }
        return Optional.empty();
    }

    public boolean deletar(Long id) {
        if (veiculoRepository.existsById(id)) {
            veiculoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private VeiculoDto toDto(Veiculo veiculo) {
        VeiculoDto dto = new VeiculoDto();
        dto.setId(veiculo.getId());
        dto.setPlaca(veiculo.getPlaca());
        dto.setModelo(veiculo.getModelo());
        dto.setTipoVeiculo(veiculo.getTipoVeiculo());
        dto.setCapacidadePeso(veiculo.getCapacidadePeso());
        dto.setCustoPorKm(veiculo.getCustoPorKm());
        dto.setStatus(veiculo.getStatus());
        return dto;
    }

    private Veiculo toEntity(VeiculoDto dto) {
        Veiculo veiculo = new Veiculo();
        veiculo.setId(dto.getId());
        veiculo.setPlaca(dto.getPlaca());
        veiculo.setModelo(dto.getModelo());
        veiculo.setTipoVeiculo(dto.getTipoVeiculo());
        veiculo.setCapacidadePeso(dto.getCapacidadePeso());
        veiculo.setCustoPorKm(dto.getCustoPorKm());
        veiculo.setStatus(dto.getStatus());
        return veiculo;
    }
}
