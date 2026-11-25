import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PacienteService } from '../../services/paciente.service';
import { Paciente } from '../../models/paciente.model';

@Component({
  selector: 'app-buscar',
  imports: [FormsModule],
  templateUrl: './buscar.html',
  styleUrl: './buscar.css',
})
export class Buscar {
  private readonly service = inject(PacienteService);

  protected termo = '';
  protected readonly resultados = signal<Paciente[]>([]);
  protected readonly buscou = signal(false);
  protected readonly termoBuscado = signal('');
  protected readonly carregando = signal(false);
  protected readonly erro = signal('');

  protected buscar(): void {
    const t = this.termo.trim();
    if (!t) {
      this.erro.set('Digite um nome para buscar.');
      this.buscou.set(false);
      this.resultados.set([]);
      return;
    }

    this.carregando.set(true);
    this.erro.set('');

    this.service.buscarPorNome(t).subscribe({
      next: (lista) => {
        this.resultados.set(lista);
        this.termoBuscado.set(t);
        this.buscou.set(true);
        this.carregando.set(false);
      },
      error: () => {
        this.resultados.set([]);
        this.buscou.set(false);
        this.carregando.set(false);
        this.erro.set('Não foi possível falar com a API. Verifique se o back-end está no ar.');
      },
    });
  }
}