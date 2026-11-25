import { Component, inject, signal } from '@angular/core';
import { PacienteService } from '../../services/paciente.service';
import { Paciente } from '../../models/paciente.model';

@Component({
  selector: 'app-listar',
  templateUrl: './listar.html',
  styleUrl: './listar.css',
})
export class Listar {
  private readonly service = inject(PacienteService);

  protected readonly pacientes = signal<Paciente[]>([]);
  protected readonly carregando = signal(false);
  protected readonly erro = signal('');

  constructor() {
    this.carregar();
  }

  protected carregar(): void {
    this.carregando.set(true);
    this.erro.set('');

    this.service.listar().subscribe({
      next: (lista) => {
        this.pacientes.set(lista);
        this.carregando.set(false);
      },
      error: () => {
        this.pacientes.set([]);
        this.carregando.set(false);
        this.erro.set('Não foi possível falar com a API. Verifique se o back-end está no ar.');
      },
    });
  }
}