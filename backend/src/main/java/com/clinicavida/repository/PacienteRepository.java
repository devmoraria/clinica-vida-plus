package com.clinicavida.repository;

import com.clinicavida.model.Paciente;
import java.util.List;

public interface PacienteRepository {

    Paciente salvar(Paciente paciente);

    List<Paciente> listarTodos();
}