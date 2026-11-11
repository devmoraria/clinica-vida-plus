package com.clinicavida.dto;

import com.clinicavida.model.Paciente;

public record PacienteResponse(Long id, String nome, int idade, String telefone) {

    public static PacienteResponse de(Paciente paciente) {
        return new PacienteResponse(paciente.id(), paciente.nome(), paciente.idade(), paciente.telefone());
    }
}