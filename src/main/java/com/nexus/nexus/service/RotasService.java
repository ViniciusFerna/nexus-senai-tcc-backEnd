package com.nexus.nexus.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nexus.nexus.model.Rotas;
import com.nexus.nexus.repository.RotasRepository;

@Service
public class RotasService {

    @Autowired
    private RotasRepository rotasRepository;

    public List<Rotas> listarTodas() {
        return rotasRepository.findAll();
    }

    public Optional<Rotas> buscarPorId(Long id) {
        return rotasRepository.findById(id);
    }

    public Rotas criarRota(Rotas rota) {
        // validações possíveis aqui
        return rotasRepository.save(rota);
    }

    public Rotas atualizarRota(Long id, Rotas rotaDetails) {
        Rotas rota = rotasRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Rota não encontrada com o id: " + id));
        
        rota.setOrigem(rotaDetails.getOrigem());
        rota.setDestino(rotaDetails.getDestino());
        rota.setDistancia(rotaDetails.getDistancia());
        rota.setTempoEstimadoHoras(rotaDetails.getTempoEstimadoHoras());

        return rotasRepository.save(rota);
    }

    public void deletarRota(Long id) {
        if (!rotasRepository.existsById(id)) {
            throw new RuntimeException("Rota não encontrada com o id: " + id);
        }
        rotasRepository.deleteById(id);
    }
}