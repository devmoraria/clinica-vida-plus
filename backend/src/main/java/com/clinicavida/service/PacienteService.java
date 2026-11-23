package com.clinicavida.service;

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
}