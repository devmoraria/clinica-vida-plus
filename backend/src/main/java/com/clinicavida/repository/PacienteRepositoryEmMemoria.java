package com.clinicavida.repository;

import com.clinicavida.model.Paciente;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class PacienteRepositoryEmMemoria implements PacienteRepository {

    private final List<Paciente> pacientes = new ArrayList<>();
    private long proximoId = 1;

    @Override
    public synchronized Paciente salvar(Paciente paciente) {
        Paciente salvo = new Paciente(proximoId++, paciente.nome(), paciente.idade(), paciente.telefone());
        pacientes.add(salvo);
        return salvo;
    }

    @Override
    public synchronized List<Paciente> listarTodos() {
        return List.copyOf(pacientes);
    }
}