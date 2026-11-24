package com.clinicavida.service;

import java.text.Normalizer;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;
import com.clinicavida.dto.PacienteRequest;
import com.clinicavida.dto.PacienteResponse;
import com.clinicavida.model.Paciente;
import com.clinicavida.repository.PacienteRepository;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public PacienteResponse cadastrar(PacienteRequest request) {
        Paciente novo = new Paciente(null, request.nome().trim(), request.idade(), request.telefone());
        Paciente salvo = pacienteRepository.salvar(novo);
        return PacienteResponse.de(salvo);
    }
        public List<PacienteResponse> listar(String nome) {
        Stream<Paciente> pacientes = pacienteRepository.listarTodos().stream();

        if (nome != null && !nome.isBlank()) {
            String termo = normalizar(nome);
            pacientes = pacientes.filter(p -> normalizar(p.nome()).contains(termo));
        }

        return pacientes
                .sorted(Comparator.comparing((Paciente p) -> normalizar(p.nome())))
                .map(PacienteResponse::de)
                .toList();
    }

    private static String normalizar(String texto) {
        return Normalizer.normalize(texto.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT);
    }
}