import { Component, inject, signal } from '@angular/core';
import { DecimalPipe } from '@angular/common';
import { PacienteService } from '../../services/paciente.service';
import { Estatisticas as EstatisticasModel } from '../../models/paciente.model';

@Component({
  selector: 'app-estatisticas',
  imports: [DecimalPipe],
  templateUrl: './estatisticas.html',
  styleUrl: './estatisticas.css',
})
export class Estatisticas {
  private readonly service = inject(PacienteService);

  protected readonly dados = signal<EstatisticasModel | null>(null);
  protected readonly carregando = signal(false);
  protected readonly erro = signal('');

  constructor() {
    this.carregar();
  }

  protected carregar(): void {
    this.carregando.set(true);
    this.erro.set('');

    this.service.estatisticas().subscribe({
      next: (d) => {
        this.dados.set(d);
        this.carregando.set(false);
      },
      error: () => {
        this.dados.set(null);
        this.carregando.set(false);
        this.erro.set('Não foi possível falar com a API. Verifique se o back-end está no ar.');
      },
    });
  }
}