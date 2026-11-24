package com.clinicavida.dto;

public record EstatisticasResponse(
        int total,
        Double idadeMedia,
        PacienteResponse maisNovo,
        PacienteResponse maisVelho) {
}