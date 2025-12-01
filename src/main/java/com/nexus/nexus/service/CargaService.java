package com.nexus.nexus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexus.nexus.dto.CargaDto;
import com.nexus.nexus.model.Carga;
import com.nexus.nexus.repository.CargaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CargaService {

    @Autowired
    private CargaRepository cargaRepository;

    public List<CargaDto> buscarTodos() {
        List<Carga> cargas = cargaRepository.findAll();
        return cargas.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<CargaDto> buscarPorId(Long id) {
        return cargaRepository.findById(id).map(this::toDto);
    }

    public CargaDto criar(CargaDto dto) {
        Carga carga = toEntity(dto);
        Carga salvo = cargaRepository.save(carga);
        return toDto(salvo);
    }

    public Optional<CargaDto> atualizar(Long id, CargaDto dto) {
        Optional<Carga> cargaOpt = cargaRepository.findById(id);

        if (cargaOpt.isPresent()) {
            Carga carga = cargaOpt.get();

            carga.setNome(dto.getNome());
            carga.setPeso(dto.getPeso());
            carga.setValor(dto.getValor());
            carga.setTipo(dto.getTipo());
            carga.setStatus(dto.getStatus());
            carga.setDescricao(dto.getDescricao());

            Carga atualizado = cargaRepository.save(carga);
            return Optional.of(toDto(atualizado));
        }

        return Optional.empty();
    }

    public boolean deletar(Long id) {
        if (cargaRepository.existsById(id)) {
            cargaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private CargaDto toDto(Carga carga) {
        CargaDto dto = new CargaDto();

        dto.setId(carga.getId());
        dto.setNome(carga.getNome());
        dto.setPeso(carga.getPeso());
        dto.setValor(carga.getValor());
        dto.setTipo(carga.getTipo());
        dto.setStatus(carga.getStatus());
        dto.setDescricao(carga.getDescricao());

        return dto;
    }

    private Carga toEntity(CargaDto dto) {
        Carga carga = new Carga();

        carga.setId(dto.getId());
        carga.setNome(dto.getNome());
        carga.setPeso(dto.getPeso());
        carga.setValor(dto.getValor());
        carga.setTipo(dto.getTipo());
        carga.setStatus(dto.getStatus());
        carga.setDescricao(dto.getDescricao());

        return carga;
    }
}
