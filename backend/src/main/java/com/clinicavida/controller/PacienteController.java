package com.clinicavida.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.clinicavida.dto.PacienteRequest;
import com.clinicavida.dto.PacienteResponse;
import com.clinicavida.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<PacienteResponse> cadastrar(@Valid @RequestBody PacienteRequest request) {
        PacienteResponse criado = pacienteService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }
        @GetMapping
    public List<PacienteResponse> listar(@RequestParam(required = false) String nome) {
        return pacienteService.listar(nome);
    }
}