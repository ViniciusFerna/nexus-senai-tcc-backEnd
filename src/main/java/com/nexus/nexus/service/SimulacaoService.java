package com.nexus.nexus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexus.nexus.dto.SimulacaoDto;
import com.nexus.nexus.model.Simulacao;
import com.nexus.nexus.repository.SimulacaoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SimulacaoService {

    @Autowired
    private SimulacaoRepository simulacaoRepository;

    public List<SimulacaoDto> buscarTodos() {
        return simulacaoRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<SimulacaoDto> buscarPorId(Long id) {
        return simulacaoRepository.findById(id).map(this::toDto);
    }

    public SimulacaoDto criar(SimulacaoDto dto) {
        Simulacao sim = toEntity(dto);
        Simulacao salvo = simulacaoRepository.save(sim);
        return toDto(salvo);
    }

    public Optional<SimulacaoDto> atualizar(Long id, SimulacaoDto dto) {
        Optional<Simulacao> simOpt = simulacaoRepository.findById(id);

        if (simOpt.isPresent()) {
            Simulacao sim = simOpt.get();

            sim.setUserId(dto.getUserId());
            sim.setName(dto.getName());
            sim.setOrigin(dto.getOrigin());
            sim.setDestination(dto.getDestination());
            sim.setDistanceKm(dto.getDistanceKm());
            sim.setEstimatedTimeH(dto.getEstimatedTimeH());
            sim.setFuelCost(dto.getFuelCost());
            sim.setTollCost(dto.getTollCost());
            sim.setTotalCost(dto.getTotalCost());
            sim.setStatus(dto.getStatus());

            Simulacao atualizado = simulacaoRepository.save(sim);
            return Optional.of(toDto(atualizado));
        }

        return Optional.empty();
    }

    public boolean deletar(Long id) {
        if (simulacaoRepository.existsById(id)) {
            simulacaoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private SimulacaoDto toDto(Simulacao sim) {
        SimulacaoDto dto = new SimulacaoDto();

        dto.setId(sim.getId());
        dto.setUserId(sim.getUserId());
        dto.setName(sim.getName());
        dto.setOrigin(sim.getOrigin());
        dto.setDestination(sim.getDestination());
        dto.setDistanceKm(sim.getDistanceKm());
        dto.setEstimatedTimeH(sim.getEstimatedTimeH());
        dto.setFuelCost(sim.getFuelCost());
        dto.setTollCost(sim.getTollCost());
        dto.setTotalCost(sim.getTotalCost());
        dto.setStatus(sim.getStatus());

        return dto;
    }

    private Simulacao toEntity(SimulacaoDto dto) {
        Simulacao sim = new Simulacao();

        sim.setId(dto.getId());
        sim.setUserId(dto.getUserId());
        sim.setName(dto.getName());
        sim.setOrigin(dto.getOrigin());
        sim.setDestination(dto.getDestination());
        sim.setDistanceKm(dto.getDistanceKm());
        sim.setEstimatedTimeH(dto.getEstimatedTimeH());
        sim.setFuelCost(dto.getFuelCost());
        sim.setTollCost(dto.getTollCost());
        sim.setTotalCost(dto.getTotalCost());
        sim.setStatus(dto.getStatus());

        return sim;
    }
}
