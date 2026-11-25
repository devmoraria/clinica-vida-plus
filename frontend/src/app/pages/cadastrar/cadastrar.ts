import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { PacienteService } from '../../services/paciente.service';
import { Paciente } from '../../models/paciente.model';

@Component({
  selector: 'app-cadastrar',
  imports: [ReactiveFormsModule],
  templateUrl: './cadastrar.html',
  styleUrl: './cadastrar.css',
})
export class Cadastrar {
  private readonly fb = inject(FormBuilder);
  private readonly service = inject(PacienteService);

  protected readonly form = this.fb.group({
    nome: ['', [Validators.required, Validators.minLength(2)]],
    idade: [null as number | null, [Validators.required, Validators.min(0), Validators.max(120)]],
    telefone: ['', [Validators.required]],
  });

  protected readonly enviando = signal(false);
  protected readonly cadastrado = signal<Paciente | null>(null);
  protected readonly erroGeral = signal('');
  protected readonly errosApi = signal<Record<string, string>>({});

  protected erro(campo: 'nome' | 'idade' | 'telefone'): string {
    const apiMsg = this.errosApi()[campo];
    if (apiMsg) return apiMsg;

    const c = this.form.controls[campo];
    if (!c.touched && !c.dirty) return '';
    if (c.hasError('required')) return 'Campo obrigatório.';
    if (c.hasError('minlength')) return 'Informe pelo menos 2 caracteres.';
    if (c.hasError('min') || c.hasError('max')) return 'Informe uma idade entre 0 e 120.';
    return '';
  }

  protected enviar(): void {
    this.cadastrado.set(null);
    this.erroGeral.set('');
    this.errosApi.set({});

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const v = this.form.getRawValue();
    this.enviando.set(true);

    this.service
      .cadastrar({
        nome: (v.nome ?? '').trim(),
        idade: Number(v.idade),
        telefone: (v.telefone ?? '').trim(),
      })
      .subscribe({
        next: (paciente) => {
          this.enviando.set(false);
          this.cadastrado.set(paciente);
          this.form.reset();
        },
        error: (err: HttpErrorResponse) => {
          this.enviando.set(false);
          if (err.status === 400 && err.error && typeof err.error === 'object') {
            this.errosApi.set(err.error as Record<string, string>);
          } else if (err.status === 0 || err.status >= 500) {
            this.erroGeral.set('Não foi possível falar com a API. Verifique se o back-end está no ar.');
          } else {
            this.erroGeral.set('Não foi possível cadastrar o paciente.');
          }
        },
      });
  }
}