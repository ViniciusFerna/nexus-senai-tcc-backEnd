package com.nexus.nexus.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexus.nexus.model.Veiculo;
import com.nexus.nexus.repository.VeiculoRepository;


@RestController
@CrossOrigin
@RequestMapping("/veiculos")
public class VeiculoController {

	@Autowired
	private VeiculoRepository repVeiculo;

	// GET, POST, PUT, DELETE
	@GetMapping("/")
	public ResponseEntity<?> buscarVeiculos() {
		try {
			List<Veiculo> veiculos = repVeiculo.findAll();

			if (veiculos.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT)
						.body("Nenhum veiculo encontrado.");
			}

			return ResponseEntity.ok(veiculos);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Ocorreu um erro interno no servidor.");

		}

	}

	@PostMapping("/")
	public ResponseEntity<?> criarVeiculo(@RequestBody Veiculo veiculo) {
		try {
			if (veiculo.getPlaca().isEmpty() || veiculo.getModelo().isEmpty()
				|| veiculo.getTipoVeiculo().isEmpty() 	) {
				return ResponseEntity.badRequest().body("O campo não pode ser vazio.");
			}

			Veiculo newVeiculo = repVeiculo.save(veiculo);

			return ResponseEntity.ok(newVeiculo);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Ocorreu um erro interno no servidor.");
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizarVeiculo (@PathVariable("id") Long id, @RequestBody Veiculo veiculo) {
		try {
			Optional<Veiculo> verificarSeExiste = repVeiculo.findById(id);

			if (verificarSeExiste.isPresent()) {
				Veiculo v = verificarSeExiste.get();
				v.setPlaca(veiculo.getPlaca());
				v.setModelo(veiculo.getModelo());
				v.setTipoVeiculo(veiculo.getTipoVeiculo());
				v.setCapacidadePeso(veiculo.getCapacidadePeso());
				v.setCapacidadeVolume(veiculo.getCapacidadeVolume());

				repVeiculo.save(v);

				return ResponseEntity.ok("Veiculo atualizado com sucesso !");
			} else {
				return ResponseEntity.status(HttpStatus.NO_CONTENT)
						.body("Veiculo não encontrado");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Ocorreu um erro interno no servidor.");
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletarVeiculo(@PathVariable("id") Long id) {
		try {
			Optional<Veiculo> verificarSeExiste = repVeiculo.findById(id);

			if (verificarSeExiste.isPresent()) {
				repVeiculo.deleteById(id);

				return ResponseEntity.ok("Veiculo deletado com sucesso!");
			} else {
				return ResponseEntity.status(HttpStatus.NO_CONTENT)
						.body("Nenhum veiculo foi encontrado.");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Ocorreu um erro interno no servidor.");
		}
	}
}